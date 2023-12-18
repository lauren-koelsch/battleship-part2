package cs3500.pa03.view;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the class that is responsible for displaying the game to the user based on info given
 * by the GameController
 */
public class GameDisplayer implements View {

  private final Appendable out;

  public GameDisplayer(Appendable out) {
    this.out = out;
  }

  /**
   * Prints input given by the controller class
   *
   * @param input input received from controller class to be printed
   */
  public void print(String input) {
    try {
      out.append(input + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Could not print message: " + e);
    }
  }

  /**
   * Prints the users board and updates based on game input
   *
   * @param height height of board
   * @param width  width of board
   * @param list   list of strings at each coordinate on the board
   * @throws IOException throws an exception if the board cannot be printed
   */
  public void printUserBoard(int height, int width,
                             ArrayList<ArrayList<String>> list) throws IOException {
    try {
      out.append("YOUR BOARD: \n");
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          out.append(list.get(i).get(j) + "  ");
        }
        out.append("\n");
      }
      out.append("\n");

    } catch (IOException e) {
      throw new IllegalStateException("Could not print message: " + e);
    }
  }

  /**
   * Prints the AI players board and updates based on game input
   *
   * @param height height of board
   * @param width  width of board
   * @param list   list of strings at each coordinate on the board
   * @throws IOException throws an exception if the board cannot be printed
   */
  public void printAiBoard(int height, int width,
                           ArrayList<ArrayList<String>> list) throws IOException {

    try {
      out.append("\nOPPONENT'S BOARD: \n");
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          out.append(list.get(i).get(j) + "  ");
        }
        out.append("\n");
      }
      out.append("\n");

    } catch (IOException e) {
      throw new IllegalStateException("Could not print message: " + e);
    }
  }
}
