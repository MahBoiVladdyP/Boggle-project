
import java.util.Timer;
import java.util.TimerTask;


public class ReminderBeep {
	


	  Timer timer;

	  public ReminderBeep(int seconds) {

	    timer = new Timer();
	    timer.schedule(new RemindTask(), seconds * 1000);
	  }

	  boolean gameOver = false;
	  boolean isOver()
	  {
		  return gameOver;
	  }

	  public class RemindTask extends TimerTask {

			public void run() {
				System.out.println("Time's up!");
				gameOver = true;
				timer.cancel();
			}
		}





}
