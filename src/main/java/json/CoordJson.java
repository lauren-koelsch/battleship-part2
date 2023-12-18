package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;

/**
 * Represents a json message version of a coord object (coordinate)
 *
 * @param xvalue the x value of the coordinate
 * @param yvalue the y value of the coordinate
 */
public record CoordJson(

    @JsonProperty("x") int xvalue,
    @JsonProperty("y") int yvalue) {

  public Coord convert() {
    return new Coord(xvalue, yvalue);
  }
}
