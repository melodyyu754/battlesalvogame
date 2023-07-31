package cs3500.pa03.model;

/**
 * Enumeration for GameResult, containing the three options for the result of a game.
 */
public enum GameResult {
  /**
   * WIN: if the user wins a game by sinking all the opponent's battleships.
   */
  WIN,
  /**
   * LOSE: if the user loses a game by having all of their battleships sunk.
   */
  LOSE,
  /**
   * DRAW: if the user draws a game by sinking all the opponent's battleships at the same time
   * their battleships are all sunk.
   */
  DRAW;
}
