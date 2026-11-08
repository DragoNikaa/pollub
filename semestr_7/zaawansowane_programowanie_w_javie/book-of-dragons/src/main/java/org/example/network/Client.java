package org.example.network;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Scanner;

@RequiredArgsConstructor
public class Client {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 2004;

    private final String serverHost;
    private final int serverPort;
    private final Path downloadDirectory;

    private BufferedReader reader;
    private PrintWriter writer;

    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : DEFAULT_HOST;
        int port = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_PORT;
        Path downloadDirectory = args.length > 2 ? Path.of(args[2]) : Path.of("downloads");

        Client client = new Client(host, port, downloadDirectory);
        client.start();
    }

    private void start() {
        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            this.reader = reader;
            this.writer = writer;

            System.out.println("Connected to server.");
            runCommandLoop(scanner);
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
    }

    private void runCommandLoop(Scanner scanner) throws IOException {
        boolean running = true;

        while (running) {
            printMenu();

            if (!scanner.hasNextLine()) break;

            String command = scanner.nextLine().trim();
            running = handleCommand(command);
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("Available commands:");
        System.out.println("LIST - display available files");
        System.out.println("GET filename - download a file");
        System.out.println("EXIT - close connection");
        System.out.print("> ");
    }

    private boolean handleCommand(String command) throws IOException {
        if (command.equalsIgnoreCase("LIST")) {
            requestFileList();
        } else if (command.regionMatches(true, 0, "GET ", 0, 4)) {
            String fileName = command.substring(4).trim();

            if (isValidFileName(fileName)) downloadFile(fileName);
            else System.out.println("Invalid file name.");
        } else if (command.equalsIgnoreCase("EXIT")) {
            sendEncryptedMessage("EXIT");

            String response = readDecryptedMessage();
            System.out.println("Server: " + response);
            return false;
        } else {
            System.out.println("Unknown command.");
        }
        return true;
    }

    private void requestFileList() throws IOException {
        sendEncryptedMessage("LIST");
        receiveFileList();
    }

    private void receiveFileList() throws IOException {
        String response = readDecryptedMessage();
        if (!response.startsWith("OK ")) {
            System.out.println("Server: " + response);
            return;
        }

        System.out.println("Available files:");
        for (int i = 0; i < parseFileCount(response); i++) {
            String fileName = readDecryptedMessage();
            System.out.println("- " + fileName);
        }
    }

    private int parseFileCount(String response) throws IOException {
        try {
            int count = Integer.parseInt(response.substring(3));
            if (count < 0) throw new NumberFormatException();
            return count;
        } catch (NumberFormatException e) {
            throw new IOException("Invalid server response.", e);
        }
    }

    private void downloadFile(String fileName) throws IOException {
        sendEncryptedMessage("GET " + fileName);
        receiveFile(fileName);
    }

    private void receiveFile(String fileName) throws IOException {
        String response = readDecryptedMessage();
        if (!response.startsWith("OK ")) {
            System.out.println("Server: " + response);
            return;
        }

        long expectedSize = parseFileSize(response);
        String encodedData = readDecryptedMessage();
        byte[] fileData;

        try {
            fileData = Base64.getDecoder().decode(encodedData);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid file data received.", e);
        }

        if (fileData.length != expectedSize) {
            throw new IOException("Downloaded file size does not match.");
        }
        saveFile(fileName, fileData);
    }

    private long parseFileSize(String response) throws IOException {
        try {
            long size = Long.parseLong(response.substring(3));
            if (size < 0) throw new NumberFormatException();
            return size;
        } catch (NumberFormatException e) {
            throw new IOException("Invalid file size received.", e);
        }
    }

    private void saveFile(String fileName, byte[] fileData) throws IOException {
        Path directory = downloadDirectory.toAbsolutePath().normalize();
        Files.createDirectories(directory);
        Path outputFile = directory.resolve(fileName).normalize();

        if (!outputFile.getParent().equals(directory)) {
            throw new IOException("Invalid download path.");
        }

        Files.write(outputFile, fileData);
        System.out.println("File downloaded: " + outputFile);
    }

    private boolean isValidFileName(String fileName) {
        if (fileName == null || fileName.isBlank()) return false;

        try {
            Path path = Path.of(fileName);
            return !path.isAbsolute() && path.getNameCount() == 1 && !fileName.equals(".") && !fileName.equals("..");
        } catch (InvalidPathException e) {
            return false;
        }
    }

    private void sendEncryptedMessage(String message) throws IOException {
        String encryptedMessage = VigenereCipher.encrypt(message);
        writer.println(encryptedMessage);

        if (writer.checkError()) {
            throw new IOException("Could not send message to server.");
        }
    }

    private String readDecryptedMessage() throws IOException {
        String encryptedMessage = reader.readLine();
        if (encryptedMessage == null) {
            throw new EOFException("Server closed the connection.");
        }
        return VigenereCipher.decrypt(encryptedMessage);
    }
}