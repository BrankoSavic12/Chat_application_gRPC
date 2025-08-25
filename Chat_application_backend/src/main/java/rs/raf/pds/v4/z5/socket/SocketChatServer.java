package rs.raf.pds.v4.z5.socket;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import rs.raf.pds.v4.z5.socket.SocketMessages.*;
import rs.raf.pds.v4.z5.grpc.StoredMessage;
import rs.raf.pds.v4.z5.store.MessageRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SocketChatServer {

    private final Server server = new Server(16_384, 16_384);
    private final MessageRepository repo;

    private final Map<String, Connection> user2conn = new ConcurrentHashMap<>();
    private final Map<Connection, String> conn2user = new ConcurrentHashMap<>();
    private final Map<String, Set<String>> roomMembers;

    public SocketChatServer(MessageRepository repo, Map<String, Set<String>> roomMembers) {
        this.repo = repo;
        this.roomMembers = roomMembers;
        KryoUtil.register(server);
        attachListeners();
    }

    public void start(int tcpPort) throws IOException {
        server.start();
        server.bind(tcpPort);
        System.out.println("[KryoNet] SocketChatServer started on TCP " + tcpPort);
    }

    public void stop() {
        server.stop();
    }

    // --------- Listeners ----------
    private void attachListeners() {
        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection connection) {
                String u = conn2user.remove(connection);
                if (u != null) user2conn.remove(u);
            }

            @Override
            public void received(Connection c, Object obj) {
                try {
                    if (obj instanceof CLogin) {
                        handleLogin(c, (CLogin) obj);
                    } else if (obj instanceof CSendDM) {
                        handleDM(c, (CSendDM) obj);
                    } else if (obj instanceof CReplyDM) {
                        handleReplyDM(c, (CReplyDM) obj);
                    } else if (obj instanceof CSendMulticast) {
                        handleMulticast(c, (CSendMulticast) obj);
                    } else if (obj instanceof CReplyMulticast) {
                        handleReplyMulticast(c, (CReplyMulticast) obj);
                    } else if (obj instanceof CSendBroadcast) {
                        handleBroadcast(c, (CSendBroadcast) obj);
                    } else if (obj instanceof CReplyBroadcast) {
                        handleReplyBroadcast(c, (CReplyBroadcast) obj);
                    } else if (obj instanceof CSendGroupMsg) {
                        handleGroupMsg(c, (CSendGroupMsg) obj);
                    } else if (obj instanceof CReplyGroupMsg) {
                        handleReplyGroupMsg(c, (CReplyGroupMsg) obj);
                    } else if (obj instanceof CEditMessage) {
                        handleEdit(c, (CEditMessage) obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // --------- Handlers ----------
    private void handleLogin(Connection conn, CLogin m) {
        String user = normUser(m.username);
        conn2user.put(conn, user);
        user2conn.put(user, conn);
        System.out.println("[KryoNet] login: " + user);
    }

    private void handleDM(Connection conn, CSendDM m) {
        String from = sessionOr(conn2user.get(conn), m.fromUser);
        String to = normUser(m.toUser);
        if (from.equalsIgnoreCase(to)) return;

        StoredMessage saved = repo.saveDM(from, to, from, m.text, m.replyToId, safe(m.replyExcerpt));

        SDeliverDM out = toDM(saved, from, to);
        Connection target = user2conn.get(to);
        if (target != null) target.sendTCP(out);
        conn.sendTCP(out);
    }

    private void handleReplyDM(Connection conn, CReplyDM m) {
        String from = sessionOr(conn2user.get(conn), m.fromUser);

        for (String partner : user2conn.keySet()) {
            List<StoredMessage> last = repo.lastDM(from, partner, 50);
            for (StoredMessage orig : last) {
                if (orig.getId() == m.replyToId) {
                    String to = orig.getFromUser().equals(from) ? partner : orig.getFromUser();
                    StoredMessage saved = repo.saveDM(from, to, from, m.text, orig.getId(), excerpt(orig.getText()));

                    SDeliverDM out = toDM(saved, from, to);
                    Connection tconn = user2conn.get(to);
                    if (tconn != null) tconn.sendTCP(out);
                    conn.sendTCP(out);
                    return;
                }
            }
        }
    }

    private void handleMulticast(Connection conn, CSendMulticast m) {
        String from = sessionOr(conn2user.get(conn), m.fromUser);

        StoredMessage saved = repo.saveMulticastMessage(from, m.toUsers, m.text, m.replyToId, safe(m.replyExcerpt));

        SDeliverMulticast out = new SDeliverMulticast();
        out.id = saved.getId();
        out.fromUser = from;
        out.text = saved.getText();
        out.tsEpochMs = saved.getTs();
        out.edited = saved.getEdited();
        out.replyToId = saved.getReplyToId();
        out.replyExcerpt = saved.getReplyExcerpt();
        out.deliveredTo = new ArrayList<>(m.toUsers);

        for (String u : m.toUsers) {
            Connection target = user2conn.get(normUser(u));
            if (target != null) target.sendTCP(out);
        }
        conn.sendTCP(out);
    }

    private void handleReplyMulticast(Connection conn, CReplyMulticast m) {
        String from = sessionOr(conn2user.get(conn), m.fromUser);

        StoredMessage orig = repo.findMulticastMessageById(m.replyToId);
        if (orig == null) return;

        String author = orig.getFromUser();
        StoredMessage saved = repo.saveDM(from, author, from, m.text, orig.getId(), "multicast-reply");

        SDeliverDM out = toDM(saved, from, author);
        Connection tconn = user2conn.get(author);
        if (tconn != null) tconn.sendTCP(out);
        conn.sendTCP(out);
    }

    private void handleBroadcast(Connection conn, CSendBroadcast m) {
        String from = sessionOr(conn2user.get(conn), m.fromUser);

        StoredMessage saved = repo.saveBroadcastMessage(from, m.text);

        SDeliverBroadcast out = new SDeliverBroadcast();
        out.id = saved.getId();
        out.fromUser = from;
        out.text = saved.getText();
        out.tsEpochMs = saved.getTs();
        out.edited = saved.getEdited();
        out.replyToId = saved.getReplyToId();
        out.replyExcerpt = saved.getReplyExcerpt();

        for (Connection target : user2conn.values()) target.sendTCP(out);
    }


    private void handleGroupMsg(Connection conn, CSendGroupMsg m) {
        String from = sessionOr(conn2user.get(conn), m.fromUser);

        StoredMessage saved = repo.saveRoomMessage(m.room, from, m.text, m.replyToId, safe(m.replyExcerpt));

        deliverGroupMessage(saved);
    }


   
        if (updated == null) return;

        SDeliverEditedMessage out = new SDeliverEditedMessage();
        out.id = updated.getId();
        out.room = updated.getRoom();
        out.fromUser = from;
        out.text = updated.getText();
        out.tsEpochMs = updated.getTs();
        out.edited = true;
        out.replyToId = updated.getReplyToId();
        out.replyExcerpt = updated.getReplyExcerpt();

        if ("#broadcast".equals(updated.getRoom())) {
            for (Connection target : user2conn.values()) target.sendTCP(out);
        } else if ("#multicast".equals(updated.getRoom())) {
            for (String u : repo.getMulticastRecipients(updated.getId())) {
                Connection t = user2conn.get(u);
                if (t != null) t.sendTCP(out);
            }
            Connection f = user2conn.get(from);
            if (f != null) f.sendTCP(out);
        } else if (updated.getRoom() != null && updated.getRoom().contains("|")) {
            String[] parts = updated.getRoom().split("\\|");
            for (String u : parts) {
                Connection t = user2conn.get(u);
                if (t != null) t.sendTCP(out);
            }
        } else {
            Set<String> members = roomMembers.getOrDefault(updated.getRoom(), Set.of());
            for (String member : members) {
                Connection target = user2conn.get(member);
                if (target != null) target.sendTCP(out);
            }
        }
    }

    // --------- utils ----------
    private static String normUser(String u) {
        if (u == null || u.trim().isEmpty()) return "@unknown";
        return u.startsWith("@") ? u.trim() : ("@" + u.trim());
    }

    private static String safe(String s) {
        return (s == null) ? "" : s;
    }

    private static String excerpt(String s) {
        if (s == null) return "";
        return s.length() > 30 ? s.substring(0, 30) : s;
    }

    private static String sessionOr(String sessionUser, String fallback) {
        String u = (sessionUser != null && !sessionUser.trim().isEmpty()) ? sessionUser : fallback;
        return normUser(u);
    }

    private static SDeliverDM toDM(StoredMessage saved, String from, String to) {
        SDeliverDM out = new SDeliverDM();
        out.id = saved.getId();
        out.fromUser = from;
        out.toUser = to;
        out.text = saved.getText();
        out.tsEpochMs = saved.getTs();
        out.edited = saved.getEdited();
        out.replyToId = saved.getReplyToId();
        out.replyExcerpt = saved.getReplyExcerpt();
        return out;
    }

    private static String extractPartnerFromKey(String key, String me) {
        if (key == null || !key.contains("|")) return "";
        String[] parts = key.split("\\|");
        return parts[0].equals(me) ? parts[1] : parts[0];
    }

    public void addUserToRoom(String room, String user) {
        roomMembers.computeIfAbsent(room, r -> ConcurrentHashMap.newKeySet()).add(user);
    }

    public void removeUserFromRoom(String room, String user) {
        Set<String> members = roomMembers.get(room);
        if (members != null) {
            members.remove(user);
            if (members.isEmpty()) roomMembers.remove(room);
        }
    }

    public void deliverGroupMessage(StoredMessage saved) {
        if (saved.getRoom() == null || saved.getRoom().isEmpty()) return;

        Set<String> members = roomMembers.getOrDefault(saved.getRoom(), Set.of());

        for (String member : members) {
            Connection target = user2conn.get(normUser(member));
            if (target != null) {
                SDeliverGroupMsg out = new SDeliverGroupMsg();
                out.id = saved.getId();
                out.room = saved.getRoom();
                out.fromUser = saved.getFromUser();
                out.text = saved.getText();
                out.tsEpochMs = saved.getTs();
                out.edited = saved.getEdited();
                out.replyToId = saved.getReplyToId();
                out.replyExcerpt = saved.getReplyExcerpt();

                target.sendTCP(out);
            }
        }
    }


    


    // --------- Invites ----------
    public void deliverInvite(String room, String invitedUser, String invitedBy) {
        Connection target = user2conn.get(normUser(invitedUser));
        if (target != null) {
            SInvite out = new SInvite();
            out.room = room;
            out.invitedUser = invitedUser;
            out.invitedBy = invitedBy;
            out.tsEpochMs = System.currentTimeMillis();
            target.sendTCP(out);
        }
    }
}
