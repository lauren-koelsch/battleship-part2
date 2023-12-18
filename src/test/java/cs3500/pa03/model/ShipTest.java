package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

  Coord c1;
  Coord c2;
  Coord c3;
  List<Coord> coords;
  Ship ship;

  Coord c4;
  Coord c5;
  Coord c6;
  List<Coord> coords2;
  Ship sunk;

  @BeforeEach
  public void initData() {
    c1 = new Coord(0, 0, "S", true, false);
    c2 = new Coord(0, 1, "S", true, false);
    c3 = new Coord(0, 2, "S", true, true);
    coords = new ArrayList<>();
    coords.add(c1);
    coords.add(c2);
    coords.add(c3);
    ship = new Ship(ShipType.SUBMARINE, coords, false);

    c4 = new Coord(3, 0, "S", true, true);
    c5 = new Coord(3, 1, "S", true, true);
    c6 = new Coord(3, 2, "S", true, true);
    coords2 = new ArrayList<>();
    coords2.add(c4);
    coords2.add(c5);
    coords2.add(c6);
    sunk = new Ship(ShipType.SUBMARINE, coords2, false);
  }

  @Test
  public void testGetCoords() {
    assertEquals(3, ship.getCoords().size());
    assertEquals(0, ship.getCoords().get(0).getYvalue());
  }

  @Test
  public void testGetIsSunk() {
    assertEquals(false, ship.getIsSunk());
  }

  @Test
  public void testCheckIsSunk() {
    assertEquals(false, ship.checkIsSunk());

    assertEquals(false, sunk.getIsSunk());
    assertEquals(true, sunk.checkIsSunk());
    assertEquals(true, sunk.getIsSunk());
  }
}