package main.java.ru.javarush.cryptoanalyzer;

import java.util.List;
import java.util.stream.Collectors;

class CryptoAnalyzerAlphabet {

    private CryptoAnalyzerAlphabet() {
    }

    static final String ALPHABET_SYMBOLS = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLRSTUVWXYZ.,:-!? ";
    static final int ALPHABET_LENGTH = ALPHABET_SYMBOLS.length();

    static List<Character> getAlphabetSymbolsList() {
        return ALPHABET_SYMBOLS.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    static int getPositionByChar(char letter) {
        return getAlphabetSymbolsList().indexOf(letter);
    }

    static char getCharByPosition(int position) {
        int normalizedPosition = position % ALPHABET_LENGTH;
        if (normalizedPosition < 0) {
            normalizedPosition += ALPHABET_LENGTH;
        }
        return getAlphabetSymbolsList().get(normalizedPosition);
    }
}