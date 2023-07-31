package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cs3500.pa03.model.Coord;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.View;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for BattleSalvoView
 */
public class BattleSalvoViewTest {
  StringBuilder writer;
  View view;
  Coord[][] board1;
  Coord[][] board2;
  Appendable mockOutput;

  /**
   * Instantiates a stringbuilder and a view with that stringbuilder as a parameter. Also
   * initializes the boards (two 6x8 boards) and initializes a mockOutput.
   */
  @BeforeEach
  public void setup() {
    writer = new StringBuilder();
    view = new BattleSalvoView(writer);

    board1 = new Coord[8][6];
    board2 = new Coord[8][6];

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 6; j++) {
        board1[i][j] = new Coord(i, j);
        board2[i][j] = new Coord(i, j);
      }
    }

    // initializes the mockOutput
    mockOutput = new Appendable() {
      @Override
      public Appendable append(CharSequence csq) throws IOException {
        throw new IOException("Mock cannot write.");
      }

      @Override
      public Appendable append(CharSequence csq, int start, int end) throws IOException {
        throw new IOException("Mock cannot write.");
      }

      @Override
      public Appendable append(char c) throws IOException {
        throw new IOException("Mock cannot write.");
      }
    };
  }

  /**
   * Tests the print(String message) method by checking the output of the method.
   */
  @Test
  public void testPrintMessage() {
    view.print("Hello, world.");
    String output = writer.toString();

    assertEquals("Hello, world.\n", output);
  }

  /**
   * Tests the handling of the IOException when printing.
   */
  @Test
  public void testPrintExceptionHandling() {
    View view = new BattleSalvoView(mockOutput);
    assertThrows(RuntimeException.class, () -> view.print("Test message"));
    assertThrows(RuntimeException.class, () -> view.displayBoards(board1, board2), "");
  }

  /**
   * Tests the print(Coord[][] myBoard, Coord[][] opBoard) method by checking the output
   * of the method.
   */
  @Test
  public void testDisplayBoardsBlank() {
    view.displayBoards(board1, board2);
    String output = writer.toString();

    assertEquals("Your board:\n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + "Opponent board:\n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n", output);
  }

  /**
   * Mutates the board to have varying types of Coords, then tests the print(Coord[][] myBoard,
   * Coord[][] opBoard) method by checking the output of the method.
   */
  @Test
  public void testDisplayBoardsChanged() {
    board1[0][0].setShip(true);
    board1[1][0].setShip(true);
    board1[1][0].setHit(true);
    board1[2][0].setHit(true);

    view.displayBoards(board1, board2);
    String changedBoards = writer.toString();

    assertEquals("Your board:\n"
        + "S  .  .  .  .  .  \n"
        + "H  .  .  .  .  .  \n"
        + "M  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + "Opponent board:\n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n"
        + ".  .  .  .  .  .  \n", changedBoards);
  }
}
