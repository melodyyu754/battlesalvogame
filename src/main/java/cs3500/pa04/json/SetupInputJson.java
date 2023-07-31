package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * A Json that represents the input given from the Server to setup a game.
 *
 * @param height the height of the game board
 * @param width the width of the game board
 * @param specs a Hashmap of ShipType to integer signifying number of boats for this game
 */
public record SetupInputJson(
                              @JsonProperty("height") int height,
                              @JsonProperty("width") int width,
                              @JsonProperty("fleet-spec") Map<ShipType, Integer> specs) {
}
