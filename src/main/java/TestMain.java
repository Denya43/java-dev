import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class TestMain {
    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        int nproc = runtime.availableProcessors();
        Thread[] threads = new Thread[nproc];
        int nthread = 0;
        int numberRandom = 1000000;
        Random random = new Random();
        for (int j = 0; j < nproc; j++) {
            nthread++;
            threads[j] = new Thread(() -> {
                long start = System.currentTimeMillis();
                int[] data = new int[numberRandom];
                for (int i = 0; i < numberRandom; i++) {
                    data[i] = random.nextInt();
                }
                long finish = System.currentTimeMillis();
                try (DataOutputStream ostream = new DataOutputStream(new FileOutputStream("data_" + Thread.currentThread().getName() + ".txt"))) {
                    ostream.writeBytes("Thread: " + Thread.currentThread().getName() + " Time elapsed: " + 1.0 * (finish - start) / 1000);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            });
            threads[j].setName(String.valueOf(nthread));
            threads[j].start();
        }

        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}