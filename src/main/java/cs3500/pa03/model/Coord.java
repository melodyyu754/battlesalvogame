package cs3500.pa03.model;

/**
 * A class representing a coordinate on the BattleSalvo board, includes
 * the x and y values, as well as if the coordinate has been shot at and if there
 * is a ship in that location.
 */
public class Coord {
  private int xvalue;
  private int yvalue;
  private boolean hit;
  private boolean ship;

  /**
   * Constructor for a Coord, x and y start from the top left corner.
   *
   * @param xvalue the x value of this Coord
   * @param yvalue the y value for this Coord
   */
  public Coord(int xvalue, int yvalue) {
    this.xvalue = xvalue;
    this.yvalue = yvalue;
    this.hit = false;
    this.ship = false;
  }

  /**
   * Returns the x value for this coordinate
   *
   * @return the x value for this coordinate
   */
  public int getX() {
    return this.xvalue;
  }

  /**
   * Returns the y value for this coordinate
   *
   * @return the y value for this coordinate
   */
  public int getY() {
    return this.yvalue;
  }

  /**
   * Returns whether the coordinate has been shot at.
   *
   * @return true if it has been shot at, false if not.
   */
  public boolean isHit() {
    return this.hit;
  }

  /**
   * Returns whether the coordinate has a ship.
   *
   * @return true if there is a ship, false if there is not.
   */
  public boolean isShip() {
    return this.ship;
  }

  /**
   * Sets ship field to the given boolean
   *
   * @param b boolean representing whether a ship is there.
   */
  public void setShip(boolean b) {
    this.ship = b;
  }

  /**
   * Sets hit field to the given boolean
   *
   * @param b boolean representing whether a Coord is hit.
   */
  public void setHit(boolean b) {
    this.hit = b;
  }

  /**
   * Determines if a given object is equal to this Coord by comparing only x and y values.
   *
   * @param o the object being compared with this Coord
   * @return true if x and y values match this Coord, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Coord)) {
      return false;
    }
    Coord that = (Coord) o;
    return this.xvalue == (that.xvalue) && this.yvalue == (that.yvalue);
  }

  /**
   * Returns a hash code value for this Coord
   *
   * @return the hash code value for the Coord
   */
  @Override
  public int hashCode() {
    return this.xvalue * 1123 + this.yvalue * 30;
  }

}
