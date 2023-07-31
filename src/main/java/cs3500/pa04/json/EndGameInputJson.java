package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * A Json representing a message from the server that signifies the end
 * of a game.
 *
 * @param result the result of the game - WIN, DRAW, or LOSE
 * @param reason a description of why the game ended
 */
public record EndGameInputJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}