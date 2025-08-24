package rs.raf.pds.v4.z5.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
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
            System.out.print("Poruka (npr. Hello ili @user: Hi ili @user1,user2: Hi): ");
            String input = scanner.nextLine();

            List<String> to = null;
            String text = input;

            // ako poruka počinje sa @ -> private ili multicast
            if (input.startsWith("@")) {
                int idx = input.indexOf(":");
                if (idx > 0) {
                    String usersPart = input.substring(1, idx); // user ili user1,user2
                    to = Arrays.asList(usersPart.split(","));
                    text = input.substring(idx + 1).trim();
                }
            }

            SocketMessages msg = new SocketMessages(name, text, to);
            byte[] data = KryoUtil.serialize(msg);
            out.write(data);
            out.flush();
        }
    }
}
