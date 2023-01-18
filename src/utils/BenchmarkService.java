package utils;

public class BenchmarkService {
    private static long START_POINT = 0;
    private static long END_PONT = 0;

    public static void startTimer() {
        START_POINT = System.currentTimeMillis();
    }

    public static void stopTimer() {
        END_PONT = System.currentTimeMillis();
    }

    public static long getProcessingFileTime() {
        long processingFileTime =  END_PONT - START_POINT;
        START_POINT = 0;
        END_PONT = 0;
        return processingFileTime;
    }
}
