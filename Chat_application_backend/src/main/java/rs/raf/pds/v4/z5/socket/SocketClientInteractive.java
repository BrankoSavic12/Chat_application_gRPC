package rs.raf.pds.v4.z5.socket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import rs.raf.pds.v4.z5.socket.SocketMessages.*;

import java.util.ArrayList;
import java.util.Scanner;

public class SocketClientInteractive {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage: SocketClientInteractive <@username>");
            return;
        }
        String me = args[0].startsWith("@") ? args[0] : "@" + args[0];

        Client c = new Client(16_384, 16_384);
        Kryo kryo = c.getKryo();
        // registracije poruka
        kryo.register(CLogin.class);
        kryo.register(CSendDM.class);
        kryo.register(SDeliverDM.class);
        kryo.register(CSendMulticast.class);
        kryo.register(SDeliverMulticast.class);
        kryo.register(CSendBroadcast.class);
        kryo.register(SDeliverBroadcast.class);
        kryo.register(ArrayList.class);
        kryo.register(String.class);

        c.start();
        c.connect(5_000, "localhost", 4555);

        // login
        CLogin login = new CLogin();
        login.username = me;
        c.sendTCP(login);

        // listener
        c.addListener(new com.esotericsoftware.kryonet.Listener() {
            @Override
            public void received(com.esotericsoftware.kryonet.Connection conn, Object obj) {
                if (obj instanceof SDeliverDM) {
                    SDeliverDM m = (SDeliverDM) obj;
                    String replyInfo = (m.replyToId != 0)
                            ? " (reply to #" + m.replyToId + " → \"" + m.replyExcerpt + "\")"
                            : "";
                    System.out.printf("\n[DM] %s → %s: %s%s%n",
                            m.fromUser, m.toUser, m.text, replyInfo);
                } else if (obj instanceof SDeliverMulticast) {
                    SDeliverMulticast mc = (SDeliverMulticast) obj;
                    System.out.printf("\n[MC] %s → %s: %s%n",
                            mc.fromUser, mc.deliveredTo, mc.text);
                } else if (obj instanceof SDeliverBroadcast) {
                    SDeliverBroadcast b = (SDeliverBroadcast) obj;
                    System.out.printf("\n[BC] %s: %s%n", b.fromUser, b.text);
                }
            }
        });

        // konzola: kucaš komande
        Scanner sc = new Scanner(System.in);
        System.out.println("Konektovan kao " + me + ". Komande:");
        System.out.println("  /dm @user text...             -- privatna poruka");
        System.out.println("  /dmr @user id text...         -- privatna reply poruka");
        System.out.println("  /mc @u1 @u2 ... | text        -- multicast poruka");
        System.out.println("  /bc text                      -- broadcast svima");
        System.out.println("Ctrl+C za izlaz.");

        while (true) {
            String line = sc.nextLine().trim();
            if (line.startsWith("/dmr")) {
                // reply direct message
                String[] parts = line.split("\\s+", 4);
                if (parts.length < 4) {
                    System.out.println("Format: /dmr @user replyToId text");
                    continue;
                }
                CSendDM dm = new CSendDM();
                dm.toUser = parts[1];
                try {
                    dm.replyToId = Long.parseLong(parts[2]);
                } catch (NumberFormatException e) {
                    System.out.println("replyToId mora biti broj.");
                    continue;
                }
                dm.replyExcerpt = "(...)"; // opcionalno može se povući iz istorije
                dm.text = parts[3];
                c.sendTCP(dm);
            } else if (line.startsWith("/dm")) {
                String[] parts = line.split("\\s+", 3);
                if (parts.length < 3) {
                    System.out.println("Format: /dm @user text");
                    continue;
                }
                CSendDM dm = new CSendDM();
                dm.toUser = parts[1];
                dm.text = parts[2];
                c.sendTCP(dm);
            } else if (line.startsWith("/mc")) {
                int sep = line.indexOf("|");
                if (sep == -1) {
                    System.out.println("Format: /mc @u1 @u2 ... | text");
                    continue;
                }
                String[] users = line.substring(3, sep).trim().split("\\s+");
                String text = line.substring(sep + 1).trim();
                CSendMulticast mc = new CSendMulticast();
                mc.toUsers = new ArrayList<>();
                for (String u : users) {
                    if (u != null && !u.trim().isEmpty()) {
                        mc.toUsers.add(u.trim());
                    }
                }
                mc.text = text;
                c.sendTCP(mc);
            } else if (line.startsWith("/bc")) {
                String text = line.substring(3).trim();
                if (text.isEmpty()) {
                    System.out.println("Format: /bc text");
                    continue;
                }
                CSendBroadcast bc = new CSendBroadcast();
                bc.text = text;
                c.sendTCP(bc);
            } else {
                System.out.println("Nepoznata komanda.");
            }
        }
    }
}
