public class ThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[10];
        int counter = 0;
        for (Thread th: threads) {
            counter++;
            th = new Thread(() -> {});
            Thread.currentThread().setName("Thread_" + counter);
            System.out.println(Thread.currentThread().getName());
            th.start();
            th.join();
        }
    }
}
