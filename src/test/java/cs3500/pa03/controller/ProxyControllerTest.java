package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import json.EndGameJson;
import json.JoinJson;
import json.JsonUtils;
import json.MessageJson;
import json.SetupJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController proxyController;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * Check that the server returns a valid join response
   */
  @Test
  public void testJoin() {
    // Prepare sample message
    JoinJson joinJson = new JoinJson("lauren", "SINGLE");
    JsonNode sampleMessage = createSampleMessage("join", joinJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    Random rand = new Random();
    Board board = new Board();
    AiPlayer player = new AiPlayer(board, rand);

    // Create a Dealer
    try {
      this.proxyController = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    proxyController.run();

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"lauren-koelsch\","
        + "\"game-type\":\"SINGLE\"}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that the server returns a valid setup configuration
   */
  @Test
  public void testSetup() {

    Map<ShipType, Integer> map = new HashMap<>();

    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    // Prepare sample message
    SetupJson setupJson = new SetupJson(6, 6, map);
    JsonNode sampleMessage = createSampleMessage("setup", setupJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    Random rand = new Random(0);
    Board board = new Board();
    AiPlayer player = new AiPlayer(board, rand);

    // Create a Dealer
    try {
      this.proxyController = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    proxyController.run();

    String expected =
        "{\"method-name\":\"setup\","
            + "\"arguments\":{\"fleet\""
            + ":[{\"coord\":{\"x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},"
            + "{\"coord\":{\"x\":0,\"y\":5},\"length\":5,\"direction\":\"HORIZONTAL\"},"
            + "{\"coord\":{\"x\":2,\"y\":3},\"length\":4,\"direction\":\"HORIZONTAL\"},"
            + "{\"coord\":{\"x\":2,\"y\":2},\"length\":3,\"direction\":\"HORIZONTAL\"}]}}\n";
    assertEquals(expected, logToString());
  }

  /**
   * Check that the server returns a valid end game response
   */
  @Test
  public void testEndGame() {
    // Prepare sample message
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You won!");
    JsonNode sampleMessage = createSampleMessage("end-game", endGameJson);

    // Create the client with all necessary messages
    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    Random rand = new Random();
    Board board = new Board();
    AiPlayer player = new AiPlayer(board, rand);

    // Create a Dealer
    try {
      this.proxyController = new ProxyController(socket, player);
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    // run the dealer and verify the response
    proxyController.run();

    String expected = "";
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
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName   name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson =
        new MessageJson(messageName, JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

}