import utils.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static int THREADS_NUM = 10;

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        ExecutorService es = Executors.newFixedThreadPool(THREADS_NUM);
        Indexer indexer = new Indexer(dictionary, FileService.getFilesFromDataDirectory());
        es.execute(indexer);
        es.shutdown();
        try {
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Enter the term you wanna find in index: ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String token = null;
        try {
            token = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(dictionary.search(token));
    }
}