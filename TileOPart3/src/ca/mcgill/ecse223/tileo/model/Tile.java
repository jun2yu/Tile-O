/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;
import java.util.*;

// line 29 "../../../../../TileOPersistence.ump"
// line 32 "../../../../../TileO (updated Feb10).ump"
public abstract class Tile implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private int x;
  private int y;
  private boolean hasBeenVisited;

  //Tile Associations
  private List<Connection> connections;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(int aX, int aY, Game aGame)
  {
    x = aX;
    y = aY;
    hasBeenVisited = false;
    connections = new ArrayList<Connection>();
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create tile due to game");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setX(int aX)
  {
    boolean wasSet = false;
    x = aX;
    wasSet = true;
    return wasSet;
  }

  public boolean setY(int aY)
  {
    boolean wasSet = false;
    y = aY;
    wasSet = true;
    return wasSet;
  }

  public boolean setHasBeenVisited(boolean aHasBeenVisited)
  {
    boolean wasSet = false;
    hasBeenVisited = aHasBeenVisited;
    wasSet = true;
    return wasSet;
  }

  public int getX()
  {
    return x;
  }

  public int getY()
  {
    return y;
  }

  public boolean getHasBeenVisited()
  {
    return hasBeenVisited;
  }

  public boolean isHasBeenVisited()
  {
    return hasBeenVisited;
  }

  public Connection getConnection(int index)
  {
    Connection aConnection = connections.get(index);
    return aConnection;
  }

  public List<Connection> getConnections()
  {
    List<Connection> newConnections = Collections.unmodifiableList(connections);
    return newConnections;
  }

  public int numberOfConnections()
  {
    int number = connections.size();
    return number;
  }

  public boolean hasConnections()
  {
    boolean has = connections.size() > 0;
    return has;
  }

  public int indexOfConnection(Connection aConnection)
  {
    int index = connections.indexOf(aConnection);
    return index;
  }

  public Game getGame()
  {
    return game;
  }

  public static int minimumNumberOfConnections()
  {
    return 0;
  }

  public static int maximumNumberOfConnections()
  {
    return 4;
  }

  public boolean addConnection(Connection aConnection)
  {
    boolean wasAdded = false;
    if (connections.contains(aConnection)) { return false; }
    if (numberOfConnections() >= maximumNumberOfConnections())
    {
      return wasAdded;
    }

    connections.add(aConnection);
    if (aConnection.indexOfTile(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aConnection.addTile(this);
      if (!wasAdded)
      {
        connections.remove(aConnection);
      }
    }
    return wasAdded;
  }

  public boolean removeConnection(Connection aConnection)
  {
    boolean wasRemoved = false;
    if (!connections.contains(aConnection))
    {
      return wasRemoved;
    }

    int oldIndex = connections.indexOf(aConnection);
    connections.remove(oldIndex);
    if (aConnection.indexOfTile(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aConnection.removeTile(this);
      if (!wasRemoved)
      {
        connections.add(oldIndex,aConnection);
      }
    }
    return wasRemoved;
  }

  public boolean addConnectionAt(Connection aConnection, int index)
  {  
    boolean wasAdded = false;
    if(addConnection(aConnection))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfConnections()) { index = numberOfConnections() - 1; }
      connections.remove(aConnection);
      connections.add(index, aConnection);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveConnectionAt(Connection aConnection, int index)
  {
    boolean wasAdded = false;
    if(connections.contains(aConnection))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfConnections()) { index = numberOfConnections() - 1; }
      connections.remove(aConnection);
      connections.add(index, aConnection);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addConnectionAt(aConnection, index);
    }
    return wasAdded;
  }

  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeTile(this);
    }
    game.addTile(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Connection> copyOfConnections = new ArrayList<Connection>(connections);
    connections.clear();
    for(Connection aConnection : copyOfConnections)
    {
      if (aConnection.numberOfTiles() <= Connection.minimumNumberOfTiles())
      {
        aConnection.delete();
      }
      else
      {
        aConnection.removeTile(this);
      }
    }
    Game placeholderGame = game;
    this.game = null;
    placeholderGame.removeTile(this);
  }


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "x" + ":" + getX()+ "," +
            "y" + ":" + getY()+ "," +
            "hasBeenVisited" + ":" + getHasBeenVisited()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null")
     + outputString;
  }  
  
  //------------------------
  public List<Tile> getNextMoves (int moveLeft,Tile previousTile) {
	  //Create a ArrayList to store the neighbor Tile
	  List<Tile> possibleMoveTiles =  new ArrayList<Tile>();
	  int i = 0;
	  Tile neighborTile;
      
	  //If the moveLeft is 1, then add Tile itself into list possibleMoveTiles
	  if(moveLeft==1) {
		  possibleMoveTiles.add(this);
		  return possibleMoveTiles;
	  }
	  
	  /*If the Tile only has one connection, and it connects with previousTiles, it means this Tile cannot get possible moves anymore,
	   therefore, add the Tile itself into list possibleMoveTiles*/
	  if(this.getConnections().size()==1 && (getConnection(0).getTile(0).equals(previousTile)||getConnection(0).getTile(1).equals(previousTile))){
		  possibleMoveTiles.add(this);
		  return possibleMoveTiles;
	  }
	  
      //Pick each connection piece that connect to this Tile 
	  for(i=0;i<getConnections().size();i++){
			 Connection nextConnection = getConnection(i);
			 //Get all neighborTile
			 for(int j=0;j<2;j++){			
				 neighborTile = nextConnection.getTile(j);
				  //If the neighborTile is not the Tile itself and is not the previous Tile,then, recursively call getNextMove in order to get possible moves.
				  if((!neighborTile.equals(this))&&(!neighborTile.equals(previousTile))){
					   possibleMoveTiles.addAll(neighborTile.getNextMoves(moveLeft-1,this));
			      }			
			 }			  
		  }
		  return possibleMoveTiles;  
	  }	  
  
  //------------------------
  
  // line 32 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = 4444444444444444444L ;

  
}