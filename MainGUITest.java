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
	int spots [] = new int [8];
	int lastPlayed=0;
	String word ="";
	String temp;
	int i;
	int j;
	Label displayError = new Label("");//display error in GUI
	Label displayWord = new Label("");//display word in GUI
	Label allWords = new Label(""); //display all words in GUI
	Label displayScore = new Label("");//display score in GUI
	ArrayList<Integer> playedSpots = new ArrayList<Integer>();//all the played positions
	GridPane gridpane = new GridPane();
	static ArrayList <Button> bList = new ArrayList<>();
	ArrayList <String> recordWords = new ArrayList <String>();
	BoggleBoard boggleBoard = new BoggleBoard();
	ReminderBeep rb = new ReminderBeep(10);
	int score  = 0;
	Dictionary d = new Dictionary();



	public void start(Stage myStage){

		

		Scene scene= new Scene(gridpane, 700, 400);
		gridpane.setPadding(new Insets(30));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		myStage.setTitle("Hi Trung");
		myStage.setScene(scene);
		myStage.show();
		Label credits = new Label("© copyright no rights reserved");
		Label credits2 = new Label("Anthony, Daniel, Tyron, Winston ");
		Label displayWordTitle = new Label("Your word: ");		
		Label allWordsTitle = new Label("You've found: ");

		//TextField textfield1 = new TextField("");
		gridpane.add(credits, 7, 6);
		gridpane.add(credits2, 7, 7);
		gridpane.add(displayWordTitle, 5, 0);
		gridpane.add(displayError, 5, 2);
		gridpane.add(displayWord, 5, 1);		
		gridpane.add(allWordsTitle,  5,  7);
		gridpane.add(allWords, 5, 8);
		gridpane.add(displayScore, 5, 3);

		//Random rand = new Random();
		bList.clear();
		//Enter button
		Button enterButton = new Button(String.valueOf("Enter"));
		gridpane.add(enterButton, 4, 4);
		//Show words button, used for sake of testing ArrayList
		/*Button showFoundWords = new Button(String.valueOf("Show Found Words"));
	       gridpane.add(showFoundWords, 5, 5);*/
		Button clearWords = new Button(String.valueOf("Clear"));
		gridpane.add(clearWords,  4, 5);

		for (i = 0; i < 4; i++){
			for (j = 0; j < 4; j++)
			{
				char letter=boggleBoard.getLetter(i, j);//get board from Winston's BoggleBoard
				Button button = new Button(String.valueOf(""));
				if (letter=='$'){
					button.setText(String.valueOf("Qu"));
				}
				else{
					button.setText(String.valueOf(letter));
				}
				//temp=String.valueOf((char)((rand.nextInt(26)+1) + 64));
				//char letter=boggleBoard.getLetter(i, j);//get board from Winston's BoggleBoard
				//Button button = new Button(String.valueOf(temp));

				bList.add(button);
				gridpane.add(button, i, j);
				button.setMaxWidth(40);
				button.setMinWidth(40);
				button.setOnAction(new ButtonHandler());
			}
		}
		enterButton.setOnAction(new EnterButtonHandler());
		//showFoundWords.setOnAction(new ShowFoundWordsHandler());
		clearWords.setOnAction(new ClearWordsHandler());
	}
	private boolean isAdjacent(int currentPosition){//if true, no conflict. if false, button can't be pressed
		
		spots[0]=lastPlayed-11;//top left corner
		spots[1]=lastPlayed-1;//straight above
		spots[2]=lastPlayed+9;//top right corner
		spots[3]=lastPlayed-10;//left
		spots[4]=lastPlayed+10;//right
		spots[5]=lastPlayed-9;//bottom left corner
		spots[6]=lastPlayed+1;//straight below
		spots[7]=lastPlayed+11;//bottom right corner
		if (word.length()==0){//exception for first letter of a word, always allowed
			System.out.println("First letter of the word!");
			return true;
		}
		for (int i=0; i<8; i++){//runs through adjacent spots, checks for matching with current position
			System.out.println(spots[i] + "=?="+ currentPosition);
			if (spots[i]==currentPosition){
				return true;
			}
		}
		return false;
	}
	public static Button getButton (int x){

		return bList.get(x);
	}
	public String getWord(){
		return word;
	}
	class EnterButtonHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e){

			if(rb.isOver()==true){
				System.out.println("Game Over Man");
				try {
					Thread.sleep(100);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				Platform.exit();
			}
			
			if(d.isWord(word)==false){

				System.out.println("Not A Real Word");
				displayError.setText("Not A Real Word");
				word = "";
				playedSpots.clear();
				
				return;
			}		

			if (word.length()>2 && boggleBoard.checkWord(word)==false){
				if (d.isWord(word)){
					recordWords.add(word);
					if(word.length()==3 || word.length()==4)
						score = score + 1;
					if(word.length()==5)
						score = score + 2;
					if(word.length()==6)
						score = score + 3;
					if(word.length()==7)
						score = score + 5;
					if(word.length()>=8)
						score = score + 11;
					boggleBoard.addWord(word);
					word = "";
					playedSpots.clear();
					System.out.println("Word recorded! Nice job!");
					displayError.setText("Nice job!");
					System.out.println("score is:" + score);
					displayScore.setText("Your Score is: " + Integer.toString(score));
					String displayWordsString="";
	  			  	ArrayList<String> returnedWords = new ArrayList<String>();
	  			  	returnedWords= boggleBoard.returnWords();
	  			  	for (int k = 0; k<returnedWords.size(); k++){
	  			  		displayWordsString+=returnedWords.get(k);
	  			  		displayWordsString+=("\n");
	  			  	}
	  			  	allWords.setText(displayWordsString);
					word = "";
					playedSpots.clear();
					System.out.println("word recorded, find more!");

				}
				return;
			}
	
			
			if (word.length()<2 || boggleBoard.checkWord(word)==true){
				System.out.println("word too short or word existing");
				displayError.setText("Word too short or already exists!");
				word = "";
				playedSpots.clear();
				return;
			}
			
		}
	}

	class ClearWordsHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e){
			word = "";
			System.out.println("word cleared");
			playedSpots.clear();
			displayError.setText("");
			displayWord.setText("");
		}
	}
	class ButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){

			
			
			Button b = (Button) e.getSource();
			i=GridPane.getRowIndex(b);
			j=GridPane.getColumnIndex(b);

			int currentPosition=(i * 10) + j;
			System.out.println("currentPosition = " + currentPosition);
			if (isAdjacent(currentPosition)==true && !playedSpots.contains(currentPosition)){

				word+=b.getText();
				System.out.println("word is: " + word);
				playedSpots.add(currentPosition);
				lastPlayed=currentPosition;
				displayError.setText("");
				displayWord.setText(word);
				System.out.println("wordlength = " + word.length());
			}
			else{
				System.out.println("Illegal Move");
				displayError.setText("Illegal Move");
			}
		}
	}


	public static void main(String []args){
		launch(args);
		
	}
}
