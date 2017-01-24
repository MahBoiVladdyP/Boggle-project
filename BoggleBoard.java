import java.util.ArrayList;

public class BoggleBoard {

	public BoggleBoard() {
		makeBoard();
	}

	private char[][] board = new char[4][4];

	private void makeBoard() {
		BoggleCubeSet b = new BoggleCubeSet("BoggleCubes.txt");
		b.reset();
		for (int i = 0; i < 4; i ++){
			for (int j = 0; j < 4; j ++){
				board[i][j] = b.getCubeLetter();
			}
		}
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
					System.out.print(" ");
				}
				System.out.println("");
			}
		}
	}

	public boolean onBoard(String word){ // make sure that the input word is uppercase
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


	public boolean onBoard(String word){ // make sure that the input word is uppercase
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
}
