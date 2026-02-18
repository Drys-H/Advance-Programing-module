package GuiClass;

// worksheets 2026 version
public class Decomposer implements Runnable {
  
  private final NaiveDispatcher dispatcher;
  private final String jobName;
  private final int start;
  private final int length;
  
  private int current;
  private int segment = 2;
  
  public Decomposer(NaiveDispatcher dispatcher, String jobName, int start, int length) {
    this.dispatcher = dispatcher;
    this.jobName = jobName;
    this.start = start;
    this.length = length;
    this.current = start;
  }
  
  @Override
  public void run() {
    int todo = Math.min(this.segment, this.length - this.current);
    int currentStart = this.current;
    this.dispatcher.queueRunnable(()->NaiveDispatcher.printNumbers(this.jobName, currentStart, todo));
    this.current += todo;
    if (this.current < this.length) {
      this.dispatcher.queueRunnable(this);
    }
  }
  
}