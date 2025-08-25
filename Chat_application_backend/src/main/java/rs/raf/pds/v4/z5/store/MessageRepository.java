package rs.raf.pds.v4.z5.store;

import rs.raf.pds.v4.z5.grpc.StoredMessage;
import rs.raf.pds.v4.z5.grpc.PendingInvite;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MessageRepository {

    private final AtomicLong idGen = new AtomicLong(1);

    // -------------------- ROOM i DM poruke --------------------
    private final Map<String, List<StoredMessage>> roomMessages = new ConcurrentHashMap<>();
    private final Map<String, List<StoredMessage>> dmMessages   = new ConcurrentHashMap<>();

    private static String dmKey(String a, String b) {
        a = normalize(a);
        b = normalize(b);
        return a.compareTo(b) < 0 ? a + "|" + b : b + "|" + a;
    }

    private static String normalize(String u) {
        if (u == null || u.trim().isEmpty()) return "@unknown";
        return u.startsWith("@") ? u.trim() : "@" + u.trim();
    }

    // ---- ROOM poruke ----
    public StoredMessage saveRoomMessage(String room, String sender, String text, long replyToId, String replyExcerpt) {
        StoredMessage.Builder mb = StoredMessage.newBuilder()
                .setId(idGen.getAndIncrement())
                .setRoom(room)
                .setFromUser(normalize(sender))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false);

        if (replyToId > 0) {
            mb.setReplyToId(replyToId);
            if (replyExcerpt != null) mb.setReplyExcerpt(replyExcerpt);
        }

        StoredMessage msg = mb.build();
        roomMessages.computeIfAbsent(room, k -> new ArrayList<>()).add(msg);
        return msg;
    }

    public List<StoredMessage> lastN(String room, int n) {
        List<StoredMessage> msgs = roomMessages.getOrDefault(room, Collections.emptyList());
        int from = Math.max(0, msgs.size() - n);
        return new ArrayList<>(msgs.subList(from, msgs.size()));
    }

    public List<StoredMessage> pageBefore(String room, long beforeId, int count) {
        List<StoredMessage> msgs = roomMessages.getOrDefault(room, Collections.emptyList());
        List<StoredMessage> filtered = new ArrayList<>();
        for (int i = msgs.size() - 1; i >= 0 && filtered.size() < count; i--) {
            if (msgs.get(i).getId() < beforeId) filtered.add(msgs.get(i));
        }
        Collections.reverse(filtered);
        return filtered;
    }

    public StoredMessage findById(String room, long id) {
        return roomMessages.getOrDefault(room, Collections.emptyList())
                .stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }


    // ---- DM poruke ----
    public StoredMessage saveDM(String a, String b, String sender, String text) {
        return saveDM(a, b, sender, text, 0, null);
    }

    public StoredMessage saveDM(String a, String b, String sender, String text, long replyToId, String replyExcerpt) {
        String key = dmKey(a, b);
        StoredMessage.Builder mb = StoredMessage.newBuilder()
                .setId(idGen.getAndIncrement())
                .setRoom(key) // ključ razgovora
                .setFromUser(normalize(sender))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false);

        if (replyToId > 0) {
            mb.setReplyToId(replyToId);
            if (replyExcerpt != null) mb.setReplyExcerpt(replyExcerpt);
        }

        StoredMessage msg = mb.build();
        dmMessages.computeIfAbsent(key, k -> new ArrayList<>()).add(msg);
        return msg;
    }

    public List<StoredMessage> lastDM(String a, String b, int n) {
        String key = dmKey(a, b);
        List<StoredMessage> msgs = dmMessages.getOrDefault(key, Collections.emptyList());
        int from = Math.max(0, msgs.size() - n);
        return new ArrayList<>(msgs.subList(from, msgs.size()));
    }

    public List<StoredMessage> pageDMBefore(String a, String b, long beforeId, int count) {
        String key = dmKey(a, b);
        List<StoredMessage> msgs = dmMessages.getOrDefault(key, Collections.emptyList());
        List<StoredMessage> filtered = new ArrayList<>();
        for (int i = msgs.size() - 1; i >= 0 && filtered.size() < count; i--) {
            if (msgs.get(i).getId() < beforeId) filtered.add(msgs.get(i));
        }
        Collections.reverse(filtered);
        return filtered;
    }

    public StoredMessage findDMById(String a, String b, long id) {
        String key = dmKey(a, b);
        return dmMessages.getOrDefault(key, Collections.emptyList())
                .stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** 🔹 Globalna pretraga DM poruke po ID-ju */
    public StoredMessage findDMByIdGlobal(long id) {
        for (List<StoredMessage> list : dmMessages.values()) {
            for (StoredMessage m : list) {
                if (m.getId() == id) {
                    return m;
                }
            }
        }
        return null;
    }

  

    // ---- BROADCAST poruke ----
    private final List<StoredMessage> broadcastMessages = new ArrayList<>();

    public StoredMessage saveBroadcastMessage(String sender, String text) {
        StoredMessage msg = StoredMessage.newBuilder()
                .setId(idGen.getAndIncrement())
                .setRoom("#broadcast")
                .setFromUser(normalize(sender))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false)
                .build();
        synchronized (broadcastMessages) {
            broadcastMessages.add(msg);
        }
        return msg;
    }

    public StoredMessage findBroadcastMessageById(long id) {
        synchronized (broadcastMessages) {
            return broadcastMessages.stream()
                    .filter(m -> m.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
    }

   
    // ---- MULTICAST poruke ----
    private final Map<Long, StoredMessage> multicastMessages = new ConcurrentHashMap<>();
    private final Map<Long, List<String>> multicastRecipients = new ConcurrentHashMap<>();

    public StoredMessage saveMulticastMessage(String fromUser, List<String> toUsers, String text) {
        return saveMulticastMessage(fromUser, toUsers, text, 0, null);
    }

    public StoredMessage saveMulticastMessage(String fromUser, List<String> toUsers, String text, long replyToId, String replyExcerpt) {
        long id = idGen.getAndIncrement();
        StoredMessage.Builder mb = StoredMessage.newBuilder()
                .setId(id)
                .setRoom("#multicast")
                .setFromUser(normalize(fromUser))
                .setText(text)
                .setTs(System.currentTimeMillis())
                .setEdited(false);

        if (replyToId > 0) {
            mb.setReplyToId(replyToId);
            if (replyExcerpt != null) mb.setReplyExcerpt(replyExcerpt);
        }

        StoredMessage msg = mb.build();
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

   

    // -------------------- SOBE --------------------
    private final Map<String, Set<String>> roomMembers = new ConcurrentHashMap<>();
    private final Map<String, Long> roomCreated = new ConcurrentHashMap<>();
    private final Map<String, String> roomOwners = new ConcurrentHashMap<>(); // 🔹 vlasnici soba

    public void createRoom(String room, String owner) {
        roomCreated.putIfAbsent(room, System.currentTimeMillis());
        roomMembers.computeIfAbsent(room, k -> ConcurrentHashMap.newKeySet()).add(owner);
        roomOwners.putIfAbsent(room, normalize(owner)); // zapamti vlasnika
    }

    public void joinRoom(String room, String user) {
        roomMembers.computeIfAbsent(room, k -> ConcurrentHashMap.newKeySet()).add(user);
    }

    public void leaveRoom(String room, String user) {
        roomMembers.computeIfAbsent(room, k -> ConcurrentHashMap.newKeySet()).remove(user);
    }

    public Set<String> getMembers(String room) {
        return roomMembers.getOrDefault(room, Set.of());
    }

    public List<String> listRooms() {
        return new ArrayList<>(roomMembers.keySet());
    }

    public long getRoomCreatedAt(String room) {
        return roomCreated.getOrDefault(room, System.currentTimeMillis());
    }

    public String getRoomOwner(String room) {
        return roomOwners.getOrDefault(room, "@system");
    }

    // -------------------- INVITE --------------------
    private final Map<String, List<PendingInvite>> pendingInvites = new ConcurrentHashMap<>();

    public void inviteUser(String room, String invitedBy, String invitedUser) {
        PendingInvite inv = PendingInvite.newBuilder()
                .setRoom(room)
                .setInvitedUser(invitedUser)
                .setInvitedBy(invitedBy)
                .setTs(System.currentTimeMillis())
                .build();
        pendingInvites.computeIfAbsent(invitedUser, k -> new ArrayList<>()).add(inv);
    }

    public List<PendingInvite> getInvites(String user) {
        return pendingInvites.getOrDefault(user, List.of());
    }

    public void acceptInvite(String room, String user) {
        joinRoom(room, user);
        pendingInvites.computeIfAbsent(user, k -> new ArrayList<>())
                .removeIf(i -> i.getRoom().equals(room));
    }

    public void rejectInvite(String room, String user) {
        pendingInvites.computeIfAbsent(user, k -> new ArrayList<>())
                .removeIf(i -> i.getRoom().equals(room));
    }
}
