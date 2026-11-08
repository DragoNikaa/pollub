package org.example.network;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    private final Path sharedDirectory;

    public FileService(Path sharedDirectory) throws IOException {
        this.sharedDirectory = sharedDirectory.toRealPath();
    }

    public List<String> listFiles() throws IOException {
        try (Stream<Path> paths = Files.list(sharedDirectory)) {
            return paths
                    .filter(path -> Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
                    .map(path -> path.getFileName().toString())
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    public Optional<Path> findFile(String fileName) {
        if (fileName == null || fileName.isBlank()) return Optional.empty();

        try {
            Path sanitizedFileName = Path.of(fileName).getFileName();
            if (sanitizedFileName == null) return Optional.empty();

            Path file = sharedDirectory.resolve(sanitizedFileName).normalize();

            if (file.getParent().equals(sharedDirectory) && Files.isRegularFile(file, LinkOption.NOFOLLOW_LINKS)) {
                return Optional.of(file);
            }
            return Optional.empty();
        } catch (InvalidPathException e) {
            return Optional.empty();
        }
    }
}