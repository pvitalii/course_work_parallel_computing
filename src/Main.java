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

    public static void main(String[] args) {
        System.out.println("Enter the number of threads: ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        Integer THREADS_NUM = null;
        try {
            THREADS_NUM = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        String token = null;
        try {
            token = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Entered term have found in these files: " + dictionary.search(token));
    }
}