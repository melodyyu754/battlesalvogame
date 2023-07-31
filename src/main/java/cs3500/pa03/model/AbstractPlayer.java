package cs3500.pa03.model;

import cs3500.pa03.model.random.ActualRandom;
import cs3500.pa03.model.random.MyRandomInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Abstract class that implements the Player interface, includes shared instance fields and
 * methods of ManualPlayer and AiPlayer.
 */
public abstract class AbstractPlayer implements Player {
  /**
   * Protected field Coord[][] myBoard, so that the ManualPlayer and AiPlayer can access it.
   */
  protected MyBoard myBoard;
  /**
   * Protected field Coord[][] opBoard, so that the ManualPlayer and AiPlayer can access it.
   */
  protected OpBoard opBoard;
  private ArrayList<Ship> ships;
  /**
   * Protected field Random rd, so that the ManualPlayer and AiPlayer can access it.
   */
  protected MyRandomInterface rd;

  /**
   * Constructor for a player used in a real game.
   *
   * @param width  The width of the game board.
   * @param height The height of the game board.
   * @param specs  The hashmap representing how many there are of each ship.
   */
  AbstractPlayer(int width, int height, HashMap<ShipType, Integer> specs, MyBoard myBoard,
                 OpBoard opBoard) {
    this.rd = new ActualRandom();
    this.myBoard = myBoard;
    this.opBoard = opBoard;
    this.ships = setup(width, height, specs);
  }

  /**
   * Constructor for a player used in a test game (used with a random seed).
   *
   * @param width  The width of the game board.
   * @param height The height of the game board.
   * @param specs  The hashmap representing how many there are of each ship.
   * @param random Passed in random to seed the game, so it acts the same way everytime.
   */
  AbstractPlayer(int width, int height, HashMap<ShipType, Integer> specs,
                 MyBoard myBoard, OpBoard opBoard, MyRandomInterface random) {
    this.rd = random;
    this.myBoard = myBoard;
    this.opBoard = opBoard;
    this.ships = setup(width, height, specs);
  }

  /**
   * Constructor for server game, sets ships equal to a new arrayList to be initialized later.
   */
  public AbstractPlayer() {
    this.ships = new ArrayList<>();
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public abstract String name();

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board. Also sets up the boards and adjusting the boards to show the ships placed on
   * them.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public ArrayList<Ship> setup(int width, int height, Map<ShipType, Integer> specifications) {
    myBoard.setupBoard(width, height);
    opBoard.setupBoard(width, height);

    // Sort the specifications based on the number of each ship's occurrences
    List<Map.Entry<ShipType, Integer>> list = new ArrayList<>(specifications.entrySet());
    list.sort(Map.Entry.comparingByValue());
    Collections.reverse(list);

    ArrayList<Ship> setupShips = new ArrayList<>();

    for (Map.Entry<ShipType, Integer> entry : list) {
      int shipCount = entry.getValue();
      ShipType shipType = entry.getKey();

      for (int i = 0; i < shipCount; i++) {
        boolean isVertical = rd.nextBoolean();
        int x = rd.nextInt(width);
        int y = rd.nextInt(height);


        while (!isValidPosition(myBoard.getBoard(), x, y, shipType.getSize(), isVertical)) {
          isVertical = rd.nextBoolean();
          x = rd.nextInt(width);
          y = rd.nextInt(height);
        }

        ArrayList<Coord> location = new ArrayList<>();
        for (int j = 0; j < shipType.getSize(); j++) {
          if (isVertical) {
            myBoard.getBoard()[y + j][x].setShip(true);
            location.add(myBoard.getBoard()[y + j][x]);
          } else {
            myBoard.getBoard()[y][x + j].setShip(true);
            location.add(myBoard.getBoard()[y][x + j]);
          }
        }
        Ship ship = new Ship(location);
        setupShips.add(ship);
      }
    }

    this.ships = setupShips;
    myBoard.setShips(setupShips);
    return setupShips;
  }

  /**
   * Private helper method that checks if a Coord is a valid place to place a ship.
   *
   * @param board      The board on which the ship will be placed
   * @param x          The x value of the Coord.
   * @param y          The y value of the Coord
   * @param shipSize   The size of the ship to be placed
   * @param isVertical Boolean value for if the ship is vertical or not
   * @return true if valid position, false otherwise.
   */
  private boolean isValidPosition(Coord[][] board, int x, int y, int shipSize, boolean isVertical) {
    int height = board.length;
    int width = board[0].length;

    if ((isVertical && y + shipSize > height) || (!isVertical && x + shipSize > width)) {
      return false;
    }

    // For each Coord in a potential Ship, check if the possible location is already occupied
    for (int i = 0; i < shipSize; i++) {
      try {
        if (isVertical && board[y + i][x].isShip()) {
          return false;
        } else if (!isVertical && board[y][x + i].isShip()) {
          return false;
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        return false;
      }
    }

    return true;
  }

  /**
   * Abstract method; returns this player's shots on the opponent's board. The number of shots
   * returned should equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public abstract ArrayList<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board. Also mutates this player's myBoard and list
   * of ships to reflect being hit, and mutates this player's list of ships to reflect being hit.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *     ship on this board
   */
  @Override
  public ArrayList<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> damagedCoords = new ArrayList<>();
    for (Coord shot : opponentShotsOnBoard) {
      // sets the Coord in myBoard to "hit".
      myBoard.getBoard()[shot.getY()][shot.getX()].setHit(true);

      // add it to damagedCoords if it struck a ship
      for (Ship ship : ships) {
        for (Coord coord : ship.getLocation()) {
          if (shot.equals(coord)) {
            damagedCoords.add(shot);
            coord.setHit(true);
            break;
          }
        }
      }
    }

    return damagedCoords;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord shot : shotsThatHitOpponentShips) {
      opBoard.getBoard()[shot.getY()][shot.getX()].setShip(true);
    }
  }
}