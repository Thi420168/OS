import java.util.concurrent.*;

public class JavaThreads {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== Single Thread Executor ===");
        ExecutorService single = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 3; i++) {
            int id = i;
            single.submit(() -> {
                System.out.println("SingleThread task " + id +
                        " running in " + Thread.currentThread().getName());
            });
        }
        single.shutdown();
        single.awaitTermination(5, TimeUnit.SECONDS); // WAIT UNTIL DONE

        System.out.println("\n=== Cached Thread Pool ===");
        ExecutorService cached = Executors.newCachedThreadPool();
        for (int i = 1; i <= 5; i++) {
            int id = i;
            cached.submit(() -> {
                System.out.println("CachedThreadPool task " + id +
                        " running in " + Thread.currentThread().getName());
            });
        }
        cached.shutdown();
        cached.awaitTermination(5, TimeUnit.SECONDS); // WAIT UNTIL DONE

        System.out.println("\n=== Fork-Join Parallel Stream ===");
        ForkJoinPool pool = new ForkJoinPool();
        pool.submit(() -> {
            java.util.stream.IntStream.range(1, 10)
                    .parallel()
                    .forEach(i -> {
                        System.out.println("Parallel task " + i +
                                " executed by " + Thread.currentThread().getName());
                    });
        }).join();
        pool.shutdown();
    }
}

