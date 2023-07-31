package cs3500.pa03.model;

import cs3500.pa03.model.random.MyRandomInterface;
import cs3500.pa03.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a player that generates its own shots.
 */
public class AiPlayer extends AbstractPlayer {
  private final String name;
  private final View view;
  private final ArrayList<Coord> worklist = new ArrayList<>();

  /**
   * Constructor for an AI player playing against the server, since
   * there is a delay in instantiating the fleet.
   *
   * @param view    the view that will display the end result to this AI
   * @param myBoard this AI's board
   * @param opBoard this AI's opponent's board (the server)
   * @param rd      Passed in random, either actual random or a set stream of numberes/booleans
   */
  public AiPlayer(View view, MyBoard myBoard, OpBoard opBoard, MyRandomInterface rd) {
    super();
    this.name = "melodyyu754";
    this.rd = rd;
    this.myBoard = myBoard;
    this.opBoard = opBoard;
    this.view = view;
  }

  /**
   * Constructor for the AiPlayer used in a real game of Manual Player versus AI.
   *
   * @param width   The width of the game board.
   * @param height  The height of the game board.
   * @param specs   The hashmap representing how many there are of each ship.
   * @param myBoard The AiPlayer's view of its own board.
   * @param opBoard The AiPlayer's view of its opponent's board.
   */
  public AiPlayer(int width, int height, HashMap<ShipType, Integer> specs,
                  MyBoard myBoard, OpBoard opBoard) {
    super(width, height, specs, myBoard, opBoard);
    this.name = "AiPlayer";
    this.view = null;
  }

  /**
   * Constructor for the AiPlayer used in a test game (used with a random seed).
   *
   * @param width   The width of the game board.
   * @param height  The height of the game board.
   * @param specs   The hashmap representing how many there are of each ship.
   * @param myBoard The AiPlayer's view of its own board.
   * @param opBoard The AiPlayer's view of its opponent's board.
   * @param random  Passed in random to seed the game so it acts the same way everytime.
   */
  public AiPlayer(int width, int height, HashMap<ShipType, Integer> specs, MyBoard myBoard,
                  OpBoard opBoard, MyRandomInterface random) {
    super(width, height, specs, myBoard, opBoard, random);
    this.name = "AiPlayer";
    this.view = null;
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Returns this player's shots on the opponent's board. Searches first through a worklist, then
   * randomly with spacing, then randomly.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public ArrayList<Coord> takeShots() {
    int numShots = Math.min(myBoard.getShotsLeft(), opBoard.numUnhitCoords());
    ArrayList<Coord> shots = new ArrayList<>();

    while (shots.size() < numShots) {
      // There are known shots in the worklist to deal with.
      if (!worklist.isEmpty()) {
        Coord nextShot = worklist.remove(0);
        if (!nextShot.isHit()) {
          shots.add(nextShot);
          opBoard.getBoard()[nextShot.getY()][nextShot.getX()].setHit(true);
        }
        // Random shots that fulfill the ((x + y) % 3 == 0) requirement and aren't hit yet.
      } else {
        boolean validShotFound = spacedSearch(shots);
        if (!validShotFound) {
          randomSearch(shots);
        }
      }
    }
    return shots;
  }

  /**
   * Searches the board for shots that are not hit yet, spacing out by 3 to account for the
   * smallest size of a ship being 3.
   *
   * @param shots the list of shots to be taken that builds until it reaches the size of
   *              total shots.
   */
  private boolean spacedSearch(ArrayList<Coord> shots) {
    int x;
    int y;
    boolean validShot = false;
    int attempts = 0;
    int maxAttempts =
        myBoard.getBoard().length * myBoard.getBoard()[0].length;

    while (!validShot && attempts < maxAttempts) {
      y = rd.nextInt(opBoard.getBoard().length);
      x = rd.nextInt(opBoard.getBoard()[0].length);
      Coord nextShot = opBoard.getBoard()[y][x];
      if (!nextShot.isHit() && ((x + y) % 3 == 0)) {
        shots.add(nextShot);
        opBoard.getBoard()[y][x].setHit(true);
        validShot = true;
      }
      attempts++;
    }
    return validShot;
  }


  /**
   * Searches the board randomly for coords that are not hit yet.
   *
   * @param shots the list of shots to be taken that builds until it reaches the size of
   *              total shots.
   */
  private void randomSearch(ArrayList<Coord> shots) {
    int x;
    int y;
    boolean validShot = false;
    while (!validShot) {
      y = rd.nextInt(opBoard.getBoard().length);
      x = rd.nextInt(opBoard.getBoard()[0].length);

      Coord nextShot = opBoard.getBoard()[y][x];
      if (!nextShot.isHit()) {
        shots.add(nextShot);
        opBoard.getBoard()[y][x].setHit(true);
        validShot = true;
      }
    }
  }

  /**
   * Reports to this player what shots in their previous volley hit an opponent's ship.
   * Allows AI Player to update their worklist of shots to take in the next round by
   * checking the location of successful hits and adding valid adjacent Coords to the worklist.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord shot : shotsThatHitOpponentShips) {
      int shotY = shot.getY();
      int shotX = shot.getX();
      opBoard.getBoard()[shotY][shotX].setShip(true);

      if (shotY != 0) {
        // add above coord
        this.worklist.add(opBoard.getBoard()[shotY - 1][shotX]);
      }
      if (shotX != 0) {
        // add left coord
        this.worklist.add(opBoard.getBoard()[shotY][shotX - 1]);
      }
      if (shotY != (this.opBoard.getBoard().length - 1)) {
        // add below coord
        this.worklist.add(opBoard.getBoard()[shotY + 1][shotX]);
      }
      if (shotX != (this.opBoard.getBoard()[0].length - 1)) {
        // add right coord
        this.worklist.add(opBoard.getBoard()[shotY][shotX + 1]);
      }
    }
  }

  /**
   * Alters the AI Player that the game has ended, and the outcome.
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
    view.print("Game over: " + result + "\n" + reason);
  }
}
