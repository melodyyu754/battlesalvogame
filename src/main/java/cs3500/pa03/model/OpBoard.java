package cs3500.pa03.model;

/**
 * Represents a players view of their opponent's board
 */
public class OpBoard extends AbstractBoard {


  /**
   * Constructor for server game, board isn't setup after aiPlayer is instantiated.
   */
  public OpBoard() {
    super();
  }

  /**
   * Adds no additional fields or methods to what is provided in the abstract board.
   *
   * @param width  the board's width
   * @param height the board's height
   */
  public OpBoard(int width, int height) {
    super(width, height);
  }

  /**
   * Returns the number of coordinates on this opponent's board that have not
   * been hit yet.
   *
   * @return an integer representing the number of "unhit" spaces on this board
   */
  public int numUnhitCoords() {
    int numUnhit = 0;

    for (Coord[] row : this.board) {
      for (Coord c : row) {
        if (!c.isHit()) {
          numUnhit++;
        }
      }
    }

    return numUnhit;
  }
}
