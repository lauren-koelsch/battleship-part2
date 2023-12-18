package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Direction;

/**
 * Represents a record of a ship
 *
 * @param coord starting coord of a ship
 * @param length length of a ship
 * @param direction direction (hor/vert) of a ship
 */
public record ShipJson(
      @JsonProperty("coord") CoordJson coord,
      @JsonProperty("length") int length,
      @JsonProperty("direction") Direction direction) {
}
