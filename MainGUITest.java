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
import javafx.scene.effect.GaussianBlur;
import javafx.event.*;
import javafx.geometry.*;
public class MainGUITest extends Application{
	int spots [] = new int [8];
	int lastPlayed=0;
	String word ="";
	String temp;
	int i;
	int j;
	Label displayError = new Label("");//display error in GUI
	Label displayWord = new Label("");//display word in GUI
	TextArea allWords = new TextArea(""); //display all words in GUI
	Label displayScore = new Label("");//display score in GUI
	ArrayList<Integer> playedSpots = new ArrayList<Integer>();//all the played positions
	GridPane gridpane = new GridPane();
	static ArrayList <Button> bList = new ArrayList<>();
	ArrayList <String> recordWords = new ArrayList <String>();
	BoggleBoard boggleBoard = new BoggleBoard();
	int score  = 0;
	Dictionary d = new Dictionary();


	public void start(Stage myStage){

		Date timerStart = new Date();

		Scene scene= new Scene(gridpane, 700, 400);
		gridpane.setPadding(new Insets(30));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		myStage.setTitle("Timothy Corica");
		myStage.setScene(scene);
		myStage.setMaximized(true);
		myStage.show();
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		
		TextArea possibleWords = new TextArea();
		Label credits = new Label("© copyright all rights reserved");
		Label credits2 = new Label("Anthony, Daniel, Tyron, Winston ");
		Label yourWord = new Label("Your word: ");		
		Label allWordsTitle = new Label("You've found: ");
		Label Title = new Label("Testa");
		Label dynamicTimeDisplayLabel2 = new Label("");
		Button clearButton = new Button(String.valueOf("Clear"));
		Button resetButton = new Button(String.valueOf("Reset"));
		Button enterButton = new Button(String.valueOf("Enter"));
		
		//1=====================================================================
		Title.setTextFill(Color.HOTPINK);
		Title.setFont(Font.font("Curlz MT", FontWeight.BOLD, 40));
		Title.setEffect(ds);
		yourWord.setTextFill(Color.BLUE);
		yourWord.setFont(Font.font("Times New Roman", FontPosture.ITALIC, 50));
		displayScore.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 30));
		displayScore.setTextFill(Color.ORANGE);
		displayWord.setTextFill(Color.BLUEVIOLET);
		displayWord.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
		credits2.setTextFill(Color.DARKBLUE);
		credits.setTextFill(Color.DARKBLUE);
		allWordsTitle.setTextFill(Color.KHAKI);
		allWordsTitle.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));		
		enterButton.setTextFill(Color.LAWNGREEN);
		enterButton.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 20));
	//	enterButton.setMinWidth(80.0);
		clearButton.setTextFill(Color.RED);
		clearButton.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 20));
		clearButton.setMinWidth(70.0);
		//1=====================================================================
		
		//2=====================================
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
		//2=======================================
		
		allWords.setMaxWidth(80);
		
		//3=====================================================
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
						for (int i = 0; i < words.size(); i ++){
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
		//3=====================================================
		
	
		//initializes buttons
		for (i = 0; i < 4; i++){
			for (j = 0; j < 4; j++)
			{
				char letter=boggleBoard.getLetter(i, j);//get board from Winston's BoggleBoard
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
				//temp=String.valueOf((char)((rand.nextInt(26)+1) + 64));
				//char letter=boggleBoard.getLetter(i, j);//get board from Winston's BoggleBoard
				//Button button = new Button(String.valueOf(temp));

				bList.add(button);
				gridpane.add(button, i, j+1);
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
				displayWord.setText("");
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
					displayWord.setText("");
					System.out.println("Word recorded! Nice job!");
					displayError.setTextFill(Color.GREENYELLOW);
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
				displayError.setTextFill(Color.RED);
				displayError.setText("Illegal Move");
			}
		}
	}


	public static void main(String []args){
		launch(args);

	}
}
