package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * Represents a players view of their own board
 */
public class MyBoard extends AbstractBoard {
  private ArrayList<Ship> ships;

  /**
   * Constructor for MyBoard that takes in height, width, and a list of the player's own ships.
   *
   * @param width  the width of the board
   * @param height the height of the board
   */
  public MyBoard(int width, int height) {
    super(width, height);
  }

  /**
   * Constructor for server game, board isn't setup after aiPlayer is instantiated.
   */
  public MyBoard() {
    super();
  }

  /**
   * Determines the number of shots this player has left based on the number of ships that aren't
   * sunk.
   *
   * @return the number of shots this player has left
   */
  public int getShotsLeft() {
    int shotsLeft = 0;
    for (Ship ship : ships) {
      if (!ship.isSunk()) {
        shotsLeft++;
        //System.out.println(ship.getLocation().toString());
      }
    }
    return shotsLeft;
  }

  /**
   * Sets a player's ships equal to a given list of ships
   *
   * @param setupShips the list of ships that player has
   */
  public void setShips(ArrayList<Ship> setupShips) {
    this.ships = setupShips;
  }
}