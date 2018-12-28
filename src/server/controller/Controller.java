package server.controller;

import java.io.IOException;
import java.util.LinkedList;

import server.model.Game;
import shared.GameData;

public class Controller
{
	
	Game game;
	
	public Controller(Game game)
	{
		this.game = game;
	}

	public void receiveGuess(String string) throws IOException
	{
		game.receiveGuess(string);
	}

	public GameData getGameData()
	{
		return game.getGameData();
	}

}
