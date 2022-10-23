package main.java.ru.javarush.cryptoanalyzer;

import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import static main.java.ru.javarush.cryptoanalyzer.CryptoAnalyzerAlphabet.ALPHABET_LENGTH;

public class CryptoAnalyzerRunner {
    private static final String MODE_INPUT_WELCOME = "\nKindly choose the mode (1 - Crypt; 2 - Decrypt; 3 - Brute-force decrypt; 0 - Exit): ";
    private static final String KEY_INPUT_WELCOME = "\nKindly enter a crypto key: ";
    private static final String FILE_INPUT_WELCOME = "Kindly enter a file name in this folder: ";
    private static final String BF_RESULTS_MESSAGE = "\nBrute-force cycle is finished. The following crypto key is chosen: ";

    private static final String CRYPT_MODE = "INFO: Crypt mode is chosen";
    private static final String DECRYPT_MODE = "INFO: Decrypt mode is chosen";
    private static final String BF_DECRYPT_MODE = "INFO: Brute-force decrypt mode is chosen";
    private static final String INVALID_INPUT = "ERROR: Invalid input";
    private static final String FILE_NOT_EXIST = "ERROR: File is not exist! \nKindly re-enter a file name: ";

    static final Path INPUT_FILES_PATH = Paths.get("src", "main", "files", "input").toAbsolutePath();
    static final Path OUTPUT_FILES_PATH = Paths.get("src", "main", "files", "output").toAbsolutePath();

    void run() {
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.print(MODE_INPUT_WELCOME);
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt()) {
                int i = input.nextInt();
                if (i == 1) {
                    System.out.println(CRYPT_MODE);
                    new CryptoAnalyzerMaker(getInputFile(), getCryptoKey().getKeyLength()).encryptDecryptFile("encrypt");
                } else if (i == 2) {
                    System.out.println(DECRYPT_MODE);
                    new CryptoAnalyzerMaker(getInputFile(), getCryptoKey().getKeyLength()).encryptDecryptFile("decrypt");
                } else if (i == 3) {
                    System.out.println(BF_DECRYPT_MODE);
                    CryptoAnalyzerFile inputFile = getInputFile();
                    Map<Integer, Integer> bruteForceResults = getBruteForceStatistics(CryptoAnalyzerDict.getMostCommonWords(), inputFile);
                    new CryptoAnalyzerMaker(inputFile, getTopKeySortedByValue(bruteForceResults)).encryptDecryptFile("decrypt");
                } else if (i == 0) {
                    keepGoing = false;
                } else {
                    System.err.println(INVALID_INPUT);
                }
            } else {
                System.err.println(INVALID_INPUT);
            }
        }
    }

    private Map<Integer, Integer> getBruteForceStatistics(List<String> mostCommonWords, CryptoAnalyzerFile inputFile) {
        Map<Integer, Integer> bruteForceResults = new HashMap<>();
        for (int i = 0; i < ALPHABET_LENGTH; i++) {
            bruteForceResults.put(i, 0);
            new CryptoAnalyzerMaker(inputFile, i).bruteForceFile(bruteForceResults, mostCommonWords);
        }
        return bruteForceResults;
    }

    private Integer getTopKeySortedByValue(Map<Integer, Integer> bruteForceResults) {
        Map<Integer, Integer> sortedMapByValueReverseOrder =
                bruteForceResults.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        Integer firstKey = sortedMapByValueReverseOrder.entrySet().iterator().next().getKey();
        System.out.println(BF_RESULTS_MESSAGE + firstKey);
        return firstKey;
    }

    private CryptoAnalyzerFile getInputFile() {
        System.out.print("\n" + INPUT_FILES_PATH + "\n" + FILE_INPUT_WELCOME);
        Scanner filename = new Scanner(System.in);
        while (true) {
            String input = filename.nextLine();
            if (!input.isEmpty() && Files.exists(INPUT_FILES_PATH.resolve(input))) {
                return new CryptoAnalyzerFile(input, INPUT_FILES_PATH);
            } else {
                System.err.println(FILE_NOT_EXIST);
            }
        }
    }

    private CryptoAnalyzerKey getCryptoKey() {
        System.out.print(KEY_INPUT_WELCOME);
        return new CryptoAnalyzerKey(new Scanner(System.in).nextLine());
    }
}
