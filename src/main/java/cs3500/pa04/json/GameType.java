package cs3500.pa04.json;

/**
 * Represents the possible game types, where a single player game
 * is played against the Server AI and a multiplayer game is played
 * against another student's AI.
 */
public enum GameType {
  /**
   * Used for single player gametype, when aiPlayers play the server.
   */
  SINGLE,
  /**
   * Used for multiplayer gametype, when AiPlayers play other players on the server
   */
  MULTI }
