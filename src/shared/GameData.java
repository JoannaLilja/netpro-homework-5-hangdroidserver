package shared;

import java.io.Serializable;
import java.util.LinkedList;

public class GameData implements Serializable
{

	private int triesLeft;
	private int points;
	private String displayWord;
	private char winLoss;
	private LinkedList<String> guessHistory;
	
	/**
	 * Creates a new object of the class GameData
	 * 
	 * @param triesLeft
	 * @param points
	 * @param displayWord
	 * @param winLoss
	 */
	public GameData(int triesLeft, int points, String displayWord, char winLoss, LinkedList<String> guessHistory)
	{
		this.triesLeft = triesLeft;
		this.points = points;
		this.displayWord = displayWord;
		this.winLoss = winLoss;
		this.guessHistory = guessHistory;
	}
	
	public int getTriesLeft()
	{
		return triesLeft;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public String getDisplayWords()
	{
		return displayWord;
	}
	
	public char getWinLoss()
	{
		return winLoss;
	}
	
	public LinkedList<String> getGuessHistory()
	{
		return guessHistory;
	}
	
	
}
