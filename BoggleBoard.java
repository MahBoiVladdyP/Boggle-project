import java.util.ArrayList;

public class BoggleBoard {

	public BoggleBoard() {
		makeBoard();
	}

	private char[][] board = new char[4][4];
	ArrayList<String> words = new ArrayList<>();
	int numAllWords;
	
	public void makeBoard() {
		BoggleCubeSet cubeSet = new BoggleCubeSet("BoggleCubes.txt");
		cubeSet.reset();
		for (int i = 0; i < 4; i ++){
			for (int j = 0; j < 4; j ++){
				board[i][j] = cubeSet.getCubeLetter();
			}
		}
		words.clear();
	}

	public void printBoard() {
		ArrayList<Character> buffer = new ArrayList<>();
		for (int i = 0; i < board.length; i ++){
			for (int j = 0; j < board[i].length; j ++){
				buffer.add(board[i][j]);	
			}
		}
		if (buffer.contains('$')){
			for (int i = 0; i < board.length; i ++){
				for (int j = 0; j < board.length; j ++){
					if (board[i][j] == '$'){
						System.out.print("Qu");
						System.out.print(" ");
					} else {
						System.out.print(board[i][j]);
						System.out.print("  ");
					}
				}
				System.out.println("");
			}
		} else {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++){
					System.out.print(board[i][j]);
					System.out.print("  ");
				}
				System.out.println("");
			}
		}
	}

	public boolean onBoard(String word){ 
		word = word.toUpperCase();
		String temp = "";
		for (int i = 0; i < word.length(); i ++){
			if (i < word.length() - 1){
				if (word.charAt(i) == 'Q' && word.charAt(i + 1) == 'U'){
					temp += '$';
					i ++;
				} else {
					temp += word.charAt(i);
				}
			} else {
				temp += word.charAt(i);
			}
		}
		word = temp;
		for (int i = 0; i < board.length; i ++){
			for (int j = 0; j < board[i].length; j ++){
				boolean[][] visited = new boolean[4][4]; 
				BogglePoint point = new BogglePoint(i, j);
				int pos = 0;
				if (onBoard_Internal(point, word.toCharArray(), pos, visited)){
					return true;
				} 
			}
		}
		return false;
	}

	private boolean onBoard_Internal(BogglePoint point, char[] word, int pos, boolean[][] visited) { 
		if (board[point.x][point.y] != word[pos]){
			return false;
		}
		if (word.length - 1 == pos){
			return true;
		}
		visited[point.x][point.y] = true;
		pos ++;
		BogglePoint[] points = getAdjacent(point);
		for (int i = 0; i < points.length; i ++){
			BogglePoint nextPoint = points[i];
			if (nextPoint != null){
				if (!visited[nextPoint.x][nextPoint.y] && onBoard_Internal(nextPoint, word, pos, visited)){
					return true;
				}
			}
		}
		visited[point.x][point.y] = false;
		return false;
	}

	private BogglePoint[] getAdjacent(BogglePoint point){
		BogglePoint[] adjacent = new BogglePoint[8];
		for (int i = 0; i < adjacent.length; i ++){
			adjacent[i] = null;
		}
		int counter = -1;
		for (int i = -1; i <= 1; i ++){
			for (int j = -1; j <= 1; j ++){
				BogglePoint point2 = new BogglePoint(point.x + i, point.y + j);
				if (point2.x >= 0 && point2.x <= 3 && point2.y >= 0 && point2.y <= 3 && (i != 0 || j != 0)){
					counter ++;
					adjacent[counter] = point2;
				}
			}
		}
		return adjacent;
	}

	public char [][] getBoard(){ // return the board
		return board;
	}

	public char getLetter(int i, int j){ // get one char form the board
		return board[i][j];
	}

	public void addWord(String word){ // add a word to played words
		words.add(word);
	}

	public boolean checkWord(String word){ // check if a word has been played
		if (words.contains(word)){
			return true;
		}
		return false;
	}

	public ArrayList<String> returnWords(){ //return played words
		return words;
	}

	public ArrayList<String> getWords(){ //return all possible words
		ArrayList<String> output = new ArrayList<>();
		output.add(".");
		Dictionary dictionary = new Dictionary();
		String temp = dictionary.getFirst().toUpperCase();
		while (dictionary.hasNext()){
			if (onBoard(temp) && temp.length() >= 3 && !words.contains(temp)){
				output.add(temp);
				numAllWords++;
			}
			temp = dictionary.getNext().toUpperCase();
		}
		String message = "You got: " + words.size() + "/" + (output.size() - 1) + " words";
		output.set(0, message);
		return output;
	}
	public double getPercentage(){
		double percentage = words.size()/(double)numAllWords;
		return percentage;
	}
}
