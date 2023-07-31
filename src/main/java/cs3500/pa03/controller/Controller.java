package cs3500.pa03.controller;

/**
 * Controller interface
 */
public interface Controller {
  /**
   * Initializes the game: asks the user for initial info and instantiates players.
   */
  void initGame();

  /**
   * Runs the game: Takes in "shots" from the user and delegates to the model while the game
   * is not over.
   */
  void runGame();
}
