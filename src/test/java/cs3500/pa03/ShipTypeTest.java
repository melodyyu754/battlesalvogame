package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShipType;
import org.junit.jupiter.api.Test;

/**
 * Tests the ShipType enumeration.
 */
public class ShipTypeTest {
  /**
   * Instantiates the four ShipTypes.
   */
  ShipType submarine = ShipType.SUBMARINE;
  ShipType destroyer = ShipType.DESTROYER;
  ShipType battleship = ShipType.BATTLESHIP;
  ShipType carrier = ShipType.CARRIER;

  /**
   * Tests the getSize() method on each ShipType.
   */
  @Test
  public void getSizeTest() {
    assertEquals(3, submarine.getSize());
    assertEquals(4, destroyer.getSize());
    assertEquals(5, battleship.getSize());
    assertEquals(6, carrier.getSize());
  }
}
