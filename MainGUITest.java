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
	private TextField addWord=new TextField();
	int spots [] = new int [8];
	int lastPlayed[] = new int[1];
	int positionValue=0;
	String word ="";
	static ArrayList <Button> bList = new ArrayList<>();
	ArrayList <String> recordWords = new ArrayList <String>();
	public void start(Stage myStage){

		GridPane gridpane = new GridPane();
		Scene scene= new Scene(gridpane, 1000, 1000);
		gridpane.setPadding(new Insets(30));
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		
		myStage.setTitle("Boggle");
		myStage.setScene(scene);
		myStage.show();
		Label credits = new Label("© copyright no rights reserved");
		Label credits2= new Label("Anthony, Daniel, Tyron, Winston ");
		gridpane.add(credits, 6, 4);
		gridpane.add(credits2, 6, 5);
		
		BoggleBoard boggleBoard = new BoggleBoard();
		Random rand = new Random();
		char temp;
		bList.clear();
		 //Enter button
	       Button enterButton = new Button(String.valueOf("Enter"));
	       gridpane.add(enterButton, 4, 4);
	       //Show words button, used for sake of testing ArrayList
	       Button showFoundWords = new Button(String.valueOf("Show Found Words"));
	       gridpane.add(showFoundWords, 5, 5);
	       Button clearWords = new Button(String.valueOf("Clear"));
	       gridpane.add(clearWords,  4, 5);
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < 4; j++)
			{
				//temp=String.valueOf((char)((rand.nextInt(26)+1) + 64));
				temp=boggleBoard.getLetter(i, j);
				Button button = new Button(String.valueOf(temp));
				positionValue=(i*10)+j;
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
		//String [][] board = new String [5][5];
	}
	private void restrictSpots(){
		//i-1, j-1
		spots[0]=lastPlayed[0]+1;
	}
	public static Button getButton (int x){

		return bList.get(x);
	}
	class EnterButtonHandler implements EventHandler<ActionEvent>{
	  		  public void handle (ActionEvent e){
	  			  
	  			  if (word.length()>3){
	  				  recordWords.add(word);
	  			  	  word = "";
	  			  	  System.out.println("word recorded, find more!");
	  			  }
	  			  else {
	  				  System.out.println("LOL YOUR WORD IS TOO SHORT");
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
    		System.out.println("word cleared");
    	}
    }
	class ButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){

			Button b = (Button) e.getSource();
			word+=b.getText();
			//lastPlayed[0]=

			System.out.println("word is: " + word);
		}
	}


	public static void main(String []args){
		launch(args);
	}
}