/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;

// line 71 "../../../../../TileOPersistence.ump"
// line 256 "../../../../../TileO (updated Feb10).ump"
public abstract class RollDieActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RollDieActionCard(String aInstructions, Deck aDeck)
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

  // line 264 "../../../../../TileO (updated Feb10).ump"
   public void play(){
    Game currentGame = getDeck().getGame();
	  currentGame.rollDie();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 74 TileOPersistence.ump
  private static final long serialVersionUID = 2020202020202020202L ;
// line 259 ../../../../../TileO (updated Feb10).ump
  @Override
	public Game.Mode getActionCardGameMode() {return Game.Mode.GAME_ROLLDIEACTIONCARD;};

  
}