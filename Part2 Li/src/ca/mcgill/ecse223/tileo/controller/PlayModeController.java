package ca.mcgill.ecse223.tileo.controller;

import java.util.List;


import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.ActionCard;
import ca.mcgill.ecse223.tileo.model.ConnectTilesActionCard;
import ca.mcgill.ecse223.tileo.model.Connection;
import ca.mcgill.ecse223.tileo.model.Deck;
import ca.mcgill.ecse223.tileo.model.Game;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.model.Player;
import ca.mcgill.ecse223.tileo.model.RemoveConnectionActionCard;
import ca.mcgill.ecse223.tileo.model.RollDieActionCard;
import ca.mcgill.ecse223.tileo.model.TeleportActionCard;
import ca.mcgill.ecse223.tileo.model.Tile;
import ca.mcgill.ecse223.tileo.model.TileO;

public class PlayModeController {
	/*
	 * 1. Start a game (shuffle the action cards, place players on board)
	 * Charles
	 */
	public void startGame(Game selectedGame) throws InvalidInputException {
		//TODO: CHARLES
		String error = "";
		if(selectedGame.getDeck().numberOfCards() != 32) {
			error = "The action cards in the deck must be equal to 32.";
		}
		if(selectedGame.hasWinTile() == false) {
			error = "The winTile does not exist." ;
		}
		for(int i = 0 ; i < selectedGame.numberOfPlayers(); i++) {
			if(selectedGame.getPlayer(i).hasStartingTile() == false){
				error = "Player " + (i+1) + " does not have a starting tile." ;
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
			for(int i = 0 ; i < selectedGame.numberOfTiles(); i++){
				selectedGame.getTiles().get(i).setHasBeenVisited(false);
			}
			
			for(int j = 0; j < selectedGame.numberOfPlayers(); j++){
				Tile startingTile = selectedGame.getPlayer(j).getStartingTile();
				selectedGame.getPlayers().get(j).setCurrentTile(startingTile);
				selectedGame.getPlayers().get(j).getCurrentTile().setHasBeenVisited(true);
			}
			
			selectedGame.setCurrentPlayer(selectedGame.getPlayers().get(0));
			selectedGame.setCurrentConnectionPieces(Game.SpareConnectionPieces);
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
		List<Tile> possibleMoves = currentGame.rollDie();
		return possibleMoves; //method in Game class
	}
	
	
	/*
	 * 3. Land on a tile (basic behavior for hidden, regular, and action tiles)
	 * Chris
	 */
	public void landedOnTile(Tile tile) throws InvalidInputException{		
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		List<Tile> tiles = currentGame.getTiles();
	
		String error = "";
		if(tiles.contains(tile) == false){
			error = "The tile does not exist in the Board.";
		}
		if (error.length() > 0){
			throw new InvalidInputException(error.trim());
		}
		
		try{
			tile.land();
		}
		catch(RuntimeException e){
			throw new InvalidInputException(e.getMessage());
		}
	}

	
	/*
	 * 4. Take the first card from the deck of cards
	 * CM
	 */
	//helper method called within this controller
	private ActionCard drawCard(Game currentGame) {		
		Deck deck = currentGame.getDeck();
		ActionCard drawnCard = deck.getCurrentCard();
		ActionCard nextCard;
		
		if(deck.indexOfCard(drawnCard) < 31){ //index range: [0,31]
			nextCard = deck.getCard(deck.indexOfCard(drawnCard) + 1);
		}
		else{ //the index of the card must be 31, which is the last card
			deck.shuffle();
			nextCard = deck.getCard(0);
		}
		deck.setCurrentCard(nextCard);		
		return drawnCard;		
	}

	
	/*
	 * 5. Action card "Roll the die for an extra turn"
	 * CM
	 */
	public void playRollDieExtraTurn() {
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();		
		//the drawn card needs to be a rollDieActionCard since this method is only called in the appropriate mode
		RollDieActionCard rollDieActionCard = (RollDieActionCard) drawCard(currentGame);
		
		rollDieActionCard.play();
		
		currentGame.setMode(Mode.GAME);				
	}

	
	/*
	 * 6. Action card "Connect two adjacent tiles with a connection piece from the pile of spare connection pieces"
	 * Justin
	 */
	public void playConnectTilesActionCard (Tile selectedTile1, Tile selectedTile2) throws InvalidInputException{
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		ConnectTilesActionCard connectTilesActionCard = (ConnectTilesActionCard) drawCard(currentGame);
		
		String error = "";
		// Check if there are tiles that can be selected in the game
		if (currentGame.numberOfTiles() < 2){
			error = "There are less than 2 tiles in the current game.";
		}
		// Check if the selected tiles are adjacent
		if (!isAdjacent(selectedTile1, selectedTile2)){
			error = "The selected tiles are not adjacent.";
		}
		// Check if connection pieces are available
		if (currentGame.getCurrentConnectionPieces() < 1){
			error = "There are 0 connection pieces available.";
		}
		// Check if connection already exist at a location
		if (isConnected(selectedTile1, selectedTile2)){
			error = "The two selected tiles are already connected.";
		}
		// Check if errors are detected
		if (error.length() > 0){
			throw new InvalidInputException (error.trim());
		}
		
		try {
			// Connect the two selected tiles
			connectTilesActionCard.play(selectedTile1, selectedTile2);			
			setNextPlayer(currentGame);
			currentGame.setMode(Mode.GAME);
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
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
			ActionCard currentCard = drawCard(currentGame);
			
			try {
				if (currentCard instanceof RemoveConnectionActionCard) {				
					((RemoveConnectionActionCard) currentCard).play(connection);
				}				
				setNextPlayer(currentGame);		
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
		
		TileO tileO = TileOApplication.getTileO();
		Game currentGame = tileO.getCurrentGame();
		ActionCard currentCard= drawCard(currentGame);
		List<Tile> tiles = currentGame.getTiles();
		String error = "";
		if(tiles.contains(tile)==false){
			error = "Please select an existing tile on the board.";
		}
		if(error.length() > 0){
			throw new InvalidInputException(error.trim());
		}
		
		try{
			if (currentCard instanceof TeleportActionCard){
				((TeleportActionCard) currentCard).play(tile);
			}
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
		TileOApplication.save();
	}
	
	public Game loadGame(int index) throws InvalidInputException {
		TileO tileO = TileOApplication.getTileO();
		try {
			Game loadedGame = tileO.getGame(index);
			loadedGame.setMode(Mode.GAME);
			return loadedGame;
		}
		catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}				
	}
	
	
	//helper method for setting the next player
	public void setNextPlayer(Game aGame) {
		List<Player> playerList = aGame.getPlayers();
		Player currentPlayer = aGame.getCurrentPlayer();
		int playerIndex = aGame.indexOfPlayer(currentPlayer);
				
		//checks if current player is the last player
		if (playerIndex + 1 == playerList.size()) {
			//if it is, set the first player to current player
			aGame.setCurrentPlayer(playerList.get(0));
		}
		//if it's not, set the next player
		else {
			Player nextPlayer = playerList.get(playerIndex + 1);
			aGame.setCurrentPlayer(nextPlayer);
		}		
	}
	
	//helper method to check if tiles are adjacent
	public boolean isAdjacent(Tile tile1, Tile tile2) {
		int x1 = tile1.getX();
		int y1 = tile1.getY();
		int x2 = tile2.getX();
		int y2 = tile2.getY();
		if (x1-x2 == -1 || x1-x2 == 1){
			if(y1-y2 ==0){
				return true;
			}
		}
		if (y1-y2 == -1 || y1-y2 == 1){
			if(x1-x2 ==0){
				return true;
			}
		}
		
		
		return false;
	}
	
	//helper method two tiles are already connected
		public boolean isConnected (Tile tile1, Tile tile2){
			for (Connection c1: tile1.getConnections()){
				for (Connection c2: tile2.getConnections()){
					if (c1 == c2){
						return true;
					}
				}
			}
			return false;
		}
	
}
