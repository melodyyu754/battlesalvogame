package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.MyBoard;
import cs3500.pa03.model.OpBoard;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.model.random.FakeServerRandom;
import cs3500.pa03.view.BattleSalvoView;
import cs3500.pa03.view.View;
import cs3500.pa04.json.CoordAdapter;
import cs3500.pa04.json.EndGameInputJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupInputJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ProxyController of a Server Battle Salvo game
 */
public class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  View view;

  MyBoard aiMyBoard;
  OpBoard aiOpBoard;
  Appendable output;

  AiPlayer aiPlayer;
  private static final JsonNode VOID_MESSAGE =
      new ObjectMapper().getNodeFactory().textNode("void");

  Map<ShipType, Integer> specs;

  /**
   * Initializes relevant data for testing.
   */
  @BeforeEach
  public void initData() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
    output = new StringBuilder();
    view = new BattleSalvoView(output);

    aiMyBoard = new MyBoard(6, 8);
    aiOpBoard = new OpBoard(6, 8);
    aiPlayer = new AiPlayer(view, aiMyBoard, aiOpBoard, new FakeServerRandom());

    specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.SUBMARINE, 1);
  }

  /**
   * Tests that the ProxyController responds correctly to a join request from the server.
   */
  @Test
  public void testJoinResponse() {
    // Create server join request
    MessageJson mockJoinRequest = new MessageJson("join", VOID_MESSAGE);
    JsonNode jsonJoinRequest = JsonUtils.serializeRecord(mockJoinRequest);

    // Create server with join request
    Mocket socket = new Mocket(this.testLog, List.of(jsonJoinRequest.toString()));

    // Set up AI connection to server
    this.controller = new ProxyController(socket, aiPlayer);
    this.controller.run();

    // Check expected AI response
    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"melodyyu754\","
        + "\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Tests that the ProxyController responds correctly to a setup request from the server.
   */
  @Test
  public void testSetupResponse() {
    // Initialize setup input as a Json, serialize into Message, serialize into Node
    SetupInputJson setupInput = new SetupInputJson(8, 6, specs);
    JsonNode setupJson = JsonUtils.serializeRecord(setupInput);
    MessageJson mockSetupRequest = new MessageJson("setup", setupJson);
    JsonNode jsonSetupRequest = JsonUtils.serializeRecord(mockSetupRequest);

    // Create server with setup request
    Mocket socket = new Mocket(this.testLog, List.of(jsonSetupRequest.toString()));

    // Set up AI connection to server
    this.controller = new ProxyController(socket, aiPlayer);
    this.controller.run();

    // Check that the expected AI response is a jsonSetup
    responseToClass(jsonSetupRequest.getClass());
  }

  /**
   * Tests that the ProxyController responds correctly to a take-shots request from the server.
   */
  @Test
  public void testTakeShotsResponse() {
    // Create server take-shots request
    MessageJson mockTakeShotsRequest = new MessageJson("take-shots", VOID_MESSAGE);
    JsonNode jsonTakeShotsRequest = JsonUtils.serializeRecord(mockTakeShotsRequest);

    // Create server with take-shots request
    Mocket socket = new Mocket(this.testLog, List.of(jsonTakeShotsRequest.toString()));

    // Setup AI and create connection to server
    aiPlayer.setup(6, 8, specs);

    this.controller = new ProxyController(socket, aiPlayer);
    this.controller.run();

    // Check that the expected AI response is a jsonTakeShots
    responseToClass(jsonTakeShotsRequest.getClass());
  }

  /**
   * Tests that the ProxyController responds correctly to a report-damage request from the server.
   */
  @Test
  public void testReportDamageResponse() {
    // Create "opponent shots," serialize into VolleyJson
    ArrayList<CoordAdapter> shots = new ArrayList<>(Arrays.asList(
        new CoordAdapter(0, 0), new CoordAdapter(0, 1),
        new CoordAdapter(1, 1), new CoordAdapter(3, 2)));
    VolleyJson volley = new VolleyJson(shots);
    JsonNode mockVolley = JsonUtils.serializeRecord(volley);

    // Create server take-shots request
    MessageJson mockReportDamageRequest = new MessageJson("report-damage", mockVolley);
    JsonNode jsonReportDamageRequest = JsonUtils.serializeRecord(mockReportDamageRequest);

    // Create server with take-shots request
    Mocket socket = new Mocket(this.testLog, List.of(jsonReportDamageRequest.toString()));

    // Setup AI and create connection to server
    aiPlayer.setup(6, 8, specs);
    this.controller = new ProxyController(socket, aiPlayer);
    this.controller.run();

    // Check that the expected AI response is a jsonTakeShots
    responseToClass(jsonReportDamageRequest.getClass());
  }

  /**
   * Tests that the ProxyController responds correctly to a successful-hits request from the server.
   */
  @Test
  public void testSuccessfulHitsResponse() {
    // Create "opponent shots," serialize into VolleyJson
    ArrayList<CoordAdapter> hits = new ArrayList<>(Arrays.asList(
        new CoordAdapter(3, 4), new CoordAdapter(0, 1),
        new CoordAdapter(1, 2), new CoordAdapter(5, 0)));
    VolleyJson volley = new VolleyJson(hits);
    JsonNode mockVolley = JsonUtils.serializeRecord(volley);

    // Create server successful-hits request
    MessageJson mockSuccessfulHitsRequest = new MessageJson("successful-hits", mockVolley);
    JsonNode jsonSuccessfulHitsRequest = JsonUtils.serializeRecord(mockSuccessfulHitsRequest);

    // Create server with take-shots request
    Mocket socket = new Mocket(this.testLog, List.of(jsonSuccessfulHitsRequest.toString()));

    // Setup AI and create connection to server
    aiPlayer.setup(6, 8, specs);
    this.controller = new ProxyController(socket, aiPlayer);
    this.controller.run();

    // Check expected AI response
    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":\"void\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Tests that the ProxyController responds correctly to an end-game request from the server.
   */
  @Test
  public void testEndGameResponse() {
    // Create end game input, serialize into Json.
    EndGameInputJson endGameInput = new EndGameInputJson(GameResult.WIN,
        "You sunk all of the other player's ships!");
    JsonNode endGameInputJson = JsonUtils.serializeRecord(endGameInput);

    // Create server end-game request
    MessageJson mockEndGameRequest = new MessageJson("end-game", endGameInputJson);
    JsonNode jsonEndGameRequest = JsonUtils.serializeRecord(mockEndGameRequest);

    // Create server with end-game request
    Mocket socket = new Mocket(this.testLog, List.of(jsonEndGameRequest.toString()));

    // Setup AI and create connection to server
    aiPlayer.setup(6, 8, specs);
    this.controller = new ProxyController(socket, aiPlayer);
    this.controller.run();

    // Check expected AI response
    String expected = "{\"method-name\":\"end-game\",\"arguments\":\"void\"}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }


  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
      // No error thrown when parsing to a GuessJson, test passes!
    } catch (IOException e) {
      // Could not read
      // -> exception thrown
      // -> test fails since it must have been the wrong type of response.
      fail();
    }
  }
}

