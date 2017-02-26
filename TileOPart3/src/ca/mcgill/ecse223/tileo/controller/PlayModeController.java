package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.btms.application.BtmsApplication;
import ca.mcgill.ecse223.btms.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionCard;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class PlayModeController {

	// METHODS
	
	/*
	 * 1. Start a game (shuffle the action cards, place players on board)
	 * Charles
	 */
	public void startGame(Game selectedGame) throws InvalidInputException {
		//TODO: CHARLES
	}

	
	/*
	 * 2. Take a turn (roll the die, move to new position)
	 * Charles
	 */
	public void moveToTile(Tile chosenTile) throws InvalidInputException {
		//TODO: CHARLES
	}

	
	/*
	 * 3. Land on a tile (basic behavior for hidden, regular, and action tiles)
	 * Chris
	 */
	public void landedOnTile() {
		//TODO: CHRIS
	}
	
	
	/*
	 * 4. Take the first card from the deck of cards
	 * CM
	 */
	public void createWinTile(int x, int y) throws InvalidInputException {
		//TODO: CM
	}

	
	/*
	 * 5. Action card "Roll the die for an extra turn"
	 * CM
	 */
	public void rollDieExtraTurn() {
		//TODO: CM
	}

	
	/*
	 * 6. Action card "Connect two adjacent tiles with a connection piece from the pile of spare connection pieces"
	 * Justin
	 */
	public void playConnectTilesActionCard (Tile selectedTile1, Tile selectedTile2) throws InvalidInputException{
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		Deck deck = currentGame.getDeck();
		ConnectTilesActionCard connectTilesActionCard = (ConnectTilesActionCard) deck.getCard(0);
		
		try 
		{
			connectTilesActionCard.play(selectedTile1, selectedTile2);
			currentGame.setCurrentPlayer(currentGame.getPlayer(currentGame.indexOfPlayer(currentGame.getCurrentPlayer())+1));
			deck.setCurrentCard(deck.getCard(deck.indexOfCard(deck.getCurrentCard())+1));
			currentGame.setMode(Mode.GAME);
		}
		catch (RuntimeException e) 
		{
			throw new InvalidInputException(e.getMessage());
		}
	}
	
	
	/*
	 * 7. Action card "Remove a connection piece from the board and place it in the pile of spare connection pieces"
	 * Li
	 */
	public void playRemoveConnectionActionCard(Connection connection) throws InvalidInputException {
		//TODO: LI
		TileO tileO = new TileOApplication();
		Game currentGame = tileO.getCurrentGame();
		currentGame.getConnections();
		
		//validation check: connection needs to be in connections of currentGame
		//TODO: to everyone, how the fuck do you do that validation???
		/*
		if (connection instanceof currentGame.getConnections()) {
			
		}
		*/
		Deck deck = currentGame.getDeck();
		ActionCard currentCard = deck.getCurrentCard();
		//current card needs to be a remove connection action card
		if (currentCard.
						
						
	}

	
	/*
	 * 8. Action card "Move your playing piece to an arbitrary tile that is not your current tile"
	 * Victor
	 */
	public void playTeleportActionCard(Tile tile) throws InvalidInputException {
		//TODO: VICTORIQUE
		
		//TeleportActionCard.play(tile)
//		try {
//			TeleportActionCard.play(tile);
//		}
//		catch (RuntimeException e) {
//			throw new InvalidInputException(e.getMessage());
//		}
		
		
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		Deck deck = currentGame.getDeck();
		TeleportActionCard teleportcard = (TeleportActionCard) deck.getCurrentCard();

		try{
		teleportcard.play(tile);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		
		//set mode back to game?
		//edit added by Li @1:27 pm
		currentGame.setMode();	//expects the mode "GAME" but i don't know how that works
	}

	
	/*
	 * 9. Save and load game to continue playing at a later point
	 * Li
	 */
	public void saveGame() {
		//TODO: LI
	}
	
	public Game loadGame(Game aGame) throws InvalidInputException {
		//TODO: LI
		//set mode to "GAME" when loading a game
		return aGame;
		
	}

	
	
}