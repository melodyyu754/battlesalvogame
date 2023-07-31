package cs3500.pa03.view;

import cs3500.pa03.model.Coord;

/**
 * View interface that handles the output for the user.
 */
public interface View {
  /**
   * Prints a string message to the user
   *
   * @param message string message to be outputted to the user
   */
  void print(String message);

  /**
   * Displays two boards (2d arrays) to the user
   *
   * @param myBoard the first board to be displayed to the user
   * @param opBoard the second board to be displayed to the user
   */
  void displayBoards(Coord[][] myBoard, Coord[][] opBoard);
}