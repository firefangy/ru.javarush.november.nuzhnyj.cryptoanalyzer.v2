package main.java.ru.javarush.cryptoanalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CryptoAnalyzerDict {
    private static final Path DICT_FILES_URL = Paths.get("src", "main", "files", "dict", "top100words.txt").toAbsolutePath();

    private CryptoAnalyzerDict() {
    }

    static List<String> getMostCommonWords() {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(DICT_FILES_URL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
