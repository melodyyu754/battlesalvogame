package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import java.io.IOException;

/**
 * BattleSalvoView is a view class that implements the View controller and provides methods
 * that print a message and 2d arrays of Coordinates.
 */
public class BattleSalvoView implements View {
  private final Appendable output;

  /**
   * Constructor for BattleSalvoView, takes in an output that can be appended to.
   *
   * @param output the output to be appended to.
   */
  public BattleSalvoView(Appendable output) {
    this.output = output;
  }

  /**
   * Prints two boards using the print(Coord[][] board) private method, along with strings
   * labelling them.
   *
   * @param myboard the first board printed, representing "this" user's own board.
   * @param opBoard the second board printed, representing "this" user's view of the opponent's
   *                board.
   */
  @Override
  public void displayBoards(Coord[][] myboard, Coord[][] opBoard) {
    print("Your board:");
    print(myboard);
    print("Opponent board:");
    print(opBoard);
  }

  /**
   * Appends a message to the Appendable output along with a line separator.
   *
   * @param message the message to be appended.
   */
  @Override
  public void print(String message) {
    try {
      output.append(message).append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException("Failed to print message.");
    }
  }

  /**
   * Prints a 2d array of Coords. Uses the coordToString(Coord) helper method to determine what
   * to print for each Coord.
   *
   * @param board a 2d array of Coords that could represent a user's board or their opponent's
   *              board.
   */
  private void print(Coord[][] board) {
    for (Coord[] row : board) {
      for (Coord coord : row) {
        try {
          output.append(coordToString(coord)).append("  ");
        } catch (IOException e) {
          throw new RuntimeException("Unexpected error; failed to print");
        }
      }
      try {
        output.append(System.lineSeparator());
      } catch (IOException e) {
        throw new RuntimeException("Failed to separate lines.");
      }
    }
  }

  /**
   * Returns a string representation of a Coord based on whether the coordinate has been hit and
   * whether the coordinate has a ship on it
   *
   * @param coord a coordinate that has an x value, y value, boolean hit, and boolean ship.
   * @return "H" if the Coord is hit and contains a ship, "M" if the Coord is hit and does not
   *     contain a ship, "S" if the Coord is not hit but does contain a ship, and "." if the Coord
   *     is neither hit nor contains a ship.
   */
  private static String coordToString(Coord coord) {
    if (coord.isHit() && coord.isShip()) {
      return "H";
    } else if (coord.isHit() && !coord.isShip()) {
      return "M";
    } else if (!coord.isHit() && coord.isShip()) {
      return "S";
    } else {
      return ".";
    }
  }
}