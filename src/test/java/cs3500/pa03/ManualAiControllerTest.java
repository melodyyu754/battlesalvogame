package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.ManualAiController;
import java.io.StringReader;
import org.junit.jupiter.api.Test;

/**
 * Test class for ManualAiController
 */
public class ManualAiControllerTest {

  /**
   * Tests the initGame method, by entering different types of invalid data followed by valid data,
   * and checking the output.
   */
  @Test
  public void testInitGame() {
    Readable input = new StringReader("16 6\n" + "5 8\n" + "6 16\n" + "8 5\n" + "5 8\n"
        + "6 8 3\n" + "6 8\n" + "10 1 1 1\n"
        + "1 1 1\n" + "0 1 1 1\n" + "1 0 1 1\n" + "1 1 0 1\n" + "1 1 1 0\n" + "1 1 1 1\n");
    Appendable output = new StringBuilder();
    ManualAiController controller = new ManualAiController(input, output);

    controller.initGame();
    String outputString = output.toString();
    String[] lines = outputString.split("\n");

    assertEquals("Hello! Welcome to the OOD BattleSalvo Game! ", lines[0]);
    assertEquals("Please enter a valid width and height below, separated by a whitespace: ",
        lines[1]);
    assertEquals("Uh oh! You've entered invalid dimensions. "
        + "Please remember that the width and ", lines[2]);
    assertEquals("height of the game must be in the range (6, 15), inclusive. Try again!",
        lines[3]);
    assertEquals("Uh oh! You've entered invalid dimensions. "
        + "Please remember that the width and ", lines[4]);
    assertEquals("height of the game must be in the range (6, 15), inclusive. Try again!",
        lines[5]);
    assertEquals("Uh oh! You've entered invalid dimensions. "
        + "Please remember that the width and ", lines[6]);
    assertEquals("height of the game must be in the range (6, 15), inclusive. Try again!",
        lines[7]);
    assertEquals("Uh oh! You've entered invalid dimensions. "
        + "Please remember that the width and ", lines[8]);
    assertEquals("height of the game must be in the range (6, 15), inclusive. Try again!",
        lines[9]);
    assertEquals("Uh oh! You've entered invalid dimensions. "
        + "Please remember that the width and ", lines[10]);
    assertEquals("height of the game must be in the range (6, 15), inclusive. Try again!",
        lines[11]);
    assertEquals("Uh oh! You've entered invalid dimensions. "
        + "Please remember that the width and ", lines[12]);
    assertEquals("height of the game must be in the range (6, 15), inclusive. Try again!",
        lines[13]);
    assertEquals("Please enter your fleet in the order [Carrier, Battleship, "
            + "Destroyer, Submarine]. Remember, your fleet may not exceed size 6.",
        lines[14]);
    assertEquals("Invalid input. Please enter valid inputs for number of ships.",
        lines[15]);
    assertEquals("Invalid input. Please enter valid inputs for number of ships.",
        lines[16]);
    assertEquals("Invalid input. Please enter valid inputs for number of ships.",
        lines[17]);
    assertEquals("Invalid input. Please enter valid inputs for number of ships.",
        lines[18]);
    assertEquals("Invalid input. Please enter valid inputs for number of ships.",
        lines[19]);
    assertEquals("Invalid input. Please enter valid inputs for number of ships.",
        lines[20]);
    assertEquals("Your board:",
        lines[21]);
  }

  /**
   * Tests the runGame() method by passing in valid and invalid input and checking the output.
   * Inputs all the shots so that a NoSuchElementFound exception is not thrown.
   */
  @Test
  public void testRunGame() {
    Readable input = new StringReader("5 8\n" + "6 8 3\n" + "6 8\n" + "10 1 1 1\n" + "1 1 1 0\n"
        + "1 1 1\n" + "1 1 1 1\n"
        + "bad input\n" + "0 0\n" + "0 1\n" + "0 2\n" + "0 3\n"
        + "10 4\n" + "0 4\n" + "0 5\n"
        + "0 6\n" + "0 7\n" + "1 0\n" + "1 1\n" + "1 2\n" + "1 3\n" + "1 4\n" + "1 5\n"
        + "1 6\n" + "1 7\n" + "2 0\n" + "2 1\n" + "2 2\n" + "2 3\n" + "2 4\n" + "2 5\n"
        + "2 6\n" + "2 7\n" + "3 0\n" + "3 1\n" + "3 2\n" + "3 3\n" + "3 4\n" + "3 5\n"
        + "3 6\n" + "3 7\n" + "4 0\n" + "4 1\n" + "4 2\n" + "4 3\n" + "4 4\n" + "4 5\n"
        + "4 6\n" + "4 7\n" + "5 0\n" + "5 1\n" + "5 2\n" + "5 3\n" + "5 4\n" + "5 5\n"
        + "5 6\n" + "5 7\n" + "5 7\n" + "5 7\n");
    Appendable output = new StringBuilder();
    ManualAiController controller = new ManualAiController(input, output);
    controller.initGame();
    controller.runGame();
    String outputString = output.toString();
    String[] lines = outputString.split("\n");

    assertEquals("Please enter 4 valid shots.", lines[28]);
    assertEquals("For input string: \"bad\"", lines[29]);
    assertEquals("Please enter 4 valid shots.", lines[30]);
  }
}
