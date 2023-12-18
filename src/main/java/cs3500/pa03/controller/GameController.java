package cs3500.pa03.controller;

import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.GameDisplayer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

/**
 * Controls the game while both players still have ships and ends it when one player has
 * no ships left
 */
public class GameController implements Controller {

  // Fields
  private final Readable input;

  private final GameDisplayer view;

  private final Scanner scan;

  private final Random rand;

  /**
   * Constructor for a class that begins the study session
   *
   * @param input the user input
   * @param view  the view part of the program
   * @param rand  random for random generation throughout program
   */
  public GameController(Readable input, GameDisplayer view, Random rand) {
    this.input = Objects.requireNonNull(input);
    this.view = view;
    this.scan = new Scanner(this.input);
    this.rand = rand;
  }

  /**
   * Runs the game until someone wins
   *
   * @throws IOException throws an exception if an invalid input is given
   */
  public void run() throws IOException {

    view.print("Hello! Welcome to the OOD BattleSalvo Game! \n"
        + "Please enter a valid height and width below:");

    List<Integer> dimensions = checkDimensions();
    int height = dimensions.get(0);
    int width = dimensions.get(1);

    Board userBoard = new Board(height, width);
    Board aiBoard = new Board(height, width);
    Player user = new ManualPlayer(userBoard, rand, this);
    Player ai = new AiPlayer(aiBoard, rand);

    int maxFleetSize = Math.min(height, width);

    view.print("Please enter your fleet in the order \n"
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + maxFleetSize + ".");

    List<Integer> fleet = checkFleet(maxFleetSize);
    int numShips = getNumShips(fleet);

    Map<ShipType, Integer> fleetMap = makeMap(fleet);

    List<Ship> userShips = user.setup(height, width, fleetMap);
    List<Ship> aiShips = ai.setup(height, width, fleetMap);

    GameResult gameStatus = GameResult.PLAYING;

    int numUserShots = numShips;

    while (gameStatus == GameResult.PLAYING) {

      ArrayList<ArrayList<String>> userBoardCoords =
          boardToStringUser(height, width, userBoard.getBoard());

      ArrayList<ArrayList<String>> aiBoardCoords =
          boardToStringAi(height, width, aiBoard.getBoard());

      view.printAiBoard(height, width, aiBoardCoords);

      view.printUserBoard(height, width, userBoardCoords);

      view.print("Please Enter " + numUserShots + " Shots:");

      List<Coord> userShotList = user.takeShots();

      List<Coord> aiShotList = ai.takeShots();

      user.reportDamage(aiShotList);

      ai.reportDamage(userShotList);

      List<Ship> userShipStatus = updateShipStatus(userShips);

      List<Ship> aiShipStatus = updateShipStatus(aiShips);

      numUserShots = updateShotCount(userShipStatus);
      int numAiShots = updateShotCount(aiShipStatus);

      if (numUserShots == 0) {
        view.print("GAMEOVER, YOU LOSE! \nYour opponent has sunk all of your ships!");
        gameStatus = GameResult.LOSE;
      } else if (numAiShots == 0) {
        view.print("GAMEOVER, YOU WIN! \nYou have sunk all of your opponent's ships!");
        gameStatus = GameResult.WIN;
      }
    }
  }

  private List<Integer> checkDimensions() {
    List<Integer> dimensions = new ArrayList<>(2);
    int height;
    int width;
    boolean goodDimensions = false;
    do {
      height = scan.nextInt();
      width = scan.nextInt();
      if (height >= 6 && height <= 15 && width >= 6 && width <= 15) {
        goodDimensions = true;
      } else {
        view.print("Uh oh? You've entered invalid dimensions. Please remember that the \n"
            + "height and width of the game must be in the range (6, 15), inclusive. \n"
            + "Try again!");
      }
    } while (!goodDimensions);
    dimensions.add(height);
    dimensions.add(width);
    return dimensions;
  }

  private List<Integer> checkFleet(int fleetSize) {
    List<Integer> fleet = new ArrayList<Integer>(fleetSize);
    int carrier;
    int battleship;
    int destroyer;
    int submarine;
    boolean goodFleetSize = false;
    do {
      carrier = scan.nextInt();
      battleship = scan.nextInt();
      destroyer = scan.nextInt();
      submarine = scan.nextInt();
      int sum = carrier + battleship + destroyer + submarine;
      if (sum <= fleetSize && carrier != 0 && battleship != 0 && destroyer != 0 && submarine != 0) {
        goodFleetSize = true;
      } else {
        view.print("Uh Oh! You've entered invalid fleet sizes.\n"
            + "Please enter your fleet in the order \n"
            + "[Carrier, Battleship, Destroyer, Submarine].\n"
            + "Remember, your fleet may not exceed size " + fleetSize + ".");
      }
    } while (!goodFleetSize);
    fleet.add(carrier);
    fleet.add(battleship);
    fleet.add(destroyer);
    fleet.add(submarine);
    return fleet;
  }

  private int getNumShips(List<Integer> list) {
    int sum = 0;
    for (int i = 0; i < list.size(); i++) {
      sum += list.get(i);
    }
    return sum;
  }

  private Map<ShipType, Integer> makeMap(List<Integer> fleet) {
    Map<ShipType, Integer> map = new HashMap<>();

    map.put(ShipType.CARRIER, fleet.get(0));
    map.put(ShipType.BATTLESHIP, fleet.get(1));
    map.put(ShipType.DESTROYER, fleet.get(2));
    map.put(ShipType.SUBMARINE, fleet.get(3));

    return map;
  }

  private ArrayList<ArrayList<String>> boardToStringUser(int height, int width,
                                                         ArrayList<ArrayList<Coord>> coords) {
    ArrayList<ArrayList<String>> column = new ArrayList<>();

    for (int c = 0; c < height; c++) {
      ArrayList<String> rows = new ArrayList<>();

      for (int r = 0; r < width; r++) {
        rows.add(coords.get(c).get(r).getLabel());
      }
      column.add(rows);
    }
    return column;
  }

  private ArrayList<ArrayList<String>> boardToStringAi(int height, int width,
                                                       ArrayList<ArrayList<Coord>> coords) {
    ArrayList<ArrayList<String>> column = new ArrayList<>();

    for (int c = 0; c < height; c++) {
      ArrayList<String> rows = new ArrayList<>();

      for (int r = 0; r < width; r++) {
        if (coords.get(c).get(r).getLabel().equals("#")
            || coords.get(c).get(r).getLabel().equals("x")) {
          rows.add(coords.get(c).get(r).getLabel());
        } else {
          rows.add("_");
        }
      }
      column.add(rows);
    }
    return column;
  }

  /**
   * Collects the shot positions that are inputted by the user and makes them into a list of coords
   *
   * @param numLiveShips number of ships that are still unsunk/number of shots
   * @return returns the list of coordinates that the player shot at
   */
  public List<Coord> collectShots(int numLiveShips) {
    List<Coord> shotList = new ArrayList<>();
    for (int i = 0; i < numLiveShips; i++) {
      int xvalue = scan.nextInt();
      int yvalue = scan.nextInt();
      Coord shot = new Coord(xvalue, yvalue);
      shotList.add(shot);
    }
    return shotList;
  }

  private List<Ship> updateShipStatus(List<Ship> ships) {
    for (Ship s : ships) {
      s.checkIsSunk();
    }
    return ships;
  }

  private int updateShotCount(List<Ship> shipStatusList) {
    int counter = shipStatusList.size();

    for (Ship s : shipStatusList) {
      if (s.getIsSunk()) {
        counter = counter - 1;
      }
    }
    return counter;
  }
}