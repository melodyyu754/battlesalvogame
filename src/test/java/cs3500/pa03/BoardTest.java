package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for boards.
 */
public class BoardTest {
  Coord c1;
  Coord c2;
  MyBoard myBoard;
  Coord[][] myBoardArray;
  OpBoard opBoard;
  Coord[][] opBoardArray;
  ArrayList<Ship> ships;
  ArrayList<Ship> ships2;

  /**
   * Setup method for each test; sets up ships as well as boards.
   */
  @BeforeEach
  public void setup() {
    c1 = new Coord(0, 0);
    c2 = new Coord(0, 1);

    Ship ship1 = new Ship(new ArrayList<>(Arrays.asList(c1, c2)));
    Ship ship2 = new Ship(new ArrayList<>(Arrays.asList(
        new Coord(1, 0), new Coord(1, 1))));

    ships = new ArrayList<>(Arrays.asList(ship1, ship2));

    ships2 = new ArrayList<>(Arrays.asList(ship1));

    myBoard = new MyBoard(6, 8);
    myBoard.setShips(ships);
    myBoardArray = new Coord[8][6];
    opBoardArray = new Coord[8][6];
    opBoard = new OpBoard(6, 8);
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 6; j++) {
        myBoardArray[i][j] = new Coord(j, i);
        opBoardArray[i][j] = new Coord(j, i);
      }
    }
  }

  /**
   * Tests that getting the board returns the correct 2d array.
   */
  @Test
  public void getBoardTest() {
    for (int i = 0; i < myBoard.getBoard().length; i++) {
      for (int j = 0; j < myBoard.getBoard()[0].length; j++) {
        assertEquals(myBoard.getBoard()[i][j], myBoardArray[i][j]);
        assertEquals(opBoard.getBoard()[i][j], opBoardArray[i][j]);
      }
    }
  }

  /**
   * Tests that when all the Coords of a ship is sunk, the number of shots left decreases.
   */
  @Test
  public void getShotsLeftTest() {
    assertEquals(2, myBoard.getShotsLeft());

    c1.setHit(true);
    c2.setHit(true);
    assertEquals(1, myBoard.getShotsLeft());
  }

  /**
   * Tests the setShips() method and ensures that it mutates the ships of a board.
   */
  @Test
  public void setShipsTest() {
    assertEquals(2, myBoard.getShotsLeft());
    myBoard.setShips(ships2);
    assertEquals(1, myBoard.getShotsLeft());
  }
}
