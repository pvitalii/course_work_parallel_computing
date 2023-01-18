import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Indexer implements Runnable {

    private final Dictionary dictionary;
    private final int threadIndex;
    Indexer(Dictionary d, int threadIndex){
        this.dictionary = d;
        this.threadIndex = threadIndex;
    }

    public void run() {
        File directoryPath = new File("src/CW_dataset/train-unsup");
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        int filesByThread = filesList.length/Main.THREADS_NUM;
        int firstIndex = filesByThread * threadIndex;
        int lastIndex = (filesByThread * threadIndex) + filesByThread;

        for(int i = firstIndex; i < lastIndex; i++) {
            List<String> fileData = readFile(filesList[i].getPath());
            parseFileDataIntoDictionary(fileData, filesList[i].getName());
        }
    }

    private List<String> readFile(String filePath) {
        List<String> fileLines = new ArrayList<>();
        try {
            File myObj = new File(filePath);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileLines.add(myReader.nextLine());
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileLines;
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
