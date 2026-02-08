package GuiClass;

// worksheets 2026 version
public class CountingJob implements Runnable {
  
  private final String jobName;
  private final int start;
  private final int length;
      
  public CountingJob(String jobName, int start, int length) {
    this.jobName = jobName;
    this.start = start;
    this.length = length;
  }
  
  @Override
  public void run() {

      new Thread(() -> NaiveDispatcher.printNumbers(jobName, start, length)).start();
  }
  
}