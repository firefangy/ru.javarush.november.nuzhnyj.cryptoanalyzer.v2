package main.java.ru.javarush.cryptoanalyzer;

public class CryptoAnalyzerKey {
    private final String key;
    private final int keyLength;

    CryptoAnalyzerKey(String key) {
        this.key = key;
        this.keyLength = key.length();
        System.out.println("INFO: Crypto key is set (length: " + keyLength + ")");
    }

    int getKeyLength() {
        return keyLength;
    }
}
