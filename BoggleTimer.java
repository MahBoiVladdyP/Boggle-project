import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.concurrent.Task;
import javafx.scene.control.Label;

public class BoggleTimer  extends Task<Void>{

	int time = 60;
	Date timerStart;
	MainGUITest myMain;

	BoggleTimer(MainGUITest mgt)
	{
		super();
		timerStart = new Date();
		myMain = mgt;
	}

	protected Void call() throws Exception {
		while (true) {
			updateMessage("Time Remaining: "+(time-((new Date().getTime()/1000)-(timerStart.getTime()/1000))));
			if((time-((new Date().getTime()/1000)-(timerStart.getTime()/1000)))<=0){
				myMain.freeze();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				break;
			}
		} 
		return null;
	}
}



