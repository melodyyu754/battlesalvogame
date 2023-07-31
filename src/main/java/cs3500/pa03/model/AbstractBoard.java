package cs3500.pa03.model;

/**
 * Abstract class representing a board, which has at least a 2d array of Coords.
 */
public abstract class AbstractBoard {
  /**
   * Each class that extends AbstractBoard has access to its 2d Array of Coords.
   */
  protected Coord[][] board;

  /**
   * Constructor for a board, which takes in a height and a width and
   * initializes the board with setup.
   *
   * @param width  the width of the board.
   * @param height the height of the board.
   */
  public AbstractBoard(int width, int height) {
    setupBoard(width, height);
  }

  /**
   * Constructor for server game, board isn't setup after aiPlayer is instantiated.
   */
  public AbstractBoard() {
    // nothing
  }

  /**
   * Sets up the board by creating the array and filling it with new coordinates.
   *
   * @param width  the width of the board
   * @param height the height of the board
   */
  public void setupBoard(int width, int height) {
    this.board = new Coord[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        board[i][j] = new Coord(j, i);
      }
    }
  }

  /**
   * Gets the 2d array of Coords belonging to the board.
   *
   * @return a 2d array of Coords, the actual board.
   */
  public Coord[][] getBoard() {
    return this.board;
  }
}
