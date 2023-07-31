package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualAiModel;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.random.FakeServerRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for the ManualAiModel
 */
public class ManualAiModelTest {
  AiPlayer aiPlayer;
  MyBoard aiMyBoard;
  OpBoard aiOpBoard;

  ManualPlayer manualPlayer;
  MyBoard manualMyBoard;
  OpBoard manualOpBoard;

  HashMap<ShipType, Integer> specs;
  ManualAiModel model;
  ArrayList<Coord> manualSalvo;

  /**
   * Sets up HashMap of specs and instantiates players and the model, as well as the boards and
   * two salvos.
   */
  @BeforeEach
  public void setupInitialData() {
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    aiMyBoard = new MyBoard(6, 8);
    aiOpBoard = new OpBoard(6, 8);
    aiPlayer = new AiPlayer(6, 8, specs, aiMyBoard, aiOpBoard, new FakeServerRandom());

    manualSalvo = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3)));
    manualMyBoard = new MyBoard(6, 8);
    manualOpBoard = new OpBoard(6, 8);
    manualPlayer = new ManualPlayer(6, 8, specs, manualSalvo, manualMyBoard,
        manualOpBoard, new FakeServerRandom());

    model = new ManualAiModel(manualPlayer, aiPlayer, manualMyBoard, aiMyBoard);
  }

  /**
   * Tests the playRound method by checking if the shots entered by the manualPlayer are now
   * "hit" on the board.
   */
  @Test
  public void playRoundTest() {
    for (Coord shot : manualSalvo) {
      assertFalse(aiMyBoard.getBoard()[shot.getY()][shot.getX()].isHit());
      assertFalse(manualOpBoard.getBoard()[shot.getY()][shot.getX()].isHit());
    }

    model.playRound();

    for (Coord shot : manualSalvo) {
      assertTrue(aiMyBoard.getBoard()[shot.getY()][shot.getX()].isHit());
      assertTrue(manualOpBoard.getBoard()[shot.getY()][shot.getX()].isHit());
    }
  }

  /**
   * Tests the checkShot method with valid and invalid input.
   */
  @Test
  public void checkShotTest() {
    assertEquals(new Coord(0, 0), model.checkShot(new String[] {"0", "0"}, 6, 8));

    assertThrows(IllegalArgumentException.class, () ->
        model.checkShot(new String[] {"6", "8"}, 6, 8));
    assertThrows(IllegalArgumentException.class, () ->
        model.checkShot(new String[] {"-1", "8"}, 6, 8));
    assertThrows(IllegalArgumentException.class, () ->
        model.checkShot(new String[] {"3", "-1"}, 6, 8));
    assertThrows(IllegalArgumentException.class, () ->
        model.checkShot(new String[] {"3", "9"}, 6, 8));
  }

  /**
   * Tests the gameOver() method by having a manual player shoot all the Coords of a board,
   * and checking if the game was over.
   */
  @Test
  public void gameOverTest() {
    assertEquals(4, manualMyBoard.getShotsLeft());
    assertEquals(4, aiMyBoard.getShotsLeft());
    assertFalse(model.gameOver());

    manualSalvo.clear();
    for (int i = 0; i < manualOpBoard.getBoard().length; i++) {
      for (int j = 0; j < manualOpBoard.getBoard()[0].length; j++) {
        manualSalvo.add(new Coord(j, i));
      }
    }
    model.playRound();

    assertTrue(model.gameOver());
  }

  /**
   * Tests the whoWon() method and asserts that the manual player wins when the aiPlayer runs
   * out of shots.
   */
  @Test
  public void whoWonTestAi() {
    assertEquals(4, manualMyBoard.getShotsLeft());
    assertEquals(4, aiMyBoard.getShotsLeft());
    assertFalse(model.gameOver());

    ArrayList<Ship> empty = new ArrayList<>();
    manualMyBoard.setShips(empty);
    assertEquals(0, manualMyBoard.getShotsLeft());
    assertEquals(4, aiMyBoard.getShotsLeft());

    model.playRound();

    assertTrue(model.gameOver());
    assertEquals(-1, model.whoWon());
  }

  /**
   * Tests the whoWon() method and asserts that the ai player wins when the manualPlayer runs
   * out of shots.
   */
  @Test
  public void whoWonTestManual() {
    assertEquals(4, manualMyBoard.getShotsLeft());
    assertEquals(4, aiMyBoard.getShotsLeft());
    assertFalse(model.gameOver());

    ArrayList<Ship> empty = new ArrayList<>();
    aiMyBoard.setShips(empty);
    assertEquals(0, aiMyBoard.getShotsLeft());
    assertEquals(4, manualMyBoard.getShotsLeft());

    model.playRound();

    assertTrue(model.gameOver());
    assertEquals(1, model.whoWon());
  }

  /**
   * Tests the whoWon() method and asserts that the manual player wins when both players run out
   * of shots
   */
  @Test
  public void whoWonTestDraw() {
    assertEquals(4, manualMyBoard.getShotsLeft());
    assertEquals(4, aiMyBoard.getShotsLeft());
    assertFalse(model.gameOver());

    ArrayList<Ship> empty = new ArrayList<>();
    aiMyBoard.setShips(empty);
    manualMyBoard.setShips(empty);
    assertEquals(0, aiMyBoard.getShotsLeft());
    assertEquals(0, manualMyBoard.getShotsLeft());

    model.playRound();

    assertTrue(model.gameOver());
    assertEquals(0, model.whoWon());
  }
}
