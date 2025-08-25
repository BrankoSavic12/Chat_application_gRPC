package rs.raf.pds.v4.z5.server;

import io.grpc.stub.StreamObserver;
import rs.raf.pds.v4.z5.grpc.*;
import rs.raf.pds.v4.z5.store.MessageRepository;
import rs.raf.pds.v4.z5.socket.SocketChatServer;

import java.util.List;

public class ChatControlServiceImpl extends ChatControlGrpc.ChatControlImplBase {

    private final MessageRepository repo;
    private final SocketChatServer socket;

    public ChatControlServiceImpl(MessageRepository repo, SocketChatServer socket) {
        this.repo = repo;
        this.socket = socket;
    }

    // ---------- Helpers ----------

    private static String normRoom(String r) {
        if (r == null || r.trim().isEmpty()) return "#general";
        r = r.trim();
        return r.startsWith("#") ? r : ("#" + r);
    }

    private static String normUser(String u) {
        if (u == null || u.trim().isEmpty()) return "@unknown";
        u = u.trim();
        return u.startsWith("@") ? u : ("@" + u);
    }

    private static Room toRoomProto(String name, java.util.Set<String> members, long createdAt) {
        Room.Builder b = Room.newBuilder()
                .setName(name)
                .setCreatedAt(createdAt);
        if (members != null) {
            b.addAllMembers(members);
        }
        return b.build();
    }

    // ---------- Rooms ----------

    @Override
    public void createRoom(CreateRoomRequest request, StreamObserver<Room> responseObserver) {
        String room = normRoom(request.getName());
        String owner = normUser(request.getOwner());

        repo.createRoom(room, owner);

        Room out = toRoomProto(room, repo.getMembers(room), repo.getRoomCreatedAt(room));
        responseObserver.onNext(out);
        responseObserver.onCompleted();
    }

    @Override
    public void inviteUsers(InviteUsersRequest request, StreamObserver<Room> responseObserver) {
        String room = normRoom(request.getRoom());

        // pošto u request-u nemamo inviter polje, koristi vlasnika sobe kao pozivaoca
        String inviter = repo.getRoomOwner(room);
        if (inviter == null) {
            inviter = "@system";
        }

        for (String u : request.getUsersList()) {
            repo.inviteUser(room, inviter, normUser(u));
        }

        Room out = toRoomProto(room, repo.getMembers(room), repo.getRoomCreatedAt(room));
        responseObserver.onNext(out);
        responseObserver.onCompleted();
    }

    @Override
    public void listRooms(ListRoomsRequest request, StreamObserver<Room> responseObserver) {
        for (String room : repo.listRooms()) {
            responseObserver.onNext(
                    toRoomProto(room, repo.getMembers(room), repo.getRoomCreatedAt(room))
            );
        }
        responseObserver.onCompleted();
    }

    @Override
    public void joinRoom(JoinRoomRequest request, StreamObserver<JoinRoomResponse> responseObserver) {
        String room = normRoom(request.getRoom());
        String user = normUser(request.getUser());

        repo.joinRoom(room, user);
        socket.addUserToRoom(room, user);

        List<StoredMessage> last10 = repo.lastN(room, 10);

        JoinRoomResponse resp = JoinRoomResponse.newBuilder()
                .setRoom(toRoomProto(room, repo.getMembers(room), repo.getRoomCreatedAt(room)))
                .addAllLast10(last10)
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void getMoreRoom(MoreRoomRequest request, StreamObserver<MoreRoomResponse> responseObserver) {
        String room = normRoom(request.getRoom());
        long beforeId = request.getBeforeId();
        int count = request.getCount() > 0 ? request.getCount() : 10;

        List<StoredMessage> page = repo.pageBefore(room, beforeId, count);

        MoreRoomResponse.Builder mb = MoreRoomResponse.newBuilder()
                .addAllMessages(page);

        boolean hasMore = !page.isEmpty();
        long nextBeforeId = 0;
        if (!page.isEmpty()) {
            nextBeforeId = page.get(0).getId();
            if (page.size() < count) hasMore = false;
        } else {
            hasMore = false;
        }

        mb.setHasMore(hasMore).setNextBeforeId(nextBeforeId);
        responseObserver.onNext(mb.build());
        responseObserver.onCompleted();
    }

    // ---------- Room Messages ----------

    @Override
    public void sendRoomMessage(SendRoomMessageRequest request, StreamObserver<SendRoomMessageResponse> responseObserver) {
        String room = normRoom(request.getRoom());
        String user = normUser(request.getFromUser());

        if (!repo.getMembers(room).contains(user)) {
            responseObserver.onError(new IllegalArgumentException("User " + user + " is not a member of " + room));
            return;
        }

        long replyToId = request.getReplyToId();
        String excerpt = null;
        if (replyToId > 0) {
            StoredMessage orig = repo.findById(room, replyToId);
            if (orig != null) {
                excerpt = orig.getText().length() > 30
                        ? orig.getText().substring(0, 30) + "..."
                        : orig.getText();
            }
        }

        StoredMessage msg = repo.saveRoomMessage(room, user, request.getText(), replyToId, excerpt);

        socket.deliverGroupMessage(msg);

        SendRoomMessageResponse resp = SendRoomMessageResponse.newBuilder().setMessage(msg).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void editMessage(EditMessageRequest request, StreamObserver<EditMessageResponse> responseObserver) {
        String editor = normUser(request.getEditor());
        long id = request.getId();
        String newText = request.getNewText();

        if (request.getRoom() == null || request.getRoom().isEmpty()) {
            responseObserver.onError(new IllegalArgumentException("Editing is supported only for rooms via gRPC"));
            return;
        }

        String room = normRoom(request.getRoom());
        StoredMessage edited = repo.editRoomMessage(room, id, editor, newText);

        if (edited == null) {
            responseObserver.onError(new IllegalArgumentException("Message not found or permission denied"));
            return;
        }

        socket.deliverGroupMessage(edited);

        EditMessageResponse resp = EditMessageResponse.newBuilder().setMessage(edited).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void leaveRoom(LeaveRoomRequest request, StreamObserver<LeaveRoomResponse> responseObserver) {
        String room = normRoom(request.getRoom());
        String user = normUser(request.getUser());

        boolean ok = repo.getMembers(room).contains(user);
        if (ok) {
            repo.leaveRoom(room, user);
            socket.removeUserFromRoom(room, user);
        }

        LeaveRoomResponse resp = LeaveRoomResponse.newBuilder()
                .setSuccess(ok)
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    // ---------- Invites ----------

    @Override
    public void respondInvite(RespondInviteRequest request, StreamObserver<RespondInviteResponse> responseObserver) {
        String user = normUser(request.getUser());
        String room = normRoom(request.getRoom());

        if (request.getAccept()) {
            repo.acceptInvite(room, user);
            socket.addUserToRoom(room, user);

            // 🔹 povuci poslednjih 10 poruka
            List<StoredMessage> last10 = repo.lastN(room, 10);

            RespondInviteResponse resp = RespondInviteResponse.newBuilder()
                    .setRoom(toRoomProto(room, repo.getMembers(room), repo.getRoomCreatedAt(room)))
                    .addAllLast10(last10)   // isto kao joinRoom
                    .build();

            responseObserver.onNext(resp);
            responseObserver.onCompleted();
            return;
        }

        // ako je odbijen poziv
        repo.rejectInvite(room, user);
        RespondInviteResponse resp = RespondInviteResponse.newBuilder()
                .setRoom(toRoomProto(room, repo.getMembers(room), repo.getRoomCreatedAt(room)))
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }


    @Override
    public void listInvites(ListInvitesRequest request, StreamObserver<ListInvitesResponse> responseObserver) {
        String user = normUser(request.getUser());
        ListInvitesResponse resp = ListInvitesResponse.newBuilder()
                .addAllInvites(repo.getInvites(user))
                .build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
