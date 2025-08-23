package rs.raf.pds.v4.z5.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.io.InputStream;


public class SocketChatServer {

    private static final int PORT = 9000;
    private static final List<Socket> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("📡 Server started on port " + PORT);

        while (true) {
            Socket client = serverSocket.accept();
            clients.add(client);
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

                broadcast(msg, client);
            }
        } catch (IOException e) {
            System.out.println("❌ Client disconnected: " + client);
        } finally {
            clients.remove(client);
        }
    }

    private static void broadcast(SocketMessages msg, Socket sender) {
        byte[] data = KryoUtil.serialize(msg);

        for (Socket c : clients) {
            if (!c.equals(sender)) { // šalje svima osim pošiljaocu
                try {
                    OutputStream out = c.getOutputStream();
                    out.write(data);
                    out.flush();
                } catch (IOException e) {
                    System.out.println("⚠️ Error sending to client: " + c);
                }
            }
        }
    }
}
