import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BoggleCubeSet {

	private ArrayList<String> rawData = new ArrayList<>();
	private ArrayList<String> buffer = rawData;
	private int counter = 0;
	private ArrayList<Character> output = new ArrayList<>();

	public BoggleCubeSet(String filename){
		rawData = loadStrings(filename);
		buffer = loadStrings(filename);
	}

	private static ArrayList<String> loadStrings(String fileName) 
	{
		String line = "";
		ArrayList<String> toReturn = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) 
		{

			while ((line = br.readLine()) != null) 
			{
				toReturn.add(line);
			}

		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public void reset(){
		Random rand = new Random();
		buffer.clear();
		buffer = rawData;
		for (int i = 0; i < 16; i ++){
			int x = rand.nextInt(buffer.size());
			int y = rand.nextInt(6);
			char ch = buffer.get(x).charAt(y);
			buffer.set(x, buffer.get(buffer.size() - 1));
			buffer.remove(buffer.size() - 1);
			output.add(ch);
		}
		counter = -1;
	}

	public char getCubeLetter(){
		counter ++;
		return output.get(counter);
		}
}
