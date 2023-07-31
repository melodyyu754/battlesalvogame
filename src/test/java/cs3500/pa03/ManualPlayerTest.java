package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.ShipType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the ManualPlayer class
 */
public class ManualPlayerTest {
  ManualPlayer manualPlayer;
  HashMap<ShipType, Integer> specs = new HashMap<>();
  ArrayList<Coord> salvo;
  MyBoard myBoard;
  OpBoard opBoard;

  /**
   * Sets up HashMap of specs and instantiates a ManualPlayer and one of its salvos. Sets the
   * "currentShots" of the manualPlayer to the salvo via addShots.
   */
  @BeforeEach
  public void setupInitialData() {
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
    salvo = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(0, 1), new Coord(0, 2), new Coord(0, 3)));
    myBoard = new MyBoard(6, 8);
    opBoard = new OpBoard(6, 8);
    manualPlayer = new ManualPlayer(6, 8, specs, salvo, myBoard, opBoard);
  }

  /**
   * Tests the name() method for ManualPlayer.
   */
  @Test
  public void nameTest() {
    assertEquals("ManualPlayer", manualPlayer.name());
  }

  /**
   * Tests the takeShots method for ManualPlayer, that OpBoard is mutated and takeShots returns
   * the salvo inputted.
   */
  @Test
  public void takeShotsTest() {
    assertEquals(salvo, manualPlayer.takeShots());

    for (Coord shot : salvo) {
      assertTrue(opBoard.getBoard()[shot.getY()][shot.getX()].isHit());
    }
  }
}