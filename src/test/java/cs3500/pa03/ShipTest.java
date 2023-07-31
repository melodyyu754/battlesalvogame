package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Ship datatype class.
 */
public class ShipTest {

  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;
  Coord c5;
  Coord c6;
  Coord c7;
  Coord c8;
  Ship s1;
  Ship s2;

  /**
   * Setup method, which instantiates the coordinates and ships before each class.
   */
  @BeforeEach
  public void setup() {
    c1 = new Coord(0, 0);
    c2 = new Coord(0, 1);
    c2.setHit(true);
    c3 = new Coord(0, 2);
    c4 = new Coord(0, 3);
    s1 = new Ship(new ArrayList<>(Arrays.asList(c1, c2, c3, c4)));

    c5 = new Coord(0, 0);
    c5.setHit(true);
    c6 = new Coord(0, 1);
    c6.setHit(true);
    c7 = new Coord(0, 2);
    c7.setHit(true);
    c8 = new Coord(0, 3);
    c8.setHit(true);
    s2 = new Ship(new ArrayList<>(Arrays.asList(c5, c6, c7, c8)));
  }

  /**
   * Tests the isSunk() method. Asserts that ships with all coordinates hit are sunk.
   */
  @Test
  public void isSunkTest() {
    assertFalse(s1.isSunk());
    assertTrue(s2.isSunk());
  }

  /**
   * Tests the getLocation() method.
   */
  @Test
  public void getLocationTest() {
    assertEquals(new ArrayList<>(Arrays.asList(c1, c2, c3, c4)), s1.getLocation());
    assertEquals(new ArrayList<>(Arrays.asList(c5, c6, c7, c8)), s2.getLocation());
  }
}
