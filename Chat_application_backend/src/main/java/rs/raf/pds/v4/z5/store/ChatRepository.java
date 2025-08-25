package rs.raf.pds.v4.z5.store;

import rs.raf.pds.v4.z5.grpc.StoredMessage;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class ChatRepository {

    // članovi soba
    private final ConcurrentMap<String, Set<String>> roomMembers = new ConcurrentHashMap<>();

    // poruke po sobama + sekvenca ID-jeva
    private final ConcurrentMap<String, Deque<StoredMessage>> roomMessages = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, AtomicLong> roomSeq = new ConcurrentHashMap<>();

    // poruke po DM razgovorima + sekvenca ID-jeva
    private final ConcurrentMap<String, Deque<StoredMessage>> dmMessages = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, AtomicLong> dmSeq = new ConcurrentHashMap<>();

    // ----------------- MULTICAST -----------------
    private final Map<Long, StoredMessage> multicastMessages = new ConcurrentHashMap<>();
    private final Map<Long, List<String>> multicastRecipients = new ConcurrentHashMap<>();
    private final AtomicLong multicastSeq = new AtomicLong(0);

    // poseban ključ za broadcast
    private static final String BROADCAST_ROOM = "#broadcast";

    // ----------------- helperi -----------------
    private static String roomKey(String room) {
        return room.startsWith("#") ? room : "#" + room;
    }

    private static String normUser(String u) {
        return (u == null) ? "@unknown" : (u.startsWith("@") ? u : "@" + u);
    }

    private static String dmKey(String u1, String u2) {
        String a = normUser(u1);
        String b = normUser(u2);
        return (a.compareTo(b) < 0) ? (a + "|" + b) : (b + "|" + a);
    }

    private static String excerpt(String s) {
        if (s == null) return "";
        return s.length() > 30 ? s.substring(0, 30) : s;
    }

    // ----------------- sobe -----------------
    public void ensureRoom(String room) {
        String key = roomKey(room);
        roomMessages.computeIfAbsent(key, k -> new ConcurrentLinkedDeque<>());
        roomSeq.computeIfAbsent(key, k -> new AtomicLong(0));
        roomMembers.computeIfAbsent(key, k -> ConcurrentHashMap.newKeySet());
    }

    public void addMember(String room, String user) {
        ensureRoom(room);
        roomMembers.get(roomKey(room)).add(normUser(user));
    }

    public StoredMessage appendRoom(String room, String sender, String text,
                                    Long replyToId, String replyExcerpt) {
        String key = roomKey(room);
        long id = roomSeq.computeIfAbsent(key, k -> new AtomicLong(0)).incrementAndGet();

        StoredMessage.Builder mb = StoredMessage.newBuilder()
                .setId(id)
                .setRoom(key)
                .setFromUser(normUser(sender))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false);

        if (replyToId != null && replyToId > 0) {
            mb.setReplyToId(replyToId);
            if (replyExcerpt != null) mb.setReplyExcerpt(replyExcerpt);
        }

        StoredMessage msg = mb.build();
        roomMessages.computeIfAbsent(key, k -> new ConcurrentLinkedDeque<>()).addLast(msg);
        return msg;
    }

    public List<StoredMessage> lastN(String room, int n) {
        Deque<StoredMessage> dq = roomMessages.get(roomKey(room));
        if (dq == null || dq.isEmpty()) return Collections.emptyList();
        ArrayList<StoredMessage> out = new ArrayList<>(Math.min(n, dq.size()));
        Iterator<StoredMessage> it = dq.descendingIterator();
        while (it.hasNext() && out.size() < n) out.add(it.next());
        Collections.reverse(out);
        return out;
    }

    public StoredMessage findById(String room, long id) {
        Deque<StoredMessage> dq = roomMessages.get(roomKey(room));
        if (dq == null) return null;
        for (StoredMessage m : dq) {
            if (m.getId() == id) return m;
        }
        return null;
    }

   
    // ----------------- BROADCAST -----------------
    public StoredMessage saveBroadcastMessage(String sender, String text) {
        long id = roomSeq.computeIfAbsent(BROADCAST_ROOM, k -> new AtomicLong(0)).incrementAndGet();

        StoredMessage msg = StoredMessage.newBuilder()
                .setId(id)
                .setRoom(BROADCAST_ROOM)
                .setFromUser(normUser(sender))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false)
                .build();

        roomMessages.computeIfAbsent(BROADCAST_ROOM, k -> new ConcurrentLinkedDeque<>()).addLast(msg);
        return msg;
    }

    public StoredMessage findBroadcastMessageById(long id) {
        return findById(BROADCAST_ROOM, id);
    }

    public StoredMessage editBroadcastMessage(long id, String editor, String newText) {
        return editMessage(BROADCAST_ROOM, id, editor, newText);
    }

    // ----------------- MULTICAST -----------------
    public StoredMessage saveMulticastMessage(String fromUser, List<String> toUsers, String text) {
        long id = multicastSeq.incrementAndGet();

        StoredMessage msg = StoredMessage.newBuilder()
                .setId(id)
                .setRoom("#multicast")
                .setFromUser(normUser(fromUser))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false)
                .build();

        multicastMessages.put(id, msg);
        multicastRecipients.put(id, new ArrayList<>(toUsers));
        return msg;
    }

    public StoredMessage findMulticastMessageById(long id) {
        return multicastMessages.get(id);
    }

    public List<String> getMulticastRecipients(long id) {
        return multicastRecipients.getOrDefault(id, List.of());
    }

    public StoredMessage editMulticastMessage(long id, String editor, String newText) {
        StoredMessage orig = multicastMessages.get(id);
        if (orig != null && orig.getFromUser().equals(normUser(editor))) {
            StoredMessage edited = StoredMessage.newBuilder(orig)
                    .setText(newText)
                    .setEdited(true)
                    .setTs(System.currentTimeMillis())
                    .build();
            multicastMessages.put(id, edited);
            return edited;
        }
        return null;
    }

    // ----------------- Rooms listing -----------------
    public Set<String> listRooms() {
        return roomMembers.keySet();
    }
}
