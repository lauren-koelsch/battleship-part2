package cs3500.pa03.model;

/**
 * Represents each type of ship available in the game BattleSalvo and their corresponding lengths
 */
public enum ShipType {

  CARRIER(6),

  BATTLESHIP(5),

  DESTROYER(4),

  SUBMARINE(3);

  private final int size;

  ShipType(int size) {
    this.size = size;
  }

  public int getSize() {
    return size;
  }
}
