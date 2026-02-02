package BooYah;

public class Deadlock implements Runnable {

    private Object o1 = "o1";
    private Object o2 = "o2";

    public void method1() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " entered method 1 - attempting to obtain lock o1");
        synchronized(o1) {
            System.out.println(name + " obtained lock o1 in method 1 - attempting to obtain lock o2");
            blip();
            synchronized(o2) {
                System.out.println(name + " holding both locks in method 1");
            }
            System.out.println(name + " released o2 still holding o1 in method 1");
        }
        System.out.println(name + " exiting method 1 - no locks held");
    }

    public void method2() {
        String name = Thread.currentThread().getName();
        System.out.println(name + " entered method 2 - attempting to obtain lock o2");
        synchronized(o2) {
            System.out.println(name + " obtained lock o2 in method 2 - attempting to obtain lock o1");
            blip();
            synchronized(o1) {
                System.out.println(name + " holding both locks in method 2");
            }
            System.out.println(name + " released o1 still holding o2 in method 2");
        }
        System.out.println(name + " exiting method 2 - no locks held");
    }

    public void run() {
        String name = Thread.currentThread().getName();
        for(int i = 0; i < 4; i++) {
            blip(); method1();
            blip(); method2();
            System.out.println(name + " completed loop " + i);
        }
        System.out.println(name + " successfully completed!");
    }

    private void blip() {
        int delay = 100;
        if (Thread.currentThread().getName().equals("Thread-1")) { delay = 5; }
        try { Thread.sleep((long) (Math.random() * delay)); } catch( InterruptedException x) {}
    }

    public static void main(String[] args) {
        Deadlock dl = new Deadlock();
        Thread t0 = new Thread(dl);
        Thread t1 = new Thread(dl);
        t0.start();
        t1.start();
    }

}
