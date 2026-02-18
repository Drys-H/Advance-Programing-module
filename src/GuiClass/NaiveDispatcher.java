package GuiClass;

import java.util.LinkedList;
import java.util.Queue;

// worksheets 2026 version
public class NaiveDispatcher implements Runnable {
  private final Queue<Runnable> queue = new LinkedList<Runnable>();
  
  public void queueRunnable( Runnable toQueue) {
    this.queue.add( toQueue);
  } 

  @Override
  public void run() {
    long started = System.currentTimeMillis();
    while(System.currentTimeMillis() - started < 60000) {
      Runnable next = this.queue.poll();
      if ( next != null) {
        next.run();
      }
    }
  }
  
  public static void printNumbers(String jobName, int start, int length) {
    for (int i = start; i < start + length; i++) {
      System.out.println(jobName + " " + i + " " + Thread.currentThread().getName());
      try { Thread.sleep(200); } catch (InterruptedException e) {}
    }
  }
  
  public static void main(String[] args) {
   
    NaiveDispatcher dispatcher = new NaiveDispatcher();

      dispatcher.queueRunnable(new Decomposer(dispatcher, "Bob", 0, 15));
      dispatcher.queueRunnable(new Decomposer(dispatcher, "Cat", 0, 15));

    //dispatcher.queueRunnable(new CountingJob("Bob", 0, 15));
    //dispatcher.queueRunnable(new CountingJob("Cat", 0, 15));
    
    dispatcher.run();
  }
}
