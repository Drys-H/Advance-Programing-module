package BooYah;
import java.util.Scanner;
public class CounterDemo {

    private static class DoCount implements Runnable {
        private final Counter counter;
        private final int number;
        public DoCount( final Counter counter, final int number) {
            this.counter = counter;
            this.number = number;
        }
        public void run() {       for( int i = 0; i < this.number; i++) {
            try {
                Thread.sleep( (long)(Math.random() * 10));
            } catch( InterruptedException e) {}
            this.counter.increment();
        }
        }
    }

    public static void main( final String[] args) throws InterruptedException {
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter number of threads to use: ");
        int n = keyboard.nextInt(); keyboard.nextLine(); keyboard.close();
        System.out.println("Expected count = " + (n * 100));
        final Counter counter = new Counter();
        final Thread[] threads = new Thread[n];
        // create the threads, with each one set to count up to 100
        for( int i = 0; i < threads.length; i++) {
            threads[i] = new Thread( new DoCount(counter, 100));
        }
        // set all the threads running concurrently
        for( int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        // wait for all the threads to finish
        for( int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        // print the total counted (should be 100 times the number of threads)
        System.out.println( "  Actual count = " + counter.value());
    }
}

