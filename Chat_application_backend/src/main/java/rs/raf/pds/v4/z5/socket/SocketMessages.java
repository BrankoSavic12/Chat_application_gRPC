package rs.raf.pds.v4.z5.socket;

import java.io.Serializable;
import java.util.List;

public class SocketMessages implements Serializable {
    private String from;
    private String text;
    private List<String> to; // null ili prazno = broadcast, 1 korisnik = 1-1, više korisnika = multicast

    public SocketMessages() {}

    public SocketMessages(String from, String text, List<String> to) {
        this.from = from;
        this.text = text;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    public List<String> getTo() {
        return to;
    }

    @Override
    public String toString() {
        if (to == null || to.isEmpty()) {
            return "[BROADCAST] " + from + ": " + text;
        } else if (to.size() == 1) {
            return "[PRIVATE to " + to.get(0) + "] " + from + ": " + text;
        } else {
            return "[MULTICAST to " + to + "] " + from + ": " + text;
        }
    }
}
