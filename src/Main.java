import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static int THREADS_NUM = 10;

    public static void main(String[] args) throws InterruptedException, IOException {
        Dictionary dictionary = new Dictionary();
        ExecutorService es = Executors.newFixedThreadPool(THREADS_NUM);
        long startTime = System.nanoTime();
        for (int i = 0; i < THREADS_NUM; i++) {
            Indexer indexer = new Indexer(dictionary, i);
            es.execute(indexer);
        }
        es.shutdown();
        es.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        long stopTime = System.nanoTime ();
        System.out.println (" Time :" + ( stopTime - startTime ));
        System.out.println("Enter the term you wanna find in index: ");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String token = reader.readLine();
        System.out.println(dictionary.search(token));
    }
}