package rs.raf.pds.v4.z5.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rs.raf.pds.v4.z5.grpc.*;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        ManagedChannel ch = ManagedChannelBuilder.forAddress("localhost", 8091)
                .usePlaintext()
                .build();
        ChatControlGrpc.ChatControlBlockingStub stub = ChatControlGrpc.newBlockingStub(ch);

        // 1) CreateRoom
        Room r = stub.createRoom(CreateRoomRequest.newBuilder()
                .setName("#pds")
                .setOwner("@ana")
                .build());
        System.out.println("Kreirana soba: " + r.getName() + " members=" + r.getMembersList());

        // 2) JoinRoom (dobijamo poslednjih 10 poruka)
        JoinRoomResponse jr = stub.joinRoom(JoinRoomRequest.newBuilder()
                .setRoom("#pds")
                .setUser("@mika")
                .build());
        System.out.println("Join: " + jr.getRoom().getName());
        jr.getLast10List().forEach(m ->
                System.out.println("  [" + m.getId() + "] " + m.getFromUser() + ": " + m.getText()));

        // 3) ListRooms – server-streaming
        System.out.println("Sve sobe (stream):");
        Iterator<Room> it = stub.listRooms(ListRoomsRequest.newBuilder().build());
        while (it.hasNext()) {
            Room room = it.next();
            System.out.println("  " + room.getName() + " members=" + room.getMembersList());
        }

        // 4) GetMoreRoom – “paginated” istorija sobe
        MoreRoomResponse hist = stub.getMoreRoom(MoreRoomRequest.newBuilder()
                .setRoom("#pds")
                .setBeforeId(Long.MAX_VALUE)
                .setCount(5)
                .build());
        System.out.println("Dodatne poruke iz #pds:");
        hist.getMessagesList().forEach(m ->
                System.out.println("  [" + m.getId() + "] " + m.getFromUser() + ": " + m.getText()));
        System.out.println("has_more=" + hist.getHasMore() + " next_before_id=" + hist.getNextBeforeId());

        // 5) SendRoomMessage – test slanja
        SendRoomMessageResponse sendResp = stub.sendRoomMessage(
                SendRoomMessageRequest.newBuilder()
                        .setRoom("#pds")
                        .setFromUser("@mika")
                        .setText("Pozdrav iz gRPC klijenta!")
                        .build());
        System.out.println("Nova poruka: [" + sendResp.getMessage().getId() + "] "
                + sendResp.getMessage().getFromUser() + ": " + sendResp.getMessage().getText());

        // 6) DM istorija
        DMHistoryResponse dms = stub.getLastDM(DMKey.newBuilder()
                .setUserA("@ana")
                .setUserB("@mika")
                .build());
        System.out.println("DM istorija @ana <-> @mika:");
        dms.getMessagesList().forEach(m ->
        System.out.println("  [" + m.getId() + "] " + m.getFromUser()
        + " -> room=" + m.getRoom() + ": " + m.getText()));


        // Graceful shutdown
        ch.shutdown();
        ch.awaitTermination(3, TimeUnit.SECONDS);
    }
}
