/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;

// line 114 "../../../../../TileOPersistence.ump"
// line 369 "../../../../../TileO (updated Feb10).ump"
public abstract class RevealActionTilesActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RevealActionTilesActionCard(String aInstructions, Deck aDeck)
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

  // line 373 "../../../../../TileO (updated Feb10).ump"
   public Game.Mode getActionCardGameMore(){
    return Game.Mode.GAME_REVEALACTIONTILESACTIONCARD;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 117 TileOPersistence.ump
  private static final long serialVersionUID = 1098766543012347859L ;

  
}