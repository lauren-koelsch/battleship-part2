package cs3500.pa03.model;

import java.util.List;
import json.ShipJson;

/**
 * Represents a ship in the game of BattleSalvo that has a type representing the type of ship it
 * is, a list of coordinates representing its location, and a boolean representing its status of
 * sunk or unsunk
 */
public class Ship {

  private ShipType type;

  private List<Coord> coords;

  private boolean isSunk;

  Ship(ShipType type, List<Coord> coords, boolean isSunk) {
    this.type = type;
    this.coords = coords;
    this.isSunk = isSunk;
  }

  public ShipType getType() {
    return type;
  }

  public List<Coord> getCoords() {
    return coords;
  }

  public boolean getIsSunk() {
    return isSunk;
  }

  public void setIsSunk(boolean bool) {
    isSunk = bool;
  }

  /**
   * Checks if all coordinates of a ship have been hit and if so returns true and changes the ships
   * sunk status to true
   *
   * @return returns a boolean representing whether the ship is sunk or not
   */
  public boolean checkIsSunk() {
    List<Coord> list = getCoords();
    boolean checkIsSunk = false;

    for (Coord coord : list) {
      checkIsSunk = coord.getIsHit();
      if (!checkIsSunk) {
        break;
      }
    }
    setIsSunk(checkIsSunk);
    return checkIsSunk;
  }

  public ShipJson convert() {
    return new ShipJson(coords.get(0).convert(), type.getSize(), Direction.HORIZONTAL);
  }
}
