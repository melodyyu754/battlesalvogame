package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Json that represents our response to the server when joining a game.
 *
 * @param gitUser  the Github username we will use to play this round.
 * @param gameType the type of game - SINGLE or MULTI
 */
public record JoinResponseJson(
    @JsonProperty("name") String gitUser,
    @JsonProperty("game-type") GameType gameType) {
}