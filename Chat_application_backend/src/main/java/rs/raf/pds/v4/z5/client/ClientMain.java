package rs.raf.pds.v4.z5.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ClientMain {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()
                .build();

        ChatControlGrpc.ChatControlBlockingStub stub = ChatControlGrpc.newBlockingStub(channel);

        // Kreiranje sobe
        Room room = stub.createRoom(CreateRoomRequest.newBuilder()
                .setName("general")
                .setOwner("branko")
                .build());
        System.out.println("✅ Kreirana soba: " + room.getName());

        // Slanje poruke u sobu
        SendRoomMessageResponse resp = stub.sendRoomMessage(SendRoomMessageRequest.newBuilder()
                .setRoom("general")
                .setFromUser("branko")
                .setText("Ćao svima u sobi general!")
                .build());
        System.out.println("📩 Poruka poslata: " + resp.getMessage().getText());

        channel.shutdown();
    }
}
