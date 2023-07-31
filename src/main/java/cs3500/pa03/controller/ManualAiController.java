package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualAiModel;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.BattleSalvoView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Controller for a game between a Manual player and an Ai player.
 */
public class ManualAiController implements Controller {
  private final Scanner scanner;
  private ManualAiModel model;
  private final BattleSalvoView view;

  private int height;
  private int width;

  private final ArrayList<Coord> currentSalvo;
  private MyBoard manualMyBoard;
  private OpBoard manualOpBoard;

  /**
   * Constructor for ManualAiController. Instantiates a scanner and view, then initializes the game
   * and runs it.
   *
   * @param readable   input coming in, from System.in in this case
   * @param appendable input going out, going to System.out in this case.
   */
  public ManualAiController(Readable readable, Appendable appendable) {
    this.scanner = new Scanner(readable);
    this.view = new BattleSalvoView(appendable);
    this.currentSalvo = new ArrayList<>(Arrays.asList(new Coord(0, 0)));
  }

  /**
   * Instantiates myBoards and opBoards for each player, and initializes the ManualPlayer and
   * AiPlayer in the game by collecting the height, width, and fleet specs from the user via
   * the scanner.
   * Handles invalid input gracefully by prompting the user to reenter input.
   * Displays the initial boards to the user at the end of the method.
   */
  @Override
  public void initGame() {
    view.print("Hello! Welcome to the OOD BattleSalvo Game! \n"
        + "Please enter a valid width and height below, separated by a whitespace: ");

    while (true) {
      String input = scanner.nextLine();
      String[] inputs = input.split(" ");
      try {
        width = checkDimension(inputs)[0];
        height = checkDimension(inputs)[1];
        break;
      } catch (Exception e) {
        view.print("Uh oh! You've entered invalid dimensions. Please remember that the width and "
            + "\nheight of the game must be in the range (6, 15), inclusive. Try again!");
      }
    }

    int maxFleet = Math.min(width, height);
    HashMap<ShipType, Integer> specs;
    view.print("Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine]. "
        + "Remember, your fleet may not exceed size " + maxFleet + ".");

    while (true) {
      String input = scanner.nextLine();
      String[] inputs = input.split(" ");
      try {
        specs = checkSpecs(inputs, maxFleet);
        break;
      } catch (Exception e) {
        view.print("Invalid input. Please enter valid inputs for number of ships.");
      }
    }

    this.manualMyBoard = new MyBoard(width, height);
    this.manualOpBoard = new OpBoard(width, height);
    MyBoard aiMyBoard = new MyBoard(width, height);
    OpBoard aiOpBoard = new OpBoard(width, height);

    ManualPlayer manualPlayer = new ManualPlayer(width, height, specs, currentSalvo, manualMyBoard,
        manualOpBoard);
    AiPlayer aiPlayer = new AiPlayer(width, height, specs, aiMyBoard, aiOpBoard);

    this.model = new ManualAiModel(manualPlayer, aiPlayer, manualMyBoard, aiMyBoard);
    view.displayBoards(manualMyBoard.getBoard(), manualOpBoard.getBoard());
  }

  /**
   * Checks a set of dimensions (height and width) to ensure they are valid.
   * Valid dimensions are a width and height between 6 and 15, inclusive.
   * Throws an IllegalArgumentException if requirements are not met.
   *
   * @param dimensions a String array to be processed
   * @return a validated integer array of two ints between 6 and 15.
   */
  private static Integer[] checkDimension(String[] dimensions) {
    if (!(dimensions.length == 2)) {
      throw new IllegalArgumentException();
    }
    int width = Integer.parseInt(dimensions[0]);
    int height = Integer.parseInt(dimensions[1]);
    if (height < 6 || width < 6 || height > 15 || width > 15) {
      throw new IllegalArgumentException();
    }
    return new Integer[] {width, height};
  }

  /**
   * Checks a set of specifications (number of ships of each type in the fleet) to ensure they
   * are valid. For a fleet to be valid, all ShipTypes must have at least one ship, and the total
   * number of ships cannot exceed the min dimension (between height and width). Throws an
   * IllegalArgumentException if the requirements of the fleet are not met. If the fleet is valid,
   * put the specs into a hashmap.
   *
   * @param specs a String array of specifications to be processed
   * @param max   the maximum number of ships total
   * @return a validated HashMap of ShipType to fleetSize.
   */
  private static HashMap<ShipType, Integer> checkSpecs(String[] specs, int max) {
    if (!(specs.length == 4)) {
      throw new IllegalArgumentException();
    }
    int carrierFleet = Integer.parseInt(specs[0]);
    int battleshipFleet = Integer.parseInt(specs[1]);
    int destroyerFleet = Integer.parseInt(specs[2]);
    int submarineFleet = Integer.parseInt(specs[3]);

    if (carrierFleet < 1 || battleshipFleet < 1 || destroyerFleet < 1 || submarineFleet < 1
        || carrierFleet + battleshipFleet + destroyerFleet + submarineFleet > max) {
      throw new IllegalArgumentException();
    }

    HashMap<ShipType, Integer> specsMap = new HashMap<>();
    specsMap.put(ShipType.CARRIER, carrierFleet);
    specsMap.put(ShipType.BATTLESHIP, battleshipFleet);
    specsMap.put(ShipType.DESTROYER, destroyerFleet);
    specsMap.put(ShipType.SUBMARINE, submarineFleet);
    return specsMap;
  }

  /**
   * Runs the game: takes in manual player shots equal to the number of ships. Validates the
   * inputted shots and sends these shots to the manualPlayer. Then, calls the model to play through
   * a round. When game is over, print a message to the user.
   */
  @Override
  public void runGame() {
    while (!model.gameOver()) { // while mPlayer and aiPlayer have shots left
      int shotsPerTurn = Math.min(manualMyBoard.getShotsLeft(), manualOpBoard.numUnhitCoords());
      ArrayList<Coord> salvo = new ArrayList<>();

      view.print("Please enter " + shotsPerTurn + " valid shots.");

      boolean invalidShot = false;
      for (int i = 0; i < shotsPerTurn; i++) {
        String input = scanner.nextLine();
        String[] coordinates = input.split(" ");
        try {
          salvo.add(model.checkShot(coordinates, width, height));
        } catch (Exception e) {
          invalidShot = true;
          view.print(e.getMessage());
          break;
        }
      }

      if (!invalidShot && salvo.size() == shotsPerTurn) {
        this.currentSalvo.clear();
        this.currentSalvo.addAll(salvo);
        model.playRound();
        view.displayBoards(manualMyBoard.getBoard(), manualOpBoard.getBoard());
      }
    }

    if (model.whoWon() == 0) {
      view.print("You tied. Neither you nor the aiPlayer has ships left.");
    } else if (model.whoWon() == 1) {
      view.print("You won! The ai player has no ships left.");
    } else {
      view.print("You lost. You have no ships left.");
    }
  }
}