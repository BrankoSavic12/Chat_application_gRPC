package rs.raf.pds.v4.z5.grpc;

import io.grpc.stub.StreamObserver;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ChatControlServiceImpl extends ChatControlGrpc.ChatControlImplBase {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();
    private final Map<String, List<StoredMessage>> roomMessages = new ConcurrentHashMap<>();
    private final AtomicLong messageIdGenerator = new AtomicLong(1);

    @Override
    public void createRoom(CreateRoomRequest request, StreamObserver<Room> responseObserver) {
        Room room = Room.newBuilder()
                .setName(request.getName())
                .setCreatedAt(System.currentTimeMillis())
                .addMembers(request.getOwner())
                .build();

        rooms.put(room.getName(), room);
        roomMessages.put(room.getName(), new ArrayList<>());

        responseObserver.onNext(room);
        responseObserver.onCompleted();
    }

    @Override
    public void sendRoomMessage(SendRoomMessageRequest request, StreamObserver<SendRoomMessageResponse> responseObserver) {
        StoredMessage msg = StoredMessage.newBuilder()
                .setId(messageIdGenerator.getAndIncrement())
                .setRoom(request.getRoom())
                .setFromUser(request.getFromUser())
                .setText(request.getText())
                .setTs(System.currentTimeMillis())
                .setReplyToId(request.getReplyToId())
                .build();

        roomMessages.computeIfAbsent(request.getRoom(), k -> new ArrayList<>()).add(msg);

        SendRoomMessageResponse response = SendRoomMessageResponse.newBuilder()
                .setMessage(msg)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
