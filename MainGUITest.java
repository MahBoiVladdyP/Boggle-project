import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.collections.*;
public class MainGUITest extends Application{
	int letterCounter=0;
	int spots [] = new int [8];
	int lastPlayed=0;
	int positionValue=0;
	String word ="";
	char temp;
	int i;
	int j;
	ArrayList<Integer> playedSpots = new ArrayList<Integer>();//all the played positions
 	GridPane gridpane = new GridPane();
	static ArrayList <Button> bList = new ArrayList<>();
	ArrayList <String> recordWords = new ArrayList <String>();
	BoggleBoard boggleBoard = new BoggleBoard();
	public void start(Stage myStage){

		
		Scene scene= new Scene(gridpane, 700, 400);
		gridpane.setPadding(new Insets(30));
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		
		myStage.setTitle("Hi Trung");
		myStage.setScene(scene);
		myStage.show();
		Label credits = new Label("© copyright no rights reserved");
		Label credits2= new Label("Anthony, Daniel, Tyron, Winston ");
		gridpane.add(credits, 6, 4);
		gridpane.add(credits2, 6, 5);
		
		Random rand = new Random();
		bList.clear();
		 //Enter button
	       Button enterButton = new Button(String.valueOf("Enter"));
	       gridpane.add(enterButton, 4, 4);
	       //Show words button, used for sake of testing ArrayList
	       Button showFoundWords = new Button(String.valueOf("Show Found Words"));
	       gridpane.add(showFoundWords, 5, 5);
	       Button clearWords = new Button(String.valueOf("Clear"));
	       gridpane.add(clearWords,  4, 5);
	      for (i = 0; i < 4; i++){
			for (j = 0; j < 4; j++)
			{
				//temp=String.valueOf((char)((rand.nextInt(26)+1) + 64));
	    	   	char [][] board=boggleBoard.getBoard();//get board from Winston's BoggleBoard
				Button button = new Button(String.valueOf(board[i][j]));
				bList.add(button);
				gridpane.add(button, i, j);
				button.setMaxWidth(40);
				button.setMinWidth(40);
				button.setOnAction(new ButtonHandler());
			}
		}
		enterButton.setOnAction(new EnterButtonHandler());
	    showFoundWords.setOnAction(new ShowFoundWordsHandler());
	    clearWords.setOnAction(new ClearWordsHandler());
	}
	private boolean isAdjacent(){//if true, no conflict. if false, button can't be pressed
			spots[0]=lastPlayed-11;
			spots[1]=lastPlayed-1;
			spots[2]=lastPlayed+9;
			spots[3]=lastPlayed-10;
			spots[4]=lastPlayed+10;
			spots[5]=lastPlayed-9;
			spots[6]=lastPlayed+1;
			spots[7]=lastPlayed+11;
			System.out.println("Adjacent spots to " + lastPlayed + " are: ");
			for (int i=0; i<8; i++){
				System.out.print(spots[i] + " ");
			}
		System.out.println("entered isAdjacent()");
		if (letterCounter==0){//exception for first letter of a word, always allowed
			System.out.println("letterCounter = 0, exception to isAdjacent()");
			return true;
		}
		for (int i=0; i<8; i++){//runs through adjacent spots, checks for matching with current position
			System.out.println(spots[i] + "=?="+ positionValue);
			if (spots[i]==positionValue){
				System.out.println("true, is adjacent");
				return true;
			}
		}
		System.out.println("false, not adjacent");
		return false;
	}
	public static Button getButton (int x){

		return bList.get(x);
	}
	class EnterButtonHandler implements EventHandler<ActionEvent>{
	  		  public void handle (ActionEvent e){
	  			  
	  			  if (word.length()>3){
	  				  recordWords.add(word);
	  			  	  word = "";
	  			  	  letterCounter=0;
	  			  	  System.out.println("word recorded, find more!");
	  			  }
	  			  else {
	  				  System.out.println("word too short xd");
	  			  	  word = "";
	  			  }
	  		  }
	  	  }
    class ShowFoundWordsHandler implements EventHandler<ActionEvent>{
			  public void handle (ActionEvent e){
				   System.out.println(recordWords);
	  		  }
	  	  }
    class ClearWordsHandler implements EventHandler<ActionEvent>{
    	public void handle (ActionEvent e){
    		word = "";
    		letterCounter=0;
    		System.out.println("word cleared");
    	}
    }
	class ButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){
			
			Button b = (Button) e.getSource();
			i=GridPane.getRowIndex(b);
			j=GridPane.getColumnIndex(b);
		/*	BogglePoint point = new BogglePoint(i,j);
			BogglePoint[] adjacentLetters = new BogglePoint[8];
			adjacentLetters = boggleBoard.getAdjacent(point);
			System.out.println("Adjacent: ");
			for (int k=0;  k<8; k++){
				System.out.println(adjacentLetters[k] + " ");
			}*/
			positionValue=(i * 10) + j;
			System.out.println("positionValue = " + positionValue);
			if (isAdjacent()==true && !playedSpots.contains(positionValue)){
				System.out.println("isAdjacent() = true");
				word+=b.getText();
				System.out.println("word is: " + word);
				playedSpots.add(positionValue);
				lastPlayed=positionValue;
				letterCounter+=1;
				System.out.println("letterCounter = " + letterCounter);
			}
			else
				System.out.println("error xd");
		}
	}
	public static void main(String []args){
		launch(args);
	}
}
