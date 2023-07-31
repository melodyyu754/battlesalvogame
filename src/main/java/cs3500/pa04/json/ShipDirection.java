package cs3500.pa04.json;

/**
 * Represents the possible ship directions.
 */
public enum ShipDirection {
  /**
   * Vertical ship, where the coordinate's y value changes as you go from coord to coord.
   */
  VERTICAL,
  /**
   * Horizontal ship, where the coordinate's x value changes as you go from coord to coord.
   */
  HORIZONTAL;

  @Override
  public String toString() {
    if (this == VERTICAL) {
      return "VERTICAL";
    } else {
      return "HORIZONTAL";
    }
  }
}
