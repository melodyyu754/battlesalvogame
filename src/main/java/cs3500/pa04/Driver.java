package cs3500.pa04;

import cs3500.pa03.controller.Controller;
import cs3500.pa03.controller.ManualAiController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.random.ActualRandom;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.View;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * The entry point of the program. If no command line arguments are given,
   * runs a Manual versus AI player game. If two command line arguments are given,
   * runs a game against the server.
   *
   * @param args either two of none - see above.
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      runAiServer(args);
    } else if (args.length == 0) {
      runManualVersusAi();
    } else {
      throw new IllegalArgumentException("Main method expects either 0 or 2 arguments.");
    }
  }

  private static void runAiServer(String[] args) {
    String host = args[0];
    int port = Integer.parseInt(args[1]);
    try {
      // Establish a server.
      Socket server = new Socket(host, port);

      // Create View and Boards for AI Player, create player
      View view = new BattleSalvoView(new PrintStream(System.out));
      OpBoard opBoard = new OpBoard();
      MyBoard myBoard = new MyBoard();
      AiPlayer player = new AiPlayer(view, myBoard, opBoard, new ActualRandom());

      // Initialize ProxyController and run game on server.
      ProxyController aiServerBattleSalvo = new ProxyController(server, player);
      aiServerBattleSalvo.run();
    } catch (IOException e) {
      throw new RuntimeException("Experiencing an unanticipated issue with server connection.");
    }
  }

  /**
   * Instantiates a controller, initializes it, and runs a game of Battle Salvo
   * with a Manual Player and AI Player.
   */
  private static void runManualVersusAi() {
    Controller manualAiBattleSalvo = new ManualAiController(new InputStreamReader(System.in),
        new PrintStream(System.out));
    manualAiBattleSalvo.initGame();
    manualAiBattleSalvo.runGame();
  }
}