/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

// line 157 "../../../../../TileO2.ump"
public class Location
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Location Attributes
  private int xLocation;
  private int yLocation;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Location(int aXLocation, int aYLocation)
  {
    xLocation = aXLocation;
    yLocation = aYLocation;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setXLocation(int aXLocation)
  {
    boolean wasSet = false;
    xLocation = aXLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setYLocation(int aYLocation)
  {
    boolean wasSet = false;
    yLocation = aYLocation;
    wasSet = true;
    return wasSet;
  }

  /**
   * helper class to check the location
   */
  public int getXLocation()
  {
    return xLocation;
  }

  public int getYLocation()
  {
    return yLocation;
  }

  public void delete()
  {}


  public String toString()
  {
    String outputString = "";
    return super.toString() + "["+
            "xLocation" + ":" + getXLocation()+ "," +
            "yLocation" + ":" + getYLocation()+ "]"
     + outputString;
  }
}