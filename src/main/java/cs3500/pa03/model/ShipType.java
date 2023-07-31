package cs3500.pa03.model;

/**
 * Enumeration for ShipType, with the four options for the type of ship. Each ship has
 * a size.
 */
public enum ShipType {
  /**
   * SUBMARINE: a ship with size 3.
   */
  SUBMARINE(3),
  /**
   * DESTROYER: a ship with size 4.
   */
  DESTROYER(4),
  /**
   * BATTLESHIP: a ship with size 5.
   */
  BATTLESHIP(5),
  /**
   * CARRIER: a ship with size 6.
   */
  CARRIER(6);

  /**
   * The number of coordinates a ship occupies.
   */
  private final int size;

  /**
   * A constructor for ShipType that initializes its size to the inputted size.
   *
   * @param size the size of the ship.
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Gets the size of the ship.
   *
   * @return the size of the ship.
   */
  public int getSize() {
    return this.size;
  }
}