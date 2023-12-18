package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDisplayerTest {
  GameDisplayer tester;
  StringBuilder sb;

  ArrayList<ArrayList<String>> column;

  @BeforeEach
  public void before() {
    sb = new StringBuilder();
    tester = new GameDisplayer(sb);

    column = new ArrayList<ArrayList<String>>();

    for (int c = 0; c < 2; c++) {
      ArrayList<String> row = new ArrayList<String>();

      for (int r = 0; r < 2; r++) {
        row.add("_");
      }
      column.add(row);
    }
  }

  @Test
  public void testPrint() {
    tester.print("hi");

    assertEquals(sb.toString(), "hi\n");
  }

  @Test
  public void testPrintAiBoard() throws IOException {

    tester.printAiBoard(2, 2, column);

    assertEquals("\nOPPONENT'S BOARD: \n"
        + "_  _  \n"
        + "_  _  \n"
        + "\n", sb.toString());
  }

  @Test
  public void testPrintUserBoard() throws IOException {

    tester.printUserBoard(2, 2, column);

    assertEquals("YOUR BOARD: \n"
        + "_  _  \n"
        + "_  _  \n"
        + "\n", sb.toString());
  }

  @Test
  public void testPrintThrowsException() {
    GameDisplayer view = new GameDisplayer(MockAppendable.appendable);

    assertThrows(
        IllegalStateException.class, () -> view.print("Hello"));

    assertThrows(
        IllegalStateException.class, () -> view.printUserBoard(2, 2, column));

    assertThrows(
        IllegalStateException.class, () -> view.printAiBoard(2, 2, column));
  }
}