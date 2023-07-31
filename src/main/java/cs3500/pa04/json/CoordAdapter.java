package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Json that represents an adapted Coord that can be accepted by
 * the server, with x and y position.
 *
 * @param x the coordinate's x value
 * @param y the coordinate's y value
 */
public record CoordAdapter(int x, int y) {
  /**
   * Creates a new Json CoordAdapter with x and y fields
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  @JsonCreator
  public CoordAdapter(@JsonProperty("x") int x,
                      @JsonProperty("y") int y) {
    this.x = x;
    this.y = y;
  }
}
