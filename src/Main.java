import utils.BenchmarkService;
import utils.FileService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static int THREADS_NUM = 5;

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        Queue<File> fileQueue = FileService.getFilesFromDataDirectory();
        ExecutorService es = Executors.newFixedThreadPool(THREADS_NUM);
        BenchmarkService.startTimer();
        for(int i = 0; i < THREADS_NUM; i++) {
            Indexer indexer = new Indexer(dictionary, fileQueue);
            es.execute(indexer);
        }
        es.shutdown();
        try {
            es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BenchmarkService.stopTimer();
        System.out.println("Wasted time on indexing: " + BenchmarkService.getProcessingFileTime() + " milliseconds");
        System.out.println("Enter the term you wanna find in index: ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String token = null;
        try {
            token = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Entered term have found in these files: " + dictionary.search(token));
    }
}