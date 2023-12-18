package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.view.GameDisplayer;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;
import org.junit.jupiter.api.Test;

class GameControllerTest {

  @Test
  public void testRunInvalidBoardSize() {
    // Define our input we want to test
    String string = "6 6\n\n1 1 1 1\n\n"
        + "2 2\n3 2\n4 2\n5 2\n\n"
        + "3 3\n4 3\n5 3\n0 4\n\n"
        + "1 4\n2 4\n3 4\n4 4\n\n"
        + "0 5\n1 5\n2 5\n3 5\n\n"
        + "4 5\n5 5\n0 0\n1 0";

    // StringReader implements Readable
    Readable input = new StringReader(string);

    // StringBuilder implements Appendable
    Appendable output = new StringBuilder();

    GameDisplayer view = new GameDisplayer(output);

    Random rand = new Random(0);

    // pass them into the controller
    GameController controller = new GameController(input, view, rand);

    // check that the StringBuilder is empty to start
    assertEquals("", output.toString());

    try {
      controller.run();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals("Hello! Welcome to the OOD BattleSalvo Game! \n"
        + "Please enter a valid height and width below:\n"
        + "Please enter your fleet in the order \n"
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size 6.\n\n"
        + "OPPONENT'S BOARD: \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n\n"
        + "YOUR BOARD: \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  S  S  S  _  \n"
        + "_  _  D  D  D  D  \n"
        + "C  C  C  C  C  C  \n"
        + "B  B  B  B  B  _  \n\n"
        + "Please Enter 4 Shots:\n\n"
        + "OPPONENT'S BOARD: \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  #  #  #  #  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n\n"
        + "YOUR BOARD: \n"
        + "x  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  S  S  S  _  \n"
        + "_  _  #  #  D  D  \n"
        + "C  C  C  C  C  C  \n"
        + "#  B  B  B  B  _  \n\n"
        + "Please Enter 4 Shots:\n\n"
        + "OPPONENT'S BOARD: \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  #  #  #  #  \n"
        + "_  _  _  #  #  #  \n"
        + "#  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n\n"
        + "YOUR BOARD: \n"
        + "x  x  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  S  S  S  _  \n"
        + "x  _  #  #  D  D  \n"
        + "C  C  C  #  C  C  \n"
        + "#  B  B  B  B  _  \n\n"
        + "Please Enter 4 Shots:\n\n"
        + "OPPONENT'S BOARD: \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  #  #  #  #  \n"
        + "_  _  _  #  #  #  \n"
        + "#  #  #  #  #  _  \n"
        + "_  _  _  _  _  _  \n\n"
        + "YOUR BOARD: \n"
        + "x  x  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  S  S  S  _  \n"
        + "x  _  #  #  D  D  \n"
        + "C  C  C  #  C  #  \n"
        + "#  B  B  B  B  x  \n\n"
        + "Please Enter 4 Shots:\n\n"
        + "OPPONENT'S BOARD: \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  #  #  #  #  \n"
        + "_  _  _  #  #  #  \n"
        + "#  #  #  #  #  _  \n"
        + "#  #  #  #  _  _  \n\n"
        + "YOUR BOARD: \n"
        + "x  x  _  _  _  _  \n"
        + "_  _  _  _  _  _  \n"
        + "_  _  S  S  S  x  \n"
        + "x  _  #  #  D  D  \n"
        + "C  C  C  #  C  #  \n"
        + "#  B  B  B  B  x  \n\n"
        + "Please Enter 4 Shots:\nGAMEOVER, YOU WIN! \n"
        + "You have sunk all of your opponent's ships!\n", output.toString());
  }
}