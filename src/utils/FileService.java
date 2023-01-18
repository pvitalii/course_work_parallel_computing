package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {
    private static final String DATA_DIRECTORY_PATH = "src/CW_dataset/train-unsup";

    public static File[] getFilesFromDataDirectory() {
        File directoryPath = new File(DATA_DIRECTORY_PATH);
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        return filesList;
    }

    public static List<String> readFile(String filePath) {
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
}
