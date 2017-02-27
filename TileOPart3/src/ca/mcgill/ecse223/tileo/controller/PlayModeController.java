package ca.mcgill.ecse223.tileo.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionCard;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.model.Die;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
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
		String error = "";
		if(selectedGame.getDeck().numberOfCards()!= 32){
			error = "The action cards in the Deck must be equal to 32. ";
		}
		if(selectedGame.hasWinTile() == false) {
			error = error+"The winTile does not exist. " ;
		}
		for(int i=0 ;i<selectedGame.numberOfPlayers();i++) {
			if(selectedGame.getPlayer(i).hasStartingTile() == false){
				error = error+"The player"+(i+1)+" does not have a startingTile. " ;
			}
		}
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		
		TileO tileO = TileOApplication.getTileO();
		try {
			tileO.setCurrentGame(selectedGame);
			Deck deck = selectedGame.getDeck();
			deck.shuffle();
			for(int i = 0 ; i < selectedGame.numberOfTiles();i++){
				selectedGame.getTiles().get(i).setHasBeenVisited(false);
			}
			for(int j = 0; j < selectedGame.numberOfPlayers();j++){
				Tile startingTile = selectedGame.getPlayer(j).getStartingTile();
				selectedGame.getPlayers().get(j).setCurrentTile(startingTile);
				selectedGame.getPlayers().get(j).getCurrentTile().setHasBeenVisited(true);
			}
			selectedGame.setCurrentPlayer(selectedGame.getPlayers().get(0));
			selectedGame.setCurrentConnectionPieces(selectedGame.SpareConnectionPieces);
			selectedGame.setMode(Mode.GAME);
		}
		catch(RuntimeException e){
			error = e.getMessage();
			throw new InvalidInputException(error);
		}		
	}

	
	/*
	 * 2. Take a turn (roll the die, move to new position)
	 * Charles
	 */
	public List<Tile> rollDie(){
		//TODO: CHARLES
		TileO tileO = TileOApplication.getTileO(); 
		Game currentGame = tileO.getCurrentGame();
		Die die = currentGame.getDie();
		int number = die.roll();
		Player currentPlayer = currentGame.getCurrentPlayer();
		List<Tile> possibleMoves = new ArrayList<Tile>();
		possibleMoves = currentPlayer.getPossibleMoves(number);
		return possibleMoves;	
	}
	
	/*
	 * 3. Land on a tile (basic behavior for hidden, regular, and action tiles)
	 * Chris
	 */
	public void landedOnTile(Tile tile){
		
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		List<Tile> tiles = currentGame.getTiles();
		if(tiles.contains(tile) == false){
			
			
		}else
			tile.land();
		
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
		ConnectTilesActionCard connectTilesActionCard = (ConnectTilesActionCard) deck.getCurrentCard();
		Player currentPlayer = currentGame.getCurrentPlayer();
		
		int x1 = selectedTile1.getX();
		int y1 = selectedTile1.getY();
		int x2 = selectedTile2.getX();
		int y2 = selectedTile2.getY();
		
		String error = "";
		// Check if there are tiles that can be selected in the game
		if (currentGame.numberOfTiles() < 2){
			error = "There are less than 2 tiles in the current game.";
		}
		// Check if the selected tiles are adjacent
		if ((x1 != x2+1 && y1 == y2) || (x1 != x2-1 && y1 == y2)){
			error = error + "The selected tiles are not adjacent";
		}
		if ((y1 != y2+1 && x1 == x2) || (y1 != y2-1 && x1 == x2)){
			error = error + "The selected tiles are not adjacent";
		}
		// Check if connection pieces are available
		if (currentGame.getCurrentConnectionPieces() < 1){
			error = error + "There are 0 connection pieces in the game";
		}
		// Check if errors are detected
		if (error.length() > 0){
			throw new InvalidInputException (error.trim());
		}
		
		try 
		{
			// Connect the two selected tiles
			connectTilesActionCard.play(selectedTile1, selectedTile2);
			
			// Check if current player is last player
			if (currentPlayer.getNumber() == currentGame.numberOfPlayers()){
				currentGame.setCurrentPlayer(Player.getWithNumber(1));
			}
			else{
				currentGame.setCurrentPlayer(currentGame.getPlayer(currentGame.indexOfPlayer(currentPlayer)+1));
			}
			// Check if current card is the last card
			if (deck.numberOfCards() == 1){
				deck.shuffle();
			}
			else{
				deck.setCurrentCard(deck.getCard(deck.indexOfCard(deck.getCurrentCard())+1));
			}
			
			// Set game mode to GAME
			currentGame.setMode(Mode.GAME);
		}
		catch (RuntimeException e) 
		{
			error = e.getMessage();
			throw new InvalidInputException(error);
		}
	}
	
	
	/*
	 * 7. Action card "Remove a connection piece from the board and place it in the pile of spare connection pieces"
	 * Li
	 */
	public void playRemoveConnectionActionCard(Connection connection) throws InvalidInputException {
		//TODO: LI
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		List<Connection> connectionList = currentGame.getConnections();
		
		if (connectionList.contains(connection)) {
			Deck deck = currentGame.getDeck();
			ActionCard currentCard = deck.getCurrentCard();
			Player currentPlayer = currentGame.getCurrentPlayer();
			
			try {
				//current card needs to be a remove connection action card
				if (currentCard instanceof RemoveConnectionActionCard) {
					//play(connection)???
					//"play() needs to be added to the removeconnectionactioncard class"					
					((RemoveConnectionActionCard) currentCard).play(connection);
				}
				
				//checks if current player is the last player
				if (currentPlayer.getNumber() == currentGame.getPlayers().size()) {
					//if it is, set the first player to current player
					currentGame.setCurrentPlayer(Player.getWithNumber(1));
				}
				//if it's not, set the next player
				if (currentPlayer.getNumber() != currentGame.getPlayers().size()) {
					currentGame.setCurrentPlayer(currentGame.getPlayer(currentGame.indexOfPlayer(currentPlayer)+1));
				}		
				currentGame.setMode(Mode.GAME);
			} 
			catch (RuntimeException e) {
				throw new InvalidInputException(e.getMessage());	
			}
		}						
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
		deck.setCurrentCard(deck.getCard(deck.indexOfCard(teleportcard)+1));

		try{
		teleportcard.play(tile);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
		
	
	}

	
	/*
	 * 9. Save and load game to continue playing at a later point
	 * Li
	 */
	public void saveGame() {
		//TODO: LI
		//TileO tileO = TileOApplication.getTileO();
		//Game currentGame = tileO.getCurrentGame();
		TileOApplication.save();
		
	}
	
	public Game loadGame(Game aGame) throws InvalidInputException {
		//TODO: LI
		//set mode to "GAME" when loading a game
		
		TileO tileO = TileOApplication.getTileO();
		try {
			int gameIndex = tileO.indexOfGame(aGame);
			Game loadedGame = tileO.getGame(gameIndex);
			loadedGame.setMode(Mode.GAME);
			return loadedGame;
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}				
	}

	
	
}
