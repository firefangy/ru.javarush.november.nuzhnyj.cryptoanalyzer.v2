package main.java.ru.javarush.cryptoanalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static main.java.ru.javarush.cryptoanalyzer.CryptoAnalyzerAlphabet.*;
import static main.java.ru.javarush.cryptoanalyzer.CryptoAnalyzerRunner.*;

class CryptoAnalyzerMaker {
    private static final String UNKNOWN_CRYPT_MODE = "ERROR: Unknown crypt mode";
    private final CryptoAnalyzerFile inputFile;
    private final int cryptoKeyLength;

    CryptoAnalyzerMaker(CryptoAnalyzerFile file, int cryptoKeyLength) {
        inputFile = file;
        this.cryptoKeyLength = cryptoKeyLength;
    }

    void encryptDecryptFile(String mode) {
        CryptoAnalyzerFile outputFile = new CryptoAnalyzerFile(inputFile.getFileName(), OUTPUT_FILES_PATH);
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile.getPath()));
             PrintWriter writer = new PrintWriter(outputFile.getPath(), "UTF-8")
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                char[] encryptedChars = new char[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    char originalChar = line.charAt(i);
                    int position = getPositionByChar(originalChar);
                    if (position == -1) {
                        encryptedChars[i] = originalChar;
                    } else {
                        if (mode.matches("encrypt")) {
                            encryptedChars[i] = getCharByPosition(position + cryptoKeyLength);
                        } else if (mode.matches("decrypt")) {
                            encryptedChars[i] = getCharByPosition(position - cryptoKeyLength);
                        } else {
                            System.err.println(UNKNOWN_CRYPT_MODE);
                            System.exit(1);
                        }
                    }
                }
                writer.println(new String(encryptedChars));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void bruteForceFile(Map<Integer, Integer> bruteForceResults, List<String> mostCommonWords) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile.getPath()))) {
            String line;
            while ((line = br.readLine()) != null) {
                char[] modifiedString = new char[line.length()];
                for (int i = 0; i < line.length(); i++) {
                    char originalChar = line.charAt(i);
                    int position = getPositionByChar(originalChar);
                    if (position == -1) {
                        modifiedString[i] = originalChar;
                    } else {
                        modifiedString[i] = getCharByPosition(position - cryptoKeyLength);
                    }
                }
                for (String dictWord : mostCommonWords) {
                    for (String word : new String(modifiedString).split("\\s+")) {
                        if (word.equals(dictWord)) {
                            bruteForceResults.put(cryptoKeyLength, bruteForceResults.get(cryptoKeyLength) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}