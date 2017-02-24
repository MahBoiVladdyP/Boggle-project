import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.application.*;
import javafx.concurrent.Task;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.event.*;
import javafx.geometry.*;
public class MainGUITest extends Application{
	int spots [] = new int [8];
	int lastPlayed=0;
	String word ="";
	int row;
	int column;
	Label dynamicTimeDisplayLabel2 = new Label("");
	Label displayError = new Label("");
	Label displayWord = new Label("");
	TextArea allWords = new TextArea(""); 
	Label displayScore = new Label("");
	ArrayList<Integer> playedSpots = new ArrayList<Integer>();
	GridPane gridpane = new GridPane();
	static ArrayList <Button> bList = new ArrayList<>();
	BoggleBoard boggleBoard = new BoggleBoard();
	int score  = 0;
	Dictionary d = new Dictionary();
	TextArea possibleWords = new TextArea();



	public void start(Stage myStage){

		Date timerStart = new Date();

		boggleBoard.makeBoard();
		
		Scene scene= new Scene(gridpane, 700, 400);
		gridpane.setPadding(new Insets(30));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		myStage.setTitle("Corica");
		myStage.setScene(scene);
		myStage.show();
		myStage.setMaximized(true);
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		

		Label credits = new Label("© copyright all rights reserved");
		Label credits2 = new Label("Anthony, Daniel, Tyron, Winston ");
		Label yourWord = new Label("Your word: ");		
		Label allWordsTitle = new Label("You've found: ");
		Label Title = new Label("Boggle V 1.1");
		
		Button clearButton = new Button(String.valueOf("Clear"));
		Button resetButton = new Button(String.valueOf("Reset"));
		Button enterButton = new Button(String.valueOf("Enter"));
		
		//Set Titles And Fonts==============================================================================
		Title.setTextFill(Color.DARKBLUE);
		Title.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 40));
		Title.setEffect(ds);
		yourWord.setTextFill(Color.DARKBLUE);
		yourWord.setFont(Font.font("Times New Roman", FontPosture.REGULAR, 50));
		displayScore.setFont(Font.font("Verdana", FontWeight.NORMAL, 30));
		displayScore.setTextFill(Color.DARKBLUE);
		displayWord.setTextFill(Color.DARKBLUE);
		displayWord.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 40));
		credits2.setTextFill(Color.DARKBLUE);
		credits.setTextFill(Color.DARKBLUE);
		allWordsTitle.setTextFill(Color.DARKBLUE);
		allWordsTitle.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));		
		enterButton.setTextFill(Color.DARKBLUE);
		enterButton.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		clearButton.setTextFill(Color.DARKBLUE);
		clearButton.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		//==================================================================================================
		
		//Display Buttons===================================================================================
		gridpane.add(credits, 7, 6);
		gridpane.add(credits2, 7, 7);
		gridpane.add(yourWord, 7, 1);
		gridpane.add(displayError, 7, 3);
		gridpane.add(displayWord, 7, 2);		
		gridpane.add(allWordsTitle,  5,  7);
		gridpane.add(allWords, 5, 8);
		gridpane.add(displayScore, 5, 6);
		gridpane.add(Title,  5,  0);
		gridpane.add(dynamicTimeDisplayLabel2, 5, 1);
		gridpane.add(enterButton, 4, 4);
		gridpane.add(clearButton,  4, 5);
		gridpane.add(resetButton,  4,  6);
		gridpane.add(possibleWords, 7, 8);
		//=================================================================================================
		
		allWords.setMaxWidth(80);
		
		//Timer============================================================================================
		Task dynamicTimeTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				

				while (true) {
					long currentTime = System.currentTimeMillis();
					updateMessage("Time Remaining: "+(60-((new Date().getTime()/1000)-(timerStart.getTime()/1000))));
					if((60-((new Date().getTime()/1000)-(timerStart.getTime()/1000)))<=0){
						ArrayList<String> words = boggleBoard.getWords();
						String possibleWord = "";
						for(int i = 0; i < words.size();i++){
							possibleWord += words.get(i);
							possibleWord += "\n";
						}
						possibleWords.setText(possibleWord);
						for(int i = 0; i<=16; i++){
							getButton(i).setDisable(true);
						}
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						break;
					}
				}
				return null;
			}
		};
		dynamicTimeDisplayLabel2.textProperty().bind(dynamicTimeTask.messageProperty());
		Thread t2 = new Thread(dynamicTimeTask);
		t2.setName("Task Time Updater");
		t2.setDaemon(true);
		t2.start();
		//===============================================================================================
		
	
		
		for (row = 0; row < 4; row++){
			for (column = 0; column < 4; column++)
			{
				char letter=boggleBoard.getLetter(row, column);//get board from Winston's BoggleBoard
				Button button = new Button(String.valueOf(""));
				if (letter=='$'){
					button.setText(String.valueOf("Qu"));
					button.setTextFill(Color.DARKBLUE);
					button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
				}
				else{
					button.setText(String.valueOf(letter));
					button.setTextFill(Color.DARKBLUE);
					button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
				}

				bList.add(button);
				gridpane.add(button, row, column+1);
				button.setMaxWidth(60);
				button.setMinWidth(60);
				button.setMinHeight(60);
				button.setMaxHeight(60);
				button.setOnAction(new ButtonHandler());
			}
		}
		enterButton.setOnAction(new EnterButtonHandler());
		clearButton.setOnAction(new ClearButtonHandler());
		resetButton.setOnAction(new ResetButtonHandler());
	}
	//============================================================ end of start
	
	
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

			if(d.isWord(word)==false){

				System.out.println("Not A Real Word");
				displayError.setTextFill(Color.RED);
				displayError.setText("Not A Real Word");
				word = "";
				playedSpots.clear();

				return;
			}		

			if (word.length()>2 && boggleBoard.checkWord(word)==false){
				if (d.isWord(word)){
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
					displayError.setTextFill(Color.BLUE);
					displayError.setText("Nice job!");
					System.out.println("score is:" + score);
					displayScore.setText("Score: " + Integer.toString(score));
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
				displayError.setTextFill(Color.RED);
				displayError.setText("Word too short or already exists!");
				word = "";
				playedSpots.clear();
				return;
			}

		}
	}

	class ClearButtonHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e){
			word = "";
			System.out.println("word cleared");
			playedSpots.clear();
			displayError.setText("");
			displayWord.setText("");
		}
	}
	class ResetButtonHandler implements EventHandler<ActionEvent>{
		public void handle (ActionEvent e){
			score = 0;
			displayScore.setText("Score: " + Integer.toString(score));
			playedSpots.clear();
			word = "";
			bList.clear();
			//=============================================
			boggleBoard.returnWords().clear();
			String displayWordsString="";
			ArrayList<String> returnedWords = new ArrayList<String>();
			returnedWords= boggleBoard.returnWords();
			for (int k = 0; k<returnedWords.size(); k++){
				displayWordsString+=returnedWords.get(k);
				displayWordsString+=("\n");
			}
			allWords.setText(displayWordsString);
			//============================================
			
			boggleBoard.makeBoard();
			
			for (row = 0; row < 4; row++){
				for (column = 0; column < 4; column++)
				{
					char letter=boggleBoard.getLetter(row, column);//get board from Winston's BoggleBoard
					Button button = new Button(String.valueOf(""));
					if (letter=='$'){
						button.setText(String.valueOf("Qu"));
						button.setTextFill(Color.BLUEVIOLET);
						button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
					}
					else{
						button.setText(String.valueOf(letter));
						button.setTextFill(Color.BLUEVIOLET);
						button.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
					}

					bList.add(button);
					gridpane.add(button, row, column+1);
					button.setMaxWidth(60);
					button.setMinWidth(60);
					button.setMinHeight(60);
					button.setMaxHeight(60);
					button.setOnAction(new ButtonHandler());
				}
			}
			
			//Timer============================================================================================
			Date timerStart = new Date();
			Task dynamicTimeTask = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					

					while (true) {
						long currentTime = System.currentTimeMillis();
						updateMessage("Time Remaining: "+(60-((new Date().getTime()/1000)-(timerStart.getTime()/1000))));
						if((60-((new Date().getTime()/1000)-(timerStart.getTime()/1000)))<=0){
							ArrayList<String> words = boggleBoard.getWords();
							String possibleWord = "";
							for(int i = 0; i < words.size();i++){
								possibleWord += words.get(i);
								possibleWord += "\n";
							}
							possibleWords.setText(possibleWord);
							for(int i = 0; i<=16; i++){
								getButton(i).setDisable(true);
							}
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException ex) {
							break;
						}
					}
					return null;
				}
			};
			dynamicTimeDisplayLabel2.textProperty().bind(dynamicTimeTask.messageProperty());
			Thread t2 = new Thread(dynamicTimeTask);
			t2.setName("Task Time Updater");
			t2.setDaemon(true);
			t2.start();
			//===============================================================================================
possibleWords.clear();
		}
	}
	class ButtonHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent e){



			Button b = (Button) e.getSource();
			row=GridPane.getRowIndex(b);
			column=GridPane.getColumnIndex(b);

			int currentPosition=(row * 10) + column;
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
				displayError.setTextFill(Color.RED);
				displayError.setText("Illegal Move");
			}
		}
	}


	public static void main(String []args){
		launch(args);

	}
}