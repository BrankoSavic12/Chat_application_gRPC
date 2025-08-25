package rs.raf.pds.v4.z5.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import rs.raf.pds.v4.z5.grpc.*;

import java.util.*;
import java.util.stream.Collectors;

// KryoNet & poruke
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import rs.raf.pds.v4.z5.socket.SocketMessages.*;
import rs.raf.pds.v4.z5.socket.KryoUtil;

public class UnifiedChatClient {

    // ---------------- Socket wrapper ----------------
    static class SimpleSocketClient {
        final Client client;

        SimpleSocketClient(String username, String host, int port) throws Exception {
            client = new Client(16_384, 16_384);
            KryoUtil.register(client.getKryo());

            client.addListener(new Listener() {
                @Override public void received(Connection conn, Object obj) {
                    if (obj instanceof SDeliverDM) {
                        SDeliverDM m = (SDeliverDM) obj;
                        System.out.println("\n[socket:DM] [" + m.id + "] " + m.fromUser + " -> " + m.toUser +
                                (m.edited ? " (EDITED)" : "") +
                                ": " + m.text +
                                (m.replyToId > 0 ? " [reply to " + m.replyToId + " → \"" + m.replyExcerpt + "\"]" : "")
                        );

                    } else if (obj instanceof SDeliverMulticast) {
                        SDeliverMulticast mc = (SDeliverMulticast) obj;
                        System.out.println("\n[socket:MC] [" + mc.id + "] " + mc.fromUser + " -> " + mc.deliveredTo +
                                (mc.edited ? " (EDITED)" : "") +
                                ": " + mc.text +
                                (mc.replyToId > 0 ? " [reply to " + mc.replyToId + " → \"" + mc.replyExcerpt + "\"]" : "")
                        );

                    } else if (obj instanceof SDeliverBroadcast) {
                        SDeliverBroadcast b = (SDeliverBroadcast) obj;
                        System.out.println("\n[socket:BC] [" + b.id + "] " + b.fromUser +
                                (b.edited ? " (EDITED)" : "") +
                                ": " + b.text +
                                (b.replyToId > 0 ? " [reply to " + b.replyToId + " → \"" + b.replyExcerpt + "\"]" : "")
                        );

                    } else if (obj instanceof SDeliverGroupMsg) {
                        SDeliverGroupMsg g = (SDeliverGroupMsg) obj;
                        System.out.println("\n[socket:GROUP:" + g.room + "] [" + g.id + "] " + g.fromUser +
                                (g.edited ? " (EDITED)" : "") +
                                ": " + g.text +
                                (g.replyToId > 0 ? " [reply to " + g.replyToId + " → \"" + g.replyExcerpt + "\"]" : "")
                        );

                    } else if (obj instanceof SDeliverEditedMessage) {
                        SDeliverEditedMessage e = (SDeliverEditedMessage) obj;
                        String where = (e.room != null && !e.room.isEmpty())
                                ? (e.room.equals("#broadcast") ? "BROADCAST" : "GROUP:" + e.room)
                                : "DM/MC";
                        System.out.println("\n[socket:EDIT-" + where + "] [" + e.id + "] " + e.fromUser +
                                ": " + e.text +
                                (e.replyToId > 0 ? " [reply to " + e.replyToId + " → \"" + e.replyExcerpt + "\"]" : "")
                        );
                    }
                }

                @Override public void disconnected(Connection connection) {
                    System.out.println("[socket] Diskonektovan sa servera.");
                }
            });

            client.start();
            client.connect(5_000, host, port);

            CLogin login = new CLogin();
            login.username = username;
            client.sendTCP(login);
        }

        // ---------- DM ----------
        void sendDM(String toUser, String text) {
            CSendDM dm = new CSendDM();
            dm.toUser = toUser;
            dm.fromUser = "";
            dm.text = text;
            client.sendTCP(dm);
        }

        void replyDM(long replyToId, String text) {
            CReplyDM r = new CReplyDM();
            r.fromUser = "";
            r.replyToId = replyToId;
            r.text = text;
            client.sendTCP(r);
        }

        void editDM(long id, String newText) {
            CEditMessage m = new CEditMessage();
            m.id = id;
            m.fromUser = "";
            m.room = "";
            m.toUser = "";
            m.newText = newText;
            client.sendTCP(m);
        }

        // ---------- Multicast ----------
        void sendMC(List<String> users, String text) {
            CSendMulticast mc = new CSendMulticast();
            mc.toUsers = new ArrayList<>(users);
            mc.fromUser = "";
            mc.text = text;
            client.sendTCP(mc);
        }

        void replyMC(long replyToId, String text) {
            CReplyMulticast r = new CReplyMulticast();
            r.fromUser = "";
            r.replyToId = replyToId;
            r.text = text;
            client.sendTCP(r);
        }

        void editMC(long id, String newText) {
            CEditMessage m = new CEditMessage();
            m.id = id;
            m.fromUser = "";
            m.room = "#multicast";
            m.newText = newText;
            client.sendTCP(m);
        }

        // ---------- Broadcast ----------
        void sendBC(String text) {
            CSendBroadcast bc = new CSendBroadcast();
            bc.fromUser = "";
            bc.text = text;
            client.sendTCP(bc);
        }

        void sendReplyBC(long replyToId, String text) {
            CReplyBroadcast rb = new CReplyBroadcast();
            rb.fromUser = "";
            rb.replyToId = replyToId;
            rb.text = text;
            client.sendTCP(rb);
        }

        void sendEditBC(long id, String newText) {
            CEditMessage m = new CEditMessage();
            m.fromUser = "";
            m.room     = "#broadcast";
            m.id       = id;
            m.newText  = newText;
            m.toUser   = "";
            client.sendTCP(m);
        }

        void close() {
            try { client.stop(); } catch (Exception ignore) {}
        }
    }

    // ---------------- UnifiedChatClient ----------------
    private final String username;
    private final SimpleSocketClient socketClient;
    private final ChatControlGrpc.ChatControlBlockingStub stub;
    private final ManagedChannel channel;

    public UnifiedChatClient(String username, int socketPort, int grpcPort) throws Exception {
        this.username = username;
        this.socketClient = new SimpleSocketClient(username, "localhost", socketPort);
        this.channel = ManagedChannelBuilder.forAddress("localhost", grpcPort)
                .usePlaintext()
                .build();
        this.stub = ChatControlGrpc.newBlockingStub(channel);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Unified Chat Client za " + username + " ===");
        System.out.println("Komande: "
                + "/dm, /reply-dm, /edit-dm, "
                + "/mc, /reply-mc, /edit-mc, "
                + "/bc, /reply-bc, /edit-bc, "
                + "/cr, /rooms, /join, /more, /invite, /invites, /accept, /reject, "
                + "/room-msg, /reply-msg, /edit-msg, /leave, "
                + "/quit");

        while (true) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            try {
                // ----- SOCKET commands -----
                if (line.equals("/quit")) {
                    shutdown();
                    break;

                } else if (line.startsWith("/dm ")) {
                    String[] parts = line.split("\\s+", 3);
                    if (parts.length < 3) continue;
                    socketClient.sendDM(parts[1], parts[2]);

                } else if (line.startsWith("/reply-dm ")) {
                    String[] p = line.split("\\s+", 3);
                    if (p.length < 3) continue;
                    socketClient.replyDM(Long.parseLong(p[1]), p[2]);

                } else if (line.startsWith("/edit-dm ")) {
                    String[] p = line.split("\\s+", 3);
                    if (p.length < 3) continue;
                    socketClient.editDM(Long.parseLong(p[1]), p[2]);

                } else if (line.startsWith("/mc ")) {
                    int sep = line.indexOf('|');
                    if (sep == -1) continue;
                    List<String> users = Arrays.stream(line.substring(0, sep).trim().split("\\s+"))
                            .skip(1).collect(Collectors.toList());
                    String text = line.substring(sep + 1).trim();
                    socketClient.sendMC(users, text);

                } else if (line.startsWith("/reply-mc ")) {
                    String[] p = line.split("\\s+", 3);
                    if (p.length < 3) continue;
                    socketClient.replyMC(Long.parseLong(p[1]), p[2]);

                } else if (line.startsWith("/edit-mc ")) {
                    String[] p = line.split("\\s+", 3);
                    if (p.length < 3) continue;
                    socketClient.editMC(Long.parseLong(p[1]), p[2]);

                } else if (line.startsWith("/bc ")) {
                    socketClient.sendBC(line.substring(4).trim());

                } else if (line.startsWith("/reply-bc ")) {
                    String[] p = line.split("\\s+", 3);
                    if (p.length < 3) continue;
                    socketClient.sendReplyBC(Long.parseLong(p[1]), p[2]);

                } else if (line.startsWith("/edit-bc ")) {
                    String[] p = line.split("\\s+", 3);
                    if (p.length < 3) continue;
                    socketClient.sendEditBC(Long.parseLong(p[1]), p[2]);

                // ----- gRPC commands (Rooms) -----
                } else if (line.startsWith("/cr ")) {
                    String room = line.split("\\s+", 2)[1];
                    CreateRoomRequest req = CreateRoomRequest.newBuilder()
                            .setName(room).setOwner(username).build();
                    Room r = stub.createRoom(req);
                    System.out.println("[OK] Kreirali ste sobu " + r.getName());

                } else if (line.startsWith("/rooms")) {
                    Iterator<Room> it = stub.listRooms(ListRoomsRequest.newBuilder().build());
                    System.out.println("[LISTA] Dostupne sobe:");
                    while (it.hasNext()) {
                        Room r = it.next();
                        System.out.println(" - " + r.getName() + " | clanovi: " + r.getMembersList());
                    }

                } else if (line.startsWith("/join ")) {
                    String room = line.split("\\s+", 2)[1];
                    JoinRoomRequest req = JoinRoomRequest.newBuilder()
                            .setRoom(room).setUser(username).build();
                    JoinRoomResponse resp = stub.joinRoom(req);
                    System.out.println("[PRIDRUZIVANJE] Usli ste u sobu " + room);
                    for (StoredMessage m : resp.getLast10List()) {
                        System.out.println("[soba:" + room + "] [" + m.getId() + "] " + m.getFromUser() +
                                (m.getEdited() ? " (EDITED)" : "") +
                                ": " + m.getText() +
                                (m.getReplyToId() > 0 ? " [reply to " + m.getReplyToId() + " → \"" + m.getReplyExcerpt() + "\"]" : "")
                        );
                    }

                } else if (line.startsWith("/invite ")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length < 3) continue;
                    String room = parts[1];
                    List<String> users = Arrays.asList(Arrays.copyOfRange(parts, 2, parts.length));
                    InviteUsersRequest req = InviteUsersRequest.newBuilder()
                            .setRoom(room).addAllUsers(users).build();
                    stub.inviteUsers(req);
                    System.out.println("[POZIV] Pozvali ste korisnike " + users + " u sobu " + room);

                } else if (line.startsWith("/invites")) {
                    ListInvitesRequest req = ListInvitesRequest.newBuilder().setUser(username).build();
                    ListInvitesResponse resp = stub.listInvites(req);
                    if (resp.getInvitesCount() == 0) {
                        System.out.println("[INFO] Nemate novih poziva.");
                    } else {
                        System.out.println("[INVITES] Pozivi koje ste dobili:");
                        for (PendingInvite inv : resp.getInvitesList()) {
                            System.out.println(" - u sobu " + inv.getRoom() + ", poslao " + inv.getInvitedBy());
                        }
                    }

                } else if (line.startsWith("/accept ")) {
                    String room = line.split("\\s+", 2)[1];

                    // 1. prihvati poziv
                    RespondInviteRequest req = RespondInviteRequest.newBuilder()
                            .setUser(username).setRoom(room).setAccept(true).build();
                    stub.respondInvite(req);

                    // 2. odmah se pridruži sobi da povuče poslednje poruke
                    JoinRoomRequest joinReq = JoinRoomRequest.newBuilder()
                            .setRoom(room).setUser(username).build();
                    JoinRoomResponse joinResp = stub.joinRoom(joinReq);

                    // 3. ispiši poruku i poslednjih 10
                    System.out.println("[OK] Prihvatili ste poziv i pridružili se sobi " + room);
                    for (StoredMessage m : joinResp.getLast10List()) {
                        System.out.println("[soba:" + room + "] [" + m.getId() + "] " + m.getFromUser() +
                                (m.getEdited() ? " (EDITED)" : "") +
                                ": " + m.getText() +
                                (m.getReplyToId() > 0 ? " [odgovor na " + m.getReplyToId() + " → \"" + m.getReplyExcerpt() + "\"]" : "")
                        );
                    }
                

                } else if (line.startsWith("/reject ")) {
                    String room = line.split("\\s+", 2)[1];
                    RespondInviteRequest req = RespondInviteRequest.newBuilder()
                            .setUser(username).setRoom(room).setAccept(false).build();
                    stub.respondInvite(req);
                    System.out.println("[X] Odbili ste poziv za sobu " + room);

                } else if (line.startsWith("/room-msg ")) {
                    int sep = line.indexOf('|');
                    if (sep == -1) continue;
                    String[] head = line.substring(0, sep).trim().split("\\s+");
                    if (head.length < 2) continue;
                    String room = head[1];
                    String text = line.substring(sep + 1).trim();

                    SendRoomMessageRequest req = SendRoomMessageRequest.newBuilder()
                            .setRoom(room).setFromUser(username).setText(text).build();
                    SendRoomMessageResponse resp = stub.sendRoomMessage(req);
                    StoredMessage m = resp.getMessage();
                    //System.out.println("[soba:" + room + "] [" + m.getId() + "] " + m.getFromUser() + ": " + m.getText());

                } else if (line.startsWith("/reply-msg ")) {
                    String[] p = line.split("\\s+", 4);
                    if (p.length < 4) continue;
                    String room = p[1];
                    long replyToId = Long.parseLong(p[2]);
                    String text = p[3];
                    SendRoomMessageRequest req = SendRoomMessageRequest.newBuilder()
                            .setRoom(room).setFromUser(username).setText(text).setReplyToId(replyToId).build();
                    SendRoomMessageResponse resp = stub.sendRoomMessage(req);
                    StoredMessage m = resp.getMessage();
                    /*System.out.println("[soba:" + room + "] [" + m.getId() + "] " + m.getFromUser() +
                            " (odgovor na " + replyToId + " \"" + m.getReplyExcerpt() + "\")" +
                            ": " + m.getText());*/

                } else if (line.startsWith("/edit-msg ")) {
                    String[] p = line.split("\\s+", 4);
                    if (p.length < 4) continue;
                    String room = p[1];
                    long id = Long.parseLong(p[2]);
                    String newText = p[3];
                    EditMessageRequest req = EditMessageRequest.newBuilder()
                            .setRoom(room).setId(id).setEditor(username).setNewText(newText).build();
                    EditMessageResponse resp = stub.editMessage(req);
                    StoredMessage m = resp.getMessage();
                    /*System.out.println("[IZMENA] [soba:" + room + "] [" + m.getId() + "] " + m.getFromUser() +
                            " (EDITED): " + m.getText());*/
                } else if (line.startsWith("/more ")) {
                    String[] p = line.split("\\s+");
                    if (p.length < 3) continue;
                    String room = p[1];
                    long beforeId = Long.parseLong(p[2]);
                    MoreRoomRequest req = MoreRoomRequest.newBuilder()
                            .setRoom(room).setBeforeId(beforeId).setCount(5).build();
                    MoreRoomResponse resp = stub.getMoreRoom(req);
                    for (StoredMessage m : resp.getMessagesList()) {
                        System.out.println("[soba:" + room + "] [" + m.getId() + "] " + m.getFromUser() +
                                (m.getEdited() ? " (EDITED)" : "") +
                                ": " + m.getText() +
                                (m.getReplyToId() > 0 ? " [reply to " + m.getReplyToId() + " → \"" + m.getReplyExcerpt() + "\"]" : "")
                        );
                    }

                } else if (line.startsWith("/leave ")) {
                    String room = line.split("\\s+", 2)[1];
                    LeaveRoomRequest req = LeaveRoomRequest.newBuilder()
                            .setRoom(room).setUser(username).build();
                    LeaveRoomResponse resp = stub.leaveRoom(req);
                    if (resp.getSuccess()) {
                        System.out.println("[NAPUSTANJE] Napustili ste sobu " + room);
                    } else {
                        System.out.println("[INFO] Niste clan sobe " + room);
                    }

                } else {
                    System.out.println("Nepoznata komanda: " + line);
                }
            } catch (Exception e) {
                System.err.println("Greška: " + e.getMessage());
            }
        }
    }

    private void shutdown() {
        System.out.println("Gašenje klijenta...");
        socketClient.close();
        channel.shutdown();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: UnifiedChatClient @username [socketPort grpcPort]");
            return;
        }
        String username = args[0];
        int socketPort = (args.length > 1) ? Integer.parseInt(args[1]) : 4555;
        int grpcPort   = (args.length > 2) ? Integer.parseInt(args[2]) : 8091;
        new UnifiedChatClient(username, socketPort, grpcPort).start();
    }
}
