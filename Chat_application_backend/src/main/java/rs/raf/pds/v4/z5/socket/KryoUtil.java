package rs.raf.pds.v4.z5.socket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import rs.raf.pds.v4.z5.socket.SocketMessages.*;

public class KryoUtil {

    public static void register(Kryo kryo) {
        // --- Login ---
        kryo.register(CLogin.class);

        // --- DM ---
        kryo.register(CSendDM.class);
        kryo.register(SDeliverDM.class);
        kryo.register(CReplyDM.class);

        // --- Multicast ---
        kryo.register(CSendMulticast.class);
        kryo.register(SDeliverMulticast.class);
        kryo.register(CReplyMulticast.class);

        // --- Broadcast ---
        kryo.register(CSendBroadcast.class);
        kryo.register(SDeliverBroadcast.class);
        kryo.register(CReplyBroadcast.class);

        // --- Group ---
        kryo.register(CSendGroupMsg.class);
        kryo.register(SDeliverGroupMsg.class);
        kryo.register(CReplyGroupMsg.class);   // ✅ novo

        // --- Edit ---
        kryo.register(CEditMessage.class);
        kryo.register(SDeliverEditedMessage.class);

        // --- Invite ---
        kryo.register(SInvite.class);          // ✅ novo

        // --- Wrapper tipovi ---
        kryo.register(Long.class);
        kryo.register(Integer.class);
        kryo.register(Boolean.class);

        // --- Kolekcije ---
        kryo.register(java.util.ArrayList.class);

        // --- String ---
        kryo.register(String.class);
    }

    public static void register(EndPoint endpoint) {
        register(endpoint.getKryo());
    }
}
