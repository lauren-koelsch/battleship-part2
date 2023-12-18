package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import json.CoordJson;
import json.CoordinatesJson;
import json.EndGameJson;
import json.FleetJson;
import json.JoinJson;
import json.MessageJson;
import json.SetupJson;
import json.ShipJson;

/**
 * Class to serve as a proxy controller to communicate between the server and the client
 */
public class ProxyController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final AiPlayer player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if
   */
  public ProxyController(Socket server, AiPlayer player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }


  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }


  /**
   * Determines the type of request the server has sent ("guess" or "win") and delegates to the
   * corresponding helper method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   */
  private void delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    System.out.println(name);

    switch (name) {
      case "join":
        handleJoin();
        break;
      case "setup":
        handleSetup(arguments);
        break;
      case "take-shots":
        handleTakeShots();
        break;
      case "report-damage":
        handleReportDamage(arguments);
        break;
      case "successful-hits":
        handleSuccessfulHits(arguments);
        break;
      case "end-game":
        handleEndGame(arguments);
        break;
      default:
        throw new IllegalStateException("Invalid message name");
    }
  }

  private void handleJoin() {
    JoinJson join = new JoinJson("lauren-koelsch", "SINGLE");

    JsonNode node = mapper.convertValue(join, JsonNode.class);
    MessageJson message = new MessageJson("join", node);

    JsonNode newNode = mapper.convertValue(message, JsonNode.class);
    this.out.println(newNode);
  }

  private void handleSetup(JsonNode arguments) {
    // TODO: handle exception differently
    SetupJson setup =
        mapper.convertValue(arguments, SetupJson.class);

    int width = setup.width();
    int height = setup.height();
    Map<ShipType, Integer> map = setup.fleetSpec();

    List<Ship> ships = player.setup(height, width, map);

    List<ShipJson> jsonShips = new ArrayList<>();
    for (Ship ship : ships) {
      jsonShips.add(ship.convert());
    }

    FleetJson fleet = new FleetJson(jsonShips);

    JsonNode node = mapper.convertValue(fleet, JsonNode.class);
    MessageJson message = new MessageJson("setup", node);

    JsonNode newNode = mapper.convertValue(message, JsonNode.class);
    this.out.println(newNode);
  }

  private void handleTakeShots() {
    List<Coord> shots = player.takeShots();

    List<CoordJson> jsonShots = new ArrayList<>();
    for (Coord coord : shots) {
      jsonShots.add(coord.convert());
    }

    CoordinatesJson coords = new CoordinatesJson(jsonShots);

    JsonNode node = mapper.convertValue(coords, JsonNode.class);
    MessageJson message = new MessageJson("take-shots", node);

    JsonNode newNode = mapper.convertValue(message, JsonNode.class);
    this.out.println(newNode);
  }

  private void handleReportDamage(JsonNode arguments) {
    CoordinatesJson shots = mapper.convertValue(arguments, CoordinatesJson.class);

    List<Coord> coords = new ArrayList<>();
    for (CoordJson c : shots.shots()) {
      coords.add(c.convert());
    }

    List<Coord> damage = player.reportDamage(coords);

    List<CoordJson> jsonShots = new ArrayList<>();
    for (Coord coord : damage) {
      jsonShots.add(coord.convert());
    }

    CoordinatesJson newCoords = new CoordinatesJson(jsonShots);

    JsonNode node = mapper.convertValue(newCoords, JsonNode.class);
    MessageJson message = new MessageJson("report-damage", node);

    JsonNode newNode = mapper.convertValue(message, JsonNode.class);
    this.out.println(newNode);
  }

  private void handleSuccessfulHits(JsonNode arguments) {
    CoordinatesJson shots = mapper.convertValue(arguments, CoordinatesJson.class);

    List<Coord> coords = new ArrayList<>();
    for (CoordJson c : shots.shots()) {
      coords.add(c.convert());
    }

    player.successfulHits(coords);

    JsonNode node = JsonNodeFactory.instance.objectNode();
    MessageJson response = new MessageJson("successful-hits", node);

    JsonNode newNode = mapper.convertValue(response, JsonNode.class);
    this.out.println(newNode);
  }

  private void handleEndGame(JsonNode arguments) {
    EndGameJson end = mapper.convertValue(arguments, EndGameJson.class);

    System.out.println(end.result() + " Reason:" + end.reason());
  }
}