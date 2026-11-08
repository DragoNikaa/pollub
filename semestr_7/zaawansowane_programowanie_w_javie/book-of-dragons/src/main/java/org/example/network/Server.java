package org.example.network;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;

@RequiredArgsConstructor
public class Server {
    private static final int DEFAULT_PORT = 2004;
    private static final Path SHARED_DIRECTORY = Path.of("output");

    private final int port;
    private final FileService fileService;

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        try {
            FileService fileService = new FileService(SHARED_DIRECTORY);

            Server server = new Server(port, fileService);
            server.start();
        } catch (IOException e) {
            System.err.println("Could not start the server: " + e.getMessage());
        }
    }

    private void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                startClientHandler(clientSocket);
            }
        }
    }

    private void startClientHandler(Socket clientSocket) {
        Thread clientThread = new Thread(new ClientHandler(clientSocket, fileService));
        clientThread.start();
    }
}