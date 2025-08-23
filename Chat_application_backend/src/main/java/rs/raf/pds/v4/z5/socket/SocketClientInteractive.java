package rs.raf.pds.v4.z5.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClientInteractive {

    private static final String HOST = "localhost";
    private static final int PORT = 9000;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Unesi svoje ime: ");
        String name = scanner.nextLine();

        Socket socket = new Socket(HOST, PORT);
        System.out.println("🔗 Connected to server");

        // Thread za prijem poruka
        new Thread(() -> {
            try {
                InputStream in = socket.getInputStream();
                byte[] buffer = new byte[4096];

                while (true) {
                    int read = in.read(buffer);
                    if (read == -1) break;

                    SocketMessages msg = KryoUtil.deserialize(buffer, SocketMessages.class);
                    System.out.println(msg);
                }
            } catch (IOException e) {
                System.out.println("❌ Disconnected from server");
            }
        }).start();

        // Slanje poruka
        OutputStream out = socket.getOutputStream();
        while (true) {
            String text = scanner.nextLine();
            SocketMessages msg = new SocketMessages(name, text);
            byte[] data = KryoUtil.serialize(msg);
            out.write(data);
            out.flush();
        }
    }
}
