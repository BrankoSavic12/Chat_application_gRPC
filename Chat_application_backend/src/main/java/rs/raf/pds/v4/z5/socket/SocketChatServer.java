package rs.raf.pds.v4.z5.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SocketChatServer {

    private static final int PORT = 9000;

    // mapiramo username -> socket
    private static final ConcurrentHashMap<String, Socket> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("📡 Server started on port " + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("✅ New client connected: " + client);

            new Thread(() -> handleClient(client)).start();
        }
    }

    private static void handleClient(Socket client) {
        try {
            InputStream input = client.getInputStream();
            byte[] buffer = new byte[4096];

            while (true) {
                int read = input.read(buffer);
                if (read == -1) break;

                SocketMessages msg = KryoUtil.deserialize(buffer, SocketMessages.class);
                System.out.println("📩 Received: " + msg);

                // ako korisnik nije registrovan, zapamti ga
                clients.putIfAbsent(msg.getFrom(), client);

                // šaljemo dalje
                routeMessage(msg, client);
            }
        } catch (IOException e) {
            System.out.println("❌ Client disconnected: " + client);
        } finally {
            clients.values().remove(client);
        }
    }

    private static void routeMessage(SocketMessages msg, Socket sender) {
        byte[] data = KryoUtil.serialize(msg);

        if (msg.getTo() == null || msg.getTo().isEmpty()) {
            // broadcast svima osim pošiljaocu
            for (Socket c : clients.values()) {
                if (!c.equals(sender)) {
                    sendToClient(c, data);
                }
            }
        } else {
            // multicast ili 1-1
            for (String user : msg.getTo()) {
                Socket c = clients.get(user);
                if (c != null && !c.equals(sender)) {
                    sendToClient(c, data);
                }
            }
        }
    }

    private static void sendToClient(Socket c, byte[] data) {
        try {
            OutputStream out = c.getOutputStream();
            out.write(data);
            out.flush();
        } catch (IOException e) {
            System.out.println("⚠️ Error sending to client: " + c);
        }
    }
}
