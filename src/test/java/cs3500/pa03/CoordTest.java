package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.model.Coord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the Coord datatype class.
 */
public class CoordTest {
  Coord c1;
  Coord c2;
  Coord c3;
  Coord c4;
  Coord c5;
  Coord c1Dupl;

  /**
   * Setup method, which instantiates the coordinates before each class.
   */
  @BeforeEach
  public void setup() {
    c1 = new Coord(0, 0);
    c2 = new Coord(0, 1);
    c3 = new Coord(0, 2);
    c4 = new Coord(0, 3);
    c5 = new Coord(1, 0);
    c1Dupl = new Coord(0, 0);
  }

  /**
   * Tests the getX() and getY() methods.
   */
  @Test
  public void getCoordinateTest() {
    assertEquals(0, c1.getX());
    assertEquals(0, c2.getX());
    assertEquals(0, c3.getX());
    assertEquals(0, c4.getX());

    assertEquals(0, c1.getY());
    assertEquals(1, c2.getY());
    assertEquals(2, c3.getY());
    assertEquals(3, c4.getY());
  }

  /**
   * Tests the isHit() method and the setHit(boolean b) method. Asserts that coordinates are
   * initialized with hit as false, but that hit field can be mutated with setHit method.
   */
  @Test
  public void hitTest() {
    assertFalse(c1.isHit());
    assertFalse(c2.isHit());
    c1.setHit(true);
    c2.setHit(false);
    assertTrue(c1.isHit());
    assertFalse(c2.isHit());
    c1.setHit(false);
    c2.setHit(true);
    assertFalse(c1.isHit());
    assertTrue(c2.isHit());
  }

  /**
   * Tests the isShip() method and the setShip(boolean b) method. Asserts that coordinates are
   * initialized with ship as false, but that ship field can be mutated with setShip method.
   */
  @Test
  public void shipTest() {
    assertFalse(c1.isShip());
    assertFalse(c2.isShip());
    c1.setShip(true);
    c2.setShip(false);
    assertTrue(c1.isShip());
    assertFalse(c2.isShip());
    c1.setShip(false);
    c2.setShip(true);
    assertFalse(c1.isShip());
    assertTrue(c2.isShip());
  }

  /**
   * Tests the overridden equals method. Asserts that methods with the same x and y coordinates are
   * equal, even if they are different objects and/or have different boolean values for hit/ship.
   */
  @Test
  public void equalsTest() {
    assertNotEquals(c1, c2);
    assertNotEquals(c1, c5);
    assertNotEquals(c1, "hello");

    assertEquals(c1, c1);
    assertEquals(c1, c1Dupl);
    c1Dupl.setShip(true);
    c1Dupl.setHit(true);
    assertEquals(c1, c1Dupl);
  }

  /**
   * Tests the hashcode method. Asserts that methods with the same x and y coordinates have the
   * same hashcode, even if they are different objects and/or have different boolean values for
   * hit/ship.
   */
  @Test
  public void hashCodeTest() {
    assertNotEquals(c1.hashCode(), c2.hashCode());

    assertEquals(c1.hashCode(), c1.hashCode());
    assertEquals(c1.hashCode(), c1Dupl.hashCode());
    c1Dupl.setShip(true);
    c1Dupl.setHit(true);
    assertEquals(c1.hashCode(), c1Dupl.hashCode());
  }
}
