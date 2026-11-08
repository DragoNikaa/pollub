package org.example.network;

import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final FileService fileService;

    @Override
    public void run() {
        try (Socket socket = this.socket;
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            String encryptedMessage;

            while ((encryptedMessage = reader.readLine()) != null) {
                String request = VigenereCipher.decrypt(encryptedMessage);
                System.out.println("Received request: " + request);

                if (!handleRequest(request, writer)) break;
            }
        } catch (IOException e) {
            System.out.println("Client communication error: " + e.getMessage());
        } finally {
            System.out.println("Client connection closed.");
        }
    }

    private boolean handleRequest(String request, PrintWriter writer) throws IOException {
        if (request.equals("LIST")) {
            handleListRequest(writer);
        } else if (request.startsWith("GET ")) {
            String fileName = request.substring(4).trim();
            handleDownloadRequest(fileName, writer);
        } else if (request.equals("EXIT")) {
            sendEncryptedMessage(writer, "BYE");
            return false;
        } else {
            sendEncryptedMessage(writer, "ERROR: Unknown command");
        }
        return true;
    }

    private void handleListRequest(PrintWriter writer) throws IOException {
        List<String> files;
        try {
            files = fileService.listFiles();
        } catch (IOException e) {
            sendEncryptedMessage(writer, "ERROR: Could not list files");
            return;
        }

        sendEncryptedMessage(writer, "OK " + files.size());
        for (String file : files) sendEncryptedMessage(writer, file);
    }

    private void handleDownloadRequest(String fileName, PrintWriter writer) throws IOException {
        Optional<Path> file = fileService.findFile(fileName);
        if (file.isEmpty()) {
            sendEncryptedMessage(writer, "ERROR: File not found");
            return;
        }

        try {
            byte[] fileData = Files.readAllBytes(file.get());
            String encodedData = Base64.getEncoder().encodeToString(fileData);

            sendEncryptedMessage(writer, "OK " + fileData.length);
            sendEncryptedMessage(writer, encodedData);

            System.out.println("File sent: " + fileName);
        } catch (IOException e) {
            sendEncryptedMessage(writer, "ERROR: Could not read file");
        }
    }

    private void sendEncryptedMessage(PrintWriter writer, String message) throws IOException {
        String encryptedMessage = VigenereCipher.encrypt(message);
        writer.println(encryptedMessage);

        if (writer.checkError()) {
            throw new IOException("Could not send message to client");
        }
    }
}