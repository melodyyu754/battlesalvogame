package cs3500.pa03.model.random;

import java.util.Random;

/**
 * Random class implementing MyRandomInterface that uses the real nextInt and nextBool methods.
 */
public class ActualRandom implements MyRandomInterface {
  private Random rand = new Random();

  /**
   * Produces a real random number within the limit provided.
   *
   * @param limit The upper limit (non-inclusive) of the random number desired
   * @return a random integer less than the limit
   */
  @Override
  public int nextInt(int limit) {
    return rand.nextInt(limit);
  }

  /**
   * Produces a real random boolean
   *
   * @return a random boolean
   */
  @Override
  public boolean nextBoolean() {
    return rand.nextBoolean();
  }
}