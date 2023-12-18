package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * Represents a board in the game of BattleSalvo whose height and width are specified by the user
 */
public class Board {

  private ArrayList<ArrayList<Coord>> board;

  public Board() {

  }

  public Board(int height, int width) {
    initBoard(height, width);
  }

  public ArrayList<ArrayList<Coord>> getBoard() {
    return board;
  }

  /**
   * Initializes a board by making a 2D arrayList of coordinates to later be filled with game data
   *
   * @param row height of the board
   * @param column width of the board
   */
  public void initBoard(int row, int column) {

    this.board = new ArrayList<>(row);

    for (int c = 0; c < row; c++) {
      ArrayList<Coord> rows = new ArrayList<>(column);

      for (int r = 0; r < column; r++) {
        Coord oceanCoord = new Coord(c, r, "_", false, false);
        rows.add(oceanCoord);
      }
      this.board.add(rows);
    }
  }

  public void replaceCoord(Coord newCoord) {
    board.get(newCoord.getYvalue()).set(newCoord.getXvalue(), newCoord);
  }
}


