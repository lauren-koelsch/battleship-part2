package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

  Board testBoard;
  Board newBoard;

  @BeforeEach
  public void initData() {
    testBoard = new Board(2, 3);
    newBoard = new Board(0, 0);
  }

  @Test
  public void testInitBoard() {
    newBoard.initBoard(3, 4);
    assertEquals(4, newBoard.getBoard().get(0).size());
    assertEquals(4, newBoard.getBoard().get(1).size());
    assertEquals(4, newBoard.getBoard().get(2).size());
    assertEquals(3, newBoard.getBoard().size());
  }

  @Test
  public void testGetters() {
    assertEquals("_", testBoard.getBoard().get(1).get(1).getLabel());
    assertEquals(false, testBoard.getBoard().get(1).get(1).getIsShip());
    assertEquals(false, testBoard.getBoard().get(1).get(1).getIsHit());
  }

  @Test
  public void testReplaceWith() {
    Coord newCoord = new Coord(1, 1, "S", true, true);

    testBoard.replaceCoord(newCoord);

    assertEquals("S", testBoard.getBoard().get(1).get(1).getLabel());
    assertEquals(true, testBoard.getBoard().get(1).get(1).getIsShip());
    assertEquals(true, testBoard.getBoard().get(1).get(1).getIsHit());
  }
}