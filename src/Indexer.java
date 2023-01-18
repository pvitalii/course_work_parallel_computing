import utils.FileService;

import java.util.*;
import java.io.File;

public class Indexer implements Runnable {

    private final Dictionary dictionary;
    private final Queue<File> fileQueue;

    Indexer(Dictionary d, Queue<File> fQ){
        this.dictionary = d;
        this.fileQueue = fQ;
    }

    public void run() {
        File file = fileQueue.poll();
        while (file != null) {
            List<String> fileData = FileService.readFile(file.getPath());
            parseFileDataIntoDictionary(fileData, file.getName());
            file = fileQueue.poll();
        }
    }

    private void parseFileDataIntoDictionary(List<String> fileData, String docId) {
        List<String> words = new ArrayList<>();
        for (String line : fileData) {
            String cleanedToken = cleanToken(line);
            StringTokenizer st = new StringTokenizer(cleanedToken," ");
            while (st.hasMoreTokens()) {
                words.addAll(Collections.singleton(st.nextToken()));
            }
        }
        for (String word : words) {
            dictionary.add(word, docId);
        }
    }

    private String cleanToken(String tokens){
        tokens = tokens.replaceAll("[,.()/\"]", " ");
        return tokens;
    }
}
