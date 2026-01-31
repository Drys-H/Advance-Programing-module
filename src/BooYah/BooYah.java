package BooYah;

public class BooYah {
    public static void booloop() {
        for (int i = 0; i < 299; i++) {
            System.out.println("Boo!");
            if (i == 100) {
                try {
                    Thread.sleep(1000*60*10);
                } catch(InterruptedException x) {
                    System.out.println("BOO! I WAS INTERRUPTED!");
                }
            } else {
                try { Thread.sleep((long) (Math.random() * 10)); } catch(InterruptedException x) {}
            }
        }
    }
    public static void yahloop(Thread t) {
        for (int i = 0; i < 299; i++) {
            System.out.println("Yah!");
            try {
                Thread.sleep((long)(Math.random() * 100));
            } catch(InterruptedException x) {}
            if (i == 200) { t.interrupt(); }
        }
    }
    public static void dojoin(Thread t) {
        System.out.println("Waiting to join " + t);
        try { t.join(); } catch(InterruptedException x) {}
        System.out.println("Oh, at last!");
    }
    public static void main(String args[]){
        Thread t0 = new Thread(()->booloop());
        t0.start();
        new Thread(()->dojoin(t0)).start();
        yahloop(t0);
        System.out.println("Main thread exiting");
    }

}