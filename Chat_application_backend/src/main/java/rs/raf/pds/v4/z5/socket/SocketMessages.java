package rs.raf.pds.v4.z5.socket;

import java.io.Serializable;

public class SocketMessages implements Serializable {
    private String from;
    private String text;

    public SocketMessages() {}

    public SocketMessages(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return from + ": " + text;
    }
}
