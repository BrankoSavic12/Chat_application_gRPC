package rs.raf.pds.v4.z5.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import rs.raf.pds.v4.z5.socket.SocketChatServer;
import rs.raf.pds.v4.z5.store.MessageRepository;

import java.util.*;

public class ServerMain {

    public static void main(String[] args) throws Exception {
        int grpcPort   = (args.length > 0) ? parsePort(args[0], 8091) : 8091;
        int socketPort = (args.length > 1) ? parsePort(args[1], 4555) : 4555;

        // Centralno skladište
        MessageRepository repo = new MessageRepository();

        // Mapu članova soba ostavljamo praznu (popunjava se kad korisnici kreiraju/joinuju sobe)
        Map<String, Set<String>> roomMembers = new HashMap<>();

        // Socket server
        SocketChatServer socket = new SocketChatServer(repo, roomMembers);
        socket.start(socketPort);
        System.out.println("[Socket] SocketChatServer started on port " + socketPort);

        // gRPC server
        Server grpc = ServerBuilder.forPort(grpcPort)
                .addService(new ChatControlServiceImpl(repo, socket))
                .build()
                .start();
        System.out.println("[gRPC] ChatControlService started on port " + grpcPort);

        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("[Shutdown] Stopping servers…");
            try { grpc.shutdown(); } catch (Exception ignore) {}
            try { socket.stop();   } catch (Exception ignore) {}
            System.err.println("[Shutdown] Done.");
        }));

        grpc.awaitTermination();
    }

    private static int parsePort(String s, int def) {
        try {
            int p = Integer.parseInt(s.trim());
            return (p > 0 && p <= 65535) ? p : def;
        } catch (Exception e) {
            return def;
        }
    }
}
