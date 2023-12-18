package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.view.GameDisplayer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManualPlayerTest {

  Board testBoard;
  Board smallBoard;
  ManualPlayer user;
  ManualPlayer user2;
  Random rand;
  Readable input;
  Appendable output;
  GameDisplayer view;
  GameController ctrl;

  @BeforeEach
  public void initData() {

    testBoard = new Board(6, 6);

    smallBoard = new  Board(2, 2);

    rand = new Random();

    String string = "0 0";

    input = new StringReader(string);

    output = new StringBuilder();

    view = new GameDisplayer(output);

    ctrl = new GameController(input, view, rand);

    user = new ManualPlayer(testBoard, rand, ctrl);

    user2 = new ManualPlayer(smallBoard, rand, ctrl);
  }

  @Test
  public void testSetup() {

    Map<ShipType, Integer> map = new HashMap<>();

    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    List<Ship> ships = user.setup(6, 6, map);

    assertEquals(4, ships.size());
  }

  @Test
  public void testReportDamage() {
    Coord ship = new Coord(1, 1, "S", true, false);
    smallBoard.replaceCoord(ship);

    Coord miss = new Coord(0, 0);
    Coord hit = new Coord(1, 1);

    List<Coord> noHit = new ArrayList<>();
    noHit.add(miss);

    List<Coord> oneHit = new ArrayList<>();
    oneHit.add(hit);

    List<Coord> empty = user2.reportDamage(noHit);
    assertEquals(0, empty.size());

    List<Coord> one = user2.reportDamage(oneHit);
    assertEquals(1, one.size());
  }

  @Test
  public void testSuccessfulHits() {
    Coord ship = new Coord(1, 1, "S", true, false);
    smallBoard.replaceCoord(ship);

    Coord miss = new Coord(0, 0);
    Coord hit = new Coord(1, 1);

    List<Coord> noHit = new ArrayList<>();
    noHit.add(miss);

    List<Coord> oneHit = new ArrayList<>();
    oneHit.add(hit);

    List<Coord> empty = user2.reportDamage(noHit);
    assertEquals(0, empty.size());

    List<Coord> one = user2.reportDamage(oneHit);
    assertEquals(1, one.size());
  }

  @Test
  public void testName() {
    assertEquals("Human Player", user.name());
  }

  @Test
  public void testTakeShots() {
    assertEquals(0, user.takeShots().size());
  }
}