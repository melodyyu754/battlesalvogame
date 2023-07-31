package cs3500.pa03.model.random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Produces a set stream of numbers and booleans when nextInt and nextBoolean is called.
 */
public class FakeServerRandom implements MyRandomInterface {
  private final List<Integer> numbers;
  private int numbersIndex;
  private boolean initialValue;

  /**
   * Constructor for FakeRandom, sets a list of numbers that gets looped through and an index.
   */
  public FakeServerRandom() {
    this.numbers = new ArrayList<>(Arrays.asList(0, 3, 1, 7, 7, 3, 2, 1, 1, 2, 3, 4,
        5, 6, 7, 8, 9));
    this.numbersIndex = 0;
    this.initialValue = false;
  }

  /**
   * Gets the next number in the list, and loops through.
   *
   * @param limit The upper limit (non-inclusive) of the random number desired
   * @return a fake random number under the limit.
   */
  @Override
  public int nextInt(int limit) {
    int val = numbers.get(numbersIndex);
    numbersIndex = (numbersIndex + 1) % numbers.size();
    if (val >= limit) {
      val = val % limit;
    }
    return val;
  }

  /**
   * Produces a fake random boolean (alternating)
   *
   * @return a fake random boolean (alternating)
   */

  @Override
  public boolean nextBoolean() {
    boolean result = initialValue;
    initialValue = !initialValue; // Toggle the initial value for the next call
    return result;
  }
}
