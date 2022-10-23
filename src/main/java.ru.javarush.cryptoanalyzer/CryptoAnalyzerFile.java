package main.java.ru.javarush.cryptoanalyzer;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CryptoAnalyzerFile {
    private final String fileName;
    private final Path path;

    CryptoAnalyzerFile(String filename, Path path) {
        this.fileName = filename;
        this.path = Paths.get(String.valueOf(path), filename);
        System.out.println("INFO: file url is " + this.path);
    }

    String getFileName() {
        return fileName;
    }

    String getPath() {
        return path.toString();
    }
}
