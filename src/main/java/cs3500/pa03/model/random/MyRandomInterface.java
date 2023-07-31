package cs3500.pa03.model.random;

/**
 * Interface that allows for testing without the use of a random seed.
 */
public interface MyRandomInterface {
  /**
   * Gets the next integer, whether real random or fake random.
   *
   * @param limit the upper limit of the possible random returns (non-inclusive)
   * @return a "random" number under the limit
   */
  int nextInt(int limit);

  /**
   * Gets the next boolean, whether real random or fake random.
   *
   * @return a "random" boolean
   */
  boolean nextBoolean();
}