package cs3500.pa03.model;

import cs3500.pa04.json.CoordAdapter;
import cs3500.pa04.json.ShipDirection;
import java.util.ArrayList;

/**
 * A ship is the game piece placed on the game boards. It consists of an ArrayList of Coords,
 * the Coords in which it occupies.
 */
public class Ship {
  private ArrayList<Coord> location;

  /**
   * The constructor for a ship
   *
   * @param location the ArrayList of Coords this ship occupies
   */
  public Ship(ArrayList<Coord> location) {
    this.location = location;
  }

  /**
   * Determines if all of a ship's coordinates are hit
   *
   * @return true if all coords in location are hit, false otherwise.
   */
  public boolean isSunk() {
    for (Coord c : location) {
      if (!c.isHit()) {
        return false;
      }
    }

    return true;
  }

  /**
   * Returns the positions of this ship
   *
   * @return the ArrayList of Coords this ship occupies.
   */
  public ArrayList<Coord> getLocation() {
    return this.location;
  }

  /**
   * Returns the starting (top and leftmost) CoordAdapter of this ship to help
   * with translating the Ship to a ShipAdapter.
   *
   * @return a CoordAdapter representing the origin coordinate of this ship
   */
  public CoordAdapter start() {
    Coord origin = this.getLocation().get(0);
    return new CoordAdapter(origin.getX(), origin.getY());
  }

  /**
   * Returns the orientation of this ship to help with translating it
   * to a ShipAdapter.
   *
   * @return the orientation of this ship - either VERTICAL or HORIZONTAL
   */
  public ShipDirection direction() {
    Coord firstCoord = location.get(0);
    Coord secondCoord = location.get(1);

    if (firstCoord.getX() == secondCoord.getX()) {
      return ShipDirection.VERTICAL;
    } else {
      return ShipDirection.HORIZONTAL;
    }
  }
}
