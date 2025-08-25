package rs.raf.pds.v4.z5.socket;

import java.util.ArrayList;

public class SocketMessages {

    // --- LOGIN ---
    public static class CLogin {
        public String username;
    }

    // --- DM ---
    public static class CSendDM {
        public String toUser;       
        public String fromUser;      
        public String text;
        public long   replyToId;     
        public String replyExcerpt; 
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
        public String fromUser;   
        public long   replyToId;  
        public String text;       
    }

    // --- MULTICAST ---
    public static class CSendMulticast {
        public String fromUser;
        public ArrayList<String> toUsers; 
        public String text;
        public long   replyToId;  
        public String replyExcerpt;
    }

   
    public static class SDeliverMulticast {
        public long   id;          
        public String fromUser;
        public String text;
        public long   tsEpochMs;
        public boolean edited;
        public long   replyToId;
        public String replyExcerpt;
        public ArrayList<String> deliveredTo; 
    }

    public static class CReplyMulticast {
        public String fromUser; 
        public long   replyToId; 
        public String text;      
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
        public boolean edited;     
        public long   replyToId;   
        public String replyExcerpt; 
    }

    public static class CReplyBroadcast {
        public String fromUser;  
        public long   replyToId;  
        public String text;     
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

  
    public static class CReplyGroupMsg {
        public String fromUser;   
        public String room;       
        public long   replyToId; 
        public String text;     
    }

    // --- EDIT MSG ---
    public static class CEditMessage {
        public long   id;
    
        public String room;
        public String fromUser;
        public String toUser;  
        public String newText;
    }

    public static class SDeliverEditedMessage {
        public long   id;
        public String room;    
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
