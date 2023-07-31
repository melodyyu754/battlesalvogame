package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.random.FakeServerRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for AiPlayer, includes most of APlayer testing
 */
public class AiPlayerTest {
  AiPlayer aiPlayer;
  HashMap<ShipType, Integer> specs;
  MyBoard myBoard;
  OpBoard opBoard;
  ArrayList<Coord> damagedCoords;

  /**
   * Sets up HashMap of specs and instantiates an AiPlayer.
   */
  @BeforeEach
  public void setupInitialData() {
    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);

    myBoard = new MyBoard(6, 8);
    opBoard = new OpBoard(6, 8);
    aiPlayer = new AiPlayer(6, 8, specs, myBoard, opBoard, new FakeServerRandom());
    damagedCoords = new ArrayList<>();
  }

  /**
   * Tests the name() method for AiPlayer.
   */
  @Test
  public void nameTest() {
    assertEquals("AiPlayer", aiPlayer.name());
  }

  /**
   * Tests the setup() method and that it returns the desired number of random coordinates.
   */
  @Test
  public void setupTest() {
    int numberOfShipCoords = 0;
    for (int i = 0; i < myBoard.getBoard().length; i++) {
      for (int j = 0; j < myBoard.getBoard()[0].length; j++) {
        if (myBoard.getBoard()[i][j].isShip()) {
          numberOfShipCoords++;
        }
      }
    }
    assertEquals(18, numberOfShipCoords);
  }

  /**
   * Tests the successful hits method and that for the successful hits, mutates opBoard to
   * true isShip values.
   */
  @Test
  public void successfulHitsTest() {
    assertFalse(opBoard.getBoard()[0][0].isShip());
    assertFalse(opBoard.getBoard()[0][1].isShip());
    assertFalse(opBoard.getBoard()[0][2].isShip());

    ArrayList<Coord> shotsThatHitOpponentShips = new ArrayList<>(Arrays.asList(new Coord(0, 0),
        new Coord(1, 0), new Coord(2, 0)));
    aiPlayer.successfulHits(shotsThatHitOpponentShips);

    assertTrue(opBoard.getBoard()[0][0].isShip());
    assertTrue(opBoard.getBoard()[0][1].isShip());
    assertTrue(opBoard.getBoard()[0][2].isShip());
  }

  /**
   * Tests the reportDamage method and that it returns the correct coordinates and mutates the
   * board correctly.
   */
  @Test
  public void reportDamageTest() {
    assertFalse(myBoard.getBoard()[1][2].isHit());
    assertFalse(myBoard.getBoard()[1][3].isHit());
    assertFalse(myBoard.getBoard()[1][4].isHit());
    assertFalse(myBoard.getBoard()[0][0].isHit());

    ArrayList<Coord> opPlayerShots = new ArrayList<>(Arrays.asList(new Coord(1, 2),
        new Coord(1, 3), new Coord(1, 4), new Coord(0, 0)));
    damagedCoords = aiPlayer.reportDamage(opPlayerShots);
  }
}