package cs3500.pa03;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import cs3500.pa04.Driver;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * Test class for the driver.
 */
class DriverTest {
  Driver driver = new Driver();

  /**
   * Tests the main method, and that it doesn't throw exceptions beyond the expected
   * NoSuchElementException.
   */
  @Test
  public void testMainNoArgs() {
    try {
      driver.main(new String[] {});
    } catch (IllegalArgumentException e) {
      fail("Exception was thrown: " + e.getMessage());
    } catch (NoSuchElementException e) {
      // Expected exception
    }
  }

  @Test
  public void testInvalidMain() {
    String[] args = {"invalid arg"};
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> driver.main(args));

    assertEquals("Main method expects either 0 or 2 arguments.", exception.getMessage());
  }
}