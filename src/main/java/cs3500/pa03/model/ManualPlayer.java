package cs3500.pa03.model;

import cs3500.pa03.model.random.MyRandomInterface;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a player that takes shots from the console.
 */
public class ManualPlayer extends AbstractPlayer {
  private final String name;
  private ArrayList<Coord> currentSalvo;

  /**
   * Constructor for the manual player used in a real game.
   *
   * @param width  The width of the game board.
   * @param height The height of the game board.
   * @param specs  The hashmap representing how many there are of each ship.
   * @param currentSalvo the current salvo of the ManualPlayer during any given round.
   * @param myBoard the manual player's view of their own board
   * @param opBoard the manual player's view of their opponent's board
   */
  public ManualPlayer(int width, int height, HashMap<ShipType, Integer> specs,
                      ArrayList<Coord> currentSalvo, MyBoard myBoard, OpBoard opBoard) {
    super(width, height, specs, myBoard, opBoard);
    this.name = "ManualPlayer";
    this.currentSalvo = currentSalvo;
  }

  /**
   * Constructor for the manual player used in a test game (used with a random seed).
   *
   * @param width  The width of the game board.
   * @param height The height of the game board.
   * @param specs  The hashmap representing how many there are of each ship.
   * @param currentSalvo the current salvo of the ManualPlayer during any given round.
   * @param myBoard the manual player's view of their own board
   * @param opBoard the manual player's view of their opponent's board
   * @param random Passed in random to seed the game so it acts the same way everytime.
   */
  public ManualPlayer(int width, int height, HashMap<ShipType, Integer> specs,
                      ArrayList<Coord> currentSalvo, MyBoard myBoard, OpBoard opBoard,
                      MyRandomInterface random) {
    super(width, height, specs, myBoard, opBoard, random);
    this.name = "ManualPlayer";
    this.currentSalvo = currentSalvo;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Returns this player's shots on the opponent's board. Also sets the taken shots on this
   * player's opBoard to hit.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public ArrayList<Coord> takeShots() {
    for (Coord shot : currentSalvo) {
      opBoard.getBoard()[shot.getY()][shot.getX()].setHit(true);
    }
    return currentSalvo;
  }

  /**
   * Doesn't do anything.
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    // No functionality needed.
  }
}