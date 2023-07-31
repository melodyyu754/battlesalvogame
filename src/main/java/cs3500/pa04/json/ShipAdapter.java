package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import java.util.ArrayList;

/**
 * A Json that represents an adapted Ship that can be accepted by
 * * the server, with origin coordinate, length, and direction.
 *
 * @param coord     the origin coordinate of this ship (top and leftmost)
 * @param length    the length of this ship
 * @param direction the direction this ship is oriented
 */
public record ShipAdapter(CoordAdapter coord, int length, ShipDirection direction) {
  /**
   * Constructor for a ShipAdapter.
   *
   * @param coord the origin (top leftmost coordinate)
   * @param length the length of this ship
   * @param direction the orientation of this ship (VERTICAL or HORIZONTAL)
   */
  @JsonCreator
  public ShipAdapter(@JsonProperty("coord") CoordAdapter coord,
                     @JsonProperty("length") int length,
                     @JsonProperty("direction") ShipDirection direction) {
    this.coord = coord;
    this.length = length;
    this.direction = direction;
  }
}
