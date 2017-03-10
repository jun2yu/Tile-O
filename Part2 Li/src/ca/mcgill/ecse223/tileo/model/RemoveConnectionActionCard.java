/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;

import ca.mcgill.ecse223.tileo.controller.InvalidInputException;

// line 83 "../../../../../TileOPersistence.ump"
// line 234 "../../../../../TileO (updated Feb10).ump"
public class RemoveConnectionActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RemoveConnectionActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 238 "../../../../../TileO (updated Feb10).ump"
   public void play(Connection connection) throws InvalidInputException{
    try{
		  connection.delete();
	  }
	  catch (RuntimeException e) {
		  throw new InvalidInputException(e.getMessage());
	  }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 86 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = 4040404040404040404L ;
// line 246 ../../../../../TileO (updated Feb10).ump
  @Override
	public Game.Mode getActionCardGameMode() {return Game.Mode.GAME_REMOVECONNECTIONACTIONCARD;};

  
}