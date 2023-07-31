package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * Model for a ManualAi game, the module in which the rounds are played and information is
 * exchanged between players.
 */
public class ManualAiModel implements Model {
  private final ManualPlayer manualPlayer;
  private final AiPlayer aiPlayer;
  private final MyBoard manualMyBoard;
  private final MyBoard aiMyBoard;

  /**
   * Constructor for the ManualAiModel with a manualPlayer and an AiPlayer.
   *
   * @param manualPlayer the manual player
   * @param aiPlayer     the aiPlayer
   * @param manualMyBoard the manual player's view of its own board
   * @param aiMyBoard the Ai player's view of its own board
   */
  public ManualAiModel(ManualPlayer manualPlayer, AiPlayer aiPlayer, MyBoard manualMyBoard,
                       MyBoard aiMyBoard) {
    this.manualPlayer = manualPlayer;
    this.aiPlayer = aiPlayer;
    this.manualMyBoard = manualMyBoard;
    this.aiMyBoard = aiMyBoard;
  }

  /**
   * Exchange between the mPlayer and aiPlayer. Mutates myBoard and opBoard for each player.
   */
  @Override
  public void playRound() {
    // shots taken by mPlayer
    ArrayList<Coord> manualPlayerShots = manualPlayer.takeShots();
    // shots taken by aiPlayer
    ArrayList<Coord> aiPlayerShots = aiPlayer.takeShots();

    // shots that struck mPlayer
    ArrayList<Coord> manualPlayerDamage = manualPlayer.reportDamage(aiPlayerShots);
    // shots that struck aiPlayer
    ArrayList<Coord> aiPlayerDamage = aiPlayer.reportDamage(manualPlayerShots);

    // successful shots from mPlayer
    manualPlayer.successfulHits(aiPlayerDamage);
    // succcessful shots from aiPlayer
    aiPlayer.successfulHits(manualPlayerDamage);
  }

  /**
   * Checks if a shot inputted by the user is valid. Throws an IllegalArgumentException if the
   * shot is not valid.
   *
   * @param inputs An Array of Strings that contains the coordinates of the shot
   * @param height The height of the board
   * @param width  The width of the board
   * @return the shot, if valid.
   */
  public Coord checkShot(String[] inputs, int height, int width) {
    int x = Integer.parseInt(inputs[0]); // 3
    int y = Integer.parseInt(inputs[1]); // 4

    if (x < 0 || x >= height || y < 0 || y >= width) {
      throw new IllegalArgumentException("Coordinates are off the board.");
    }

    return new Coord(x, y);
  }

  /**
   * Determines if the game is over or not based on the number of shots each player has left.
   * If either player has no shots left, the game is over.
   *
   * @return true if the game is over, false otherwise.
   */
  public boolean gameOver() {
    return manualMyBoard.getShotsLeft() == 0 || aiMyBoard.getShotsLeft() == 0;
  }

  /**
   * Determines if the manual player won, lost, or tied the game.
   *
   * @return -1 if the manual player lost, 0 if the players tied or the game isn't over yet,
   *     1 if the manual player won.
   */
  public int whoWon() {
    if (manualMyBoard.getShotsLeft() > 0 && aiMyBoard.getShotsLeft() == 0) {
      manualPlayer.endGame(GameResult.WIN, "The ai player has no ships left.");
      return 1;
    } else if (manualMyBoard.getShotsLeft() == 0 && aiMyBoard.getShotsLeft() > 0) {
      manualPlayer.endGame(GameResult.LOSE, "You have no ships left.");
      return -1;
    } else {
      manualPlayer.endGame(GameResult.LOSE, "Neither you nor the aiPlayer has ships left.");
      return 0;
    }
  }
}
