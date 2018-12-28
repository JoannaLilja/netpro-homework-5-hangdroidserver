package server.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.Stream;

import shared.GameData;


/**
 * Stores game data and holds functionality related to the game
 *
 */
public class Game
{
		
	private int triesLeft;
	private int points;
	private String currentWord;
	private StringBuilder displayWord = new StringBuilder();
	private char winLoss = '-';
	private LinkedList<String> guessHistory = new LinkedList<String>();

	
	public Game()
	{		
		try {
			pickNewWord();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
	public GameData getGameData()
	{
        return new GameData(triesLeft, points, displayWord.toString(), winLoss, guessHistory);
	}
	
	char getWinLoss()
	{
		return winLoss;
	}
	
	int getTriesLeft()
	{
		return triesLeft;
	}
	
	int getPoints()
	{
		return points;
	}
	
	String getDisplayWord()
	{
		return displayWord.toString();
	}
	
	private void pickNewWord() throws IOException
	{
		String line;
		Random r;
		int randomLine;

		
		if (displayWord.length()>0)displayWord.delete(0, displayWord.length()+5);
		
		r = new Random();
		randomLine = r.nextInt(51528) + 1;	
		
		Stream<String> lines = Files.lines(Paths.get("data/words.txt"));
		    line = lines.skip(randomLine).findFirst().get();

		currentWord = line;
		
		
		for(int i = 0; i < currentWord.length(); i++)
			displayWord.append('_');
		
		triesLeft = currentWord.length();
		
		System.out.println(currentWord);
		
		
	}
	
	public void receiveGuess(String guess) throws IOException
	{
		Guess g = new Guess(guess);
		
		if (g.isValid())
		{
			guessHistory.add(guess);
			if (g.isWordGuess)
				receiveWordGuess(guess);
			else
				receiveLetterGuess(guess.charAt(0));
		}
			
	}
	
	private void receiveWordGuess(String guess) throws IOException
	{
		if (guess.equalsIgnoreCase(currentWord))
		{
			points++;
			pickNewWord();
			winLoss = 'w';
			guessHistory.clear();
		}
		else if(triesLeft <= 0)
		{
			points--;
			pickNewWord();
			winLoss = 'l';
			guessHistory.clear();

		}
		else
		{
			triesLeft--;
		}

	}
	
	private void receiveLetterGuess(char guess) throws IOException
	{
		
		boolean correct = false;
		
		winLoss = '-';

		
		for (int i = 0; i < currentWord.length(); i++)
			if (currentWord.charAt(i) == guess)
			{
				displayWord.setCharAt(i, guess);
				correct = true;

			}
		if(correct == false)
		{
			triesLeft--;
			if(triesLeft <= 0)
			{
				points--;
				pickNewWord();
				winLoss = 'l';
			}
		}
		
	}
	

}
