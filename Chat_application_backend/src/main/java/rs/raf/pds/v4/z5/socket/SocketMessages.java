package rs.raf.pds.v4.z5.socket;

import java.util.ArrayList;

public class SocketMessages {

    // --- LOGIN ---
    public static class CLogin {
        public String username;
    }

    // --- DM ---
    public static class CSendDM {
        public String toUser;        // primalac (@korisnik)
        public String fromUser;      // pošiljalac
        public String text;
        public long   replyToId;     // 0 ako nema reply
        public String replyExcerpt;  // excerpt originala
    }

    public static class SDeliverDM {
        public long   id;
        public String fromUser;
        public String toUser;
        public String text;
        public long   tsEpochMs;
        public boolean edited;
        public long   replyToId;
        public String replyExcerpt;
    }

    public static class CReplyDM {
        public String fromUser;   // ko šalje reply
        public long   replyToId;  // ID poruke na koju se odgovara
        public String text;       // tekst odgovora
    }

    // --- MULTICAST ---
    public static class CSendMulticast {
        public String fromUser;
        public ArrayList<String> toUsers; // svi primaoci
        public String text;
        public long   replyToId;   // ako se odgovara na neku poruku
        public String replyExcerpt;
    }

    /**
     * Multicast sada tretiramo kao jednu poruku (jedan id) sa listom primaoca.
     */
    public static class SDeliverMulticast {
        public long   id;                 // jedinstveni ID multicast poruke
        public String fromUser;
        public String text;
        public long   tsEpochMs;
        public boolean edited;
        public long   replyToId;
        public String replyExcerpt;
        public ArrayList<String> deliveredTo; // svi primaoci
    }

    public static class CReplyMulticast {
        public String fromUser;   // ko šalje reply
        public long   replyToId;  // ID multicast poruke
        public String text;       // tekst odgovora
    }

    // --- BROADCAST ---
    public static class CSendBroadcast {
        public String fromUser;
        public String text;
    }

    public static class SDeliverBroadcast {
        public long   id;
        public String fromUser;
        public String text;
        public long   tsEpochMs;
        public boolean edited;      // ako je editovana poruka
        public long   replyToId;    // ako je neko odgovorio na nju
        public String replyExcerpt; // isecak originalne poruke
    }

    public static class CReplyBroadcast {
        public String fromUser;   // ko šalje reply
        public long   replyToId;  // ID broadcast poruke
        public String text;       // tekst odgovora
    }

    // --- GROUP MSG ---
    public static class CSendGroupMsg {
        public String room;
        public String fromUser;
        public String text;
        public long   replyToId;
        public String replyExcerpt;
    }

    public static class SDeliverGroupMsg {
        public long   id;
        public String room;
        public String fromUser;
        public String text;
        public long   tsEpochMs;
        public boolean edited;
        public long   replyToId;
        public String replyExcerpt;
    }

    // Reply u okviru sobe
    public static class CReplyGroupMsg {
        public String fromUser;   // ko šalje reply
        public String room;       // soba
        public long   replyToId;  // ID poruke na koju se odgovara
        public String text;       // tekst odgovora
    }

    // --- EDIT MSG ---
    public static class CEditMessage {
        public long   id;
        /**
         * "#broadcast" ili "#multicast" ili naziv sobe.
         * "" znači DM (direktna poruka).
         */
        public String room;
        public String fromUser;
        public String toUser;    // za DM
        public String newText;
    }

    public static class SDeliverEditedMessage {
        public long   id;
        public String room;      // "#broadcast", "#multicast", soba ili "" => DM
        public String fromUser;
        public String text;
        public long   tsEpochMs;
        public boolean edited;
        public long   replyToId;
        public String replyExcerpt;
    }

    // --- INVITE ---
    public static class SInvite {
        public String room;
        public String invitedUser;
        public String invitedBy;
        public long   tsEpochMs;
    }
}
