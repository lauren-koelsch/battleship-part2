package cs3500.pa03;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.controller.ProxyController;
import cs3500.pa03.model.AiPlayer;
import cs3500.pa03.model.Board;
import cs3500.pa03.view.GameDisplayer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Random;


/**
 * This is the main driver of this project.
 */
public class Driver {

  private static void runClient(String host, int port)
      throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    Random rand = new Random();
    Board board = new Board();
    AiPlayer player = new AiPlayer(board, rand);
    ProxyController proxyDealer = new ProxyController(server, player);
    proxyDealer.run();
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */

  public static void main(String[] args) {

    if (args.length != 0) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);

      try {
        Driver.runClient(host, port);
      } catch (IOException e) {
        throw new RuntimeException("Could not run program:" + e);
      }
    } else {

      Readable input = new InputStreamReader(System.in);
      GameDisplayer view = new GameDisplayer(System.out);
      Random rand = new Random();

      GameController controller = new GameController(input, view, rand);

      try {
        controller.run();
      } catch (IOException e) {
        throw new RuntimeException("Could not run program:" + e);
      }
    }
  }
}