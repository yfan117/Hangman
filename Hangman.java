/* Created by: Yulong Fan
 * 
 * Hangman.java
 * main class for this mini-game
 *  
 * required files:
 * HANG1.png 
 * HANG2.png
 * HANG3.png
 * HANG4.png
 * HANG5.png
 * HANG6.png
 * HANG7.png
 * HANG8.png
 * tryButton.png
 * reButton.png
 * dictionary.txt
 * Display.java
 * 
 * 
 * last edited: 02/23/2020
 */


//package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.* ;



public class Hangman {
	
	//initialize basic settings and display
	static int picOrder = 1;
	static String maskedWord = "";
	static String vocab ="";
	static String imageName = "HANG1.png";
	static int vocabLength = 0;
	static Display display;
	static int numCorrect = 0;
	static int numFail = 0;
	static String previousInput = "";
	static String wrongInput = "";

	
	public static void main(String[] args) 
	{	
		vocab = pickWord();
		vocabLength = vocab.length();
		maskedWord = initWord(vocabLength);
		
		display = new Display(imageName, maskedWord, wrongInput);
		
		System.out.println(vocab);
		
	}
	
	
	
	//restore to default settings
	//initialize default display
	public void reStart()
	{
		imageName = "HANG1.png";
		previousInput = "";
		picOrder = 1;
		numCorrect = 0;
		numFail = 0;
		vocab = pickWord();
		vocabLength = vocab.length();
		maskedWord = initWord(vocabLength);
		wrongInput = "";
	
		System.out.println(vocab);

		display.setEnd(false);
		display.createDisplay(imageName, maskedWord, wrongInput);
	}
	
	
	
	//determines if input has been previously entered
	static boolean doesExist(char input)
	{
		boolean result = false;
		for (int i = 0; i< previousInput.length(); i++)
		{
			if ( previousInput.charAt(i) == input)
			{
				return true;
			}
		}
		
		return result;
	}
	
	
	//the main function that makes everything works
	public void updateGame(char input)
	{
		//used for testing
		System.out.println(vocab);
		
		boolean isExist = doesExist(input);
		if((isExist == true)||(input == '0'))
		{
			//if input previously entered, display and variables doesn't change
			display.createDisplay(imageName, maskedWord, wrongInput);
		
		}
		else
		{
		
			boolean match = isMatch (input);
	
			if (match == false)
			{
				//keep track of incorrect inputs
				wrongInput = wrongInput + input	;
			}
			
			//keep track of all inputs, correct and incorrect
			previousInput = previousInput + input;

			
			imageName = updateImageName (match);
			updateMaskedWord (input);
			
			System.out.println(maskedWord);
			
			if ((numCorrect == vocabLength) || (imageName == "HANG8.png"))
			{
				//round ends, user won
				display.setEnd(true);
				display.createDisplay("HANG8.png", vocab, wrongInput);
			}
			else if ((wrongInput.length() == 6)||(imageName == "HANG7.png"))
			{
				//round ends, user lost
				display.setEnd(true);
				display.createDisplay("HANG7.png", vocab, wrongInput);
	
			}
			else
			{
					//normal display updates
					display.createDisplay(imageName, maskedWord, wrongInput);	
				
			}
			
		}
		
		
	}
	
	//updates the correct letter display
	static void updateMaskedWord(char input)
	{
		char[] updatedWord = new char [vocabLength] ;
		
		for( int i = 0; i< vocabLength; i++)
		{
			if(vocab.charAt(i) == input)
			{
				numCorrect ++;
				updatedWord[i] = input;
			}
			else
			{	
				updatedWord[i] = maskedWord.charAt(i);
			}
		}
		
		maskedWord = "";
		
		for( int i = 0; i< vocabLength; i++)
		{
			maskedWord = maskedWord + updatedWord[i];
		}

	}
	
	
	//updates which image to call
	static String updateImageName(boolean match)
	{	
		String ImageName = "";
		
			
		if(match == false)
		{
			picOrder ++;
			ImageName = "HANG" + picOrder + ".png";
		}
		else
		{
			ImageName = "HANG" + picOrder + ".png";
		}
		
		return ImageName;
	}
	
	static boolean isMatch(char input)
	{
		boolean result = false;
		
		for(int i = 0; i< vocabLength; i++)
		{
			if(vocab.charAt(i) == input)
			{
				return true;
			}
		}
		
		
		return result;
	}
	
	
	//original display of correct letters guessed
	static String initWord(int vocabLength)
	{
		String maskedWord = "";
		
		for(int i =0; i< vocabLength; i++)
		{
			maskedWord = maskedWord +"-";
		}
		
		return maskedWord;
	}
	
	
	//randomly pick a vocab from the word bank
	static String pickWord()
	{
		File dictionary = new File("dictionary.txt");
		
		Scanner fileScan = null;
		
		try {
			fileScan = new Scanner(dictionary);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			System.out.println("file not found");
			System.exit(0);
		}
		
		
		int vocabPlace = new Random().nextInt(2207);//2207 is the number of words in the dictionary.txt
		String vocab ="";
		
		for(int i = 0; i<=vocabPlace; i++)
		{
			if(i == vocabPlace)
			{
				vocab = fileScan.nextLine();
			}
			
			fileScan.nextLine();
			
		}
			fileScan.close();
		
		
		return vocab;
	}
	
}
