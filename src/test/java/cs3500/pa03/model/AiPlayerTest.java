package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiPlayerTest {

  Board testBoard;
  Board smallBoard;
  AiPlayer ai;
  AiPlayer ai2;
  Random rand;

  @BeforeEach
  public void initData() {

    testBoard = new Board(6, 6);

    smallBoard = new  Board(2, 2);

    rand = new Random();

    ai = new AiPlayer(testBoard, rand);

    ai2 = new AiPlayer(smallBoard, rand);
  }

  @Test
  public void testSetup() {

    Map<ShipType, Integer> map = new HashMap<>();

    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    List<Ship> ships = ai.setup(6, 6, map);

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

    List<Coord> empty = ai2.reportDamage(noHit);
    assertEquals(0, empty.size());

    List<Coord> one = ai2.reportDamage(oneHit);
    assertEquals(1, one.size());
  }

  @Test
  public void testName() {
    assertEquals("AI Player", ai.name());
  }
}