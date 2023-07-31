package cs3500.pa04;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.CoordAdapter;
import cs3500.pa04.json.EndGameInputJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.GameType;
import cs3500.pa04.json.JoinResponseJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupInputJson;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents the proxy controller for a Battle Salvo game against the server.
 */
public class ProxyController {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final AiPlayer player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * A void response to the server.
   */
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Constructor for a proxy controller.
   *
   * @param server the server representing an opponent's AI
   * @param player "our" AI player
   */
  public ProxyController(Socket server, AiPlayer player) { // check throw!!!
    this.server = server;

    try {
      this.in = server.getInputStream();
      this.out = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException("Experienced unexpected error when connecting to server.");
    }

    this.player = player;
  }

  /**
   * Starts the communication with the server, delegates messages until none are left.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // The parser has closed.
    }
  }


  /**
   * Delegates the given MessageJson to a handler helper to deal with
   * a specific point in the game.
   */
  private void delegateMessage(MessageJson message) {
    String name = message.name();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
      try {
        server.close();
      } catch (IOException e) {
        throw new RuntimeException("Unable to close socket.");
      }
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Responds to the Server with the GitHub username and chosen game type
   * (hard-coded).
   */
  private void handleJoin() {
    // Create response with username and game type, serialize into JsonNode.
    JoinResponseJson response = new JoinResponseJson(player.name(), GameType.SINGLE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    // Create new "join" message with JsonNode, send to server.
    MessageJson msg = new MessageJson("join", jsonResponse);
    JsonNode jsonMsg = JsonUtils.serializeRecord(msg);
    this.out.println(jsonMsg);
  }

  /**
   * Sends the height, width, and fleet specifications to the AIPlayer to
   * initialize their board. Sends a void response to the server.
   *
   * @param arguments the Json representation of a SetupInputJson.
   */
  private void handleSetup(JsonNode arguments) {
    // Convert given JsonNode to SetupInputJson, break into height, width, and specs.
    SetupInputJson setupInput = this.mapper.convertValue(arguments, SetupInputJson.class);
    int height = setupInput.height();
    int width = setupInput.width();
    Map<ShipType, Integer> specs = setupInput.specs();

    // Call player setup method.
    ArrayList<Ship> ships = player.setup(width, height, specs);

    // Convert Ships into ShipAdapters, create Fleet, serialize into JsonNode.
    ArrayList<ShipAdapter> shipAdapters = new ArrayList<>();
    for (Ship s : ships) {
      shipAdapters.add(new ShipAdapter(s.start(), s.getLocation().size(), s.direction()));
    }
    FleetJson fleet = new FleetJson(shipAdapters);
    JsonNode jsonResponse = JsonUtils.serializeRecord(fleet);

    // Create new "setup" message with Fleet, send to server.
    MessageJson msg = new MessageJson("setup", jsonResponse);
    JsonNode jsonMsg = JsonUtils.serializeRecord(msg);
    this.out.println(jsonMsg);
  }

  /**
   * Responds to the Server with the AI's generated shots.
   */
  private void handleTakeShots() {
    // Call player takeShots method.
    ArrayList<Coord> shots = player.takeShots();

    // Convert Coords into CoordAdapters, create Volley, serialize into JsonNode.
    ArrayList<CoordAdapter> coordAdapters = new ArrayList<>();
    for (Coord c : shots) {
      coordAdapters.add(new CoordAdapter(c.getX(), c.getY()));
    }
    VolleyJson volley = new VolleyJson(coordAdapters);
    JsonNode jsonResponse = JsonUtils.serializeRecord(volley);

    // Create new "take-shots" message with Fleet, send to server.
    MessageJson msg = new MessageJson("take-shots", jsonResponse);
    JsonNode jsonMsg = JsonUtils.serializeRecord(msg);
    this.out.println(jsonMsg);
  }

  /**
   * Sends the opponent's shots to the AIPlayer, sends the shots
   * that hit the AI's ships back to the server.
   *
   * @param arguments the Json representation of a Volley.
   */
  private void handleReportDamage(JsonNode arguments) {
    // Convert given JsonNode to VolleyJson
    VolleyJson volleyArgs = this.mapper.convertValue(arguments, VolleyJson.class);

    // Initializes an empty arrayList, and converts coordAdapters to coords.
    List<CoordAdapter> volley = volleyArgs.shots();
    ArrayList<Coord> shots = new ArrayList<>();
    for (CoordAdapter coordAdapter : volley) {
      shots.add(new Coord(coordAdapter.x(), coordAdapter.y()));
    }

    // Perform reportDamage method w/ player
    ArrayList<Coord> damagedCoords = player.reportDamage(shots);

    // Initializes an empty arrayList, and converts coordAdapters to coords.
    ArrayList<CoordAdapter> damagedCoordAdapters = new ArrayList<>();
    for (Coord coord : damagedCoords) {
      damagedCoordAdapters.add(new CoordAdapter(coord.getX(), coord.getY()));
    }

    // Converts ArrayList of coordAdapters into a volleyJson
    VolleyJson damagedVolley = new VolleyJson(damagedCoordAdapters);

    // Serialize volley, Create new "report-damage" message with volley, send to server.
    JsonNode jsonResponse = JsonUtils.serializeRecord(damagedVolley);
    MessageJson msg = new MessageJson("report-damage", jsonResponse);
    JsonNode jsonMsg = JsonUtils.serializeRecord(msg);
    this.out.println(jsonMsg);
  }

  /**
   * Sends the AI's successful shots to the Player to update it's model
   * of the opponent's board. Sends a void response back to the server.
   *
   * @param arguments the Json representation of a Volley.
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    // Convert given JsonNode to Volley.
    VolleyJson volley = this.mapper.convertValue(arguments, VolleyJson.class);
    List<CoordAdapter> successfulHits = volley.shots();

    // Convert Volley to Coords.
    ArrayList<Coord> hits = new ArrayList<>();
    for (CoordAdapter c : successfulHits) {
      hits.add(new Coord(c.x(), c.y()));
    }

    // Call player successfulHits method (void).
    player.successfulHits(hits);

    // Return void message.
    MessageJson msg = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode jsonMsg = JsonUtils.serializeRecord(msg);
    this.out.println(jsonMsg);
  }

  /**
   * Parses the given arguments as an EndGameInput, notifies the player if they won,
   * and sends a void response back to the server.
   *
   * @param arguments the Json representation of an EndGameInput.
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameInputJson input = this.mapper.convertValue(arguments, EndGameInputJson.class);
    GameResult result = input.result();
    String reason = input.reason();

    MessageJson msg = new MessageJson("end-game", VOID_RESPONSE);
    player.endGame(result, reason);
    JsonNode jsonMsg = JsonUtils.serializeRecord(msg);
    this.out.println(jsonMsg);
  }
}
