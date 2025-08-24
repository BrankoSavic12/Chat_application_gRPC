package rs.raf.pds.v4.z5.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(9000)
                .addService(new ChatControlServiceImpl())
                .build();

        System.out.println("📡 gRPC server started on port 9000");
        server.start();
        server.awaitTermination();
    }
}
