import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BoggleCubeSet {

	private ArrayList<String> rawData = new ArrayList<>();
	private ArrayList<Boolean> checked = new ArrayList<>();

	public BoggleCubeSet(String filename){
		rawData = loadStrings(filename);
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

	private void reset(){
		for (int i = 0; i < 16; i ++){
			checked.set(i, false);
		}
	}

	public char getCubeLetter(){
		Random rand = new Random();
		int y = rand.nextInt(6);
		while (true){
			int x = rand.nextInt(16);
			if (checked.get(x) == false){
				checked.set(x, true);
				return rawData.get(x).charAt(y);
			}
		}
	}
}
