import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Dictionary 
{
	public static void main(String[] args) throws FileNotFoundException //tester code 
	{
		Scanner scan = new Scanner (new File ("Boggle.txt")); //scanning the dictionary that's in a text file
		String word;
		Scanner inputs = new Scanner(System.in);//scanner for testing 
		
		ArrayList<String> wordList = new ArrayList<String>();//
		
		while (scan.hasNext()) //looping through the scanner and adding each word to the next line of the created ArrayList
		{
            word = scan.next();
            wordList.add(word.substring(0, word.length()-1));
                        
        }
		
		for(int i = 0; i < wordList.size(); i++)//printing the list out
		{
			System.out.println(wordList.get(i));
		}
		
		for(int i = 0; i < wordList.size(); i++)//Checks to see if the passed string is in the dictionary 
		{
			System.out.print("Enter a word ");
			String a = inputs.nextLine();
			
			if(wordList.contains(a))
				System.out.println("Found");
			else
				System.out.println("Does not exist");
		}
		
	}
	
	private ArrayList<String>wordList = new ArrayList<String>();
	private Scanner scan;
	private String b;
	
	public Dictionary()//default constructor 
	{
		try 
		{
			scan = new Scanner (new File ("Boggle.txt"));
		} catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		editWord();
	}
	
	public boolean isWord(String s)//tells if the passed string is a word or not
	{
		b = s;
		b = b.toLowerCase();
		if(wordList.contains(b))
			return true;
		
		return false;
		
	}
	
	private void editWord()//edits the word passed to solve our backslash issue.
	{
		String word;
		while (scan.hasNext()) 
		{
            word = scan.next();
            wordList.add(word.substring(0, word.length()-1));         
        }
		
	}
	
	public String getFirst()//Gets the first word in the dictionary
	{
		return wordList.get(0);
	}
	
	public String getNext()//gets the next word in the dictionary after one has been passed. If no word is found after, then the loop ends.	
	{
		String next;
		int index;
		index = wordList.indexOf(b);
		next = wordList.get(index+1);
		return next;
	}
	
	public boolean hasNext(String s)//checks to see if there is another word after the passed word.
	{
		b.toLowerCase();
		if(scan.hasNext(b))
			return true; 
		return false;
	}
	
	    
	 
}
