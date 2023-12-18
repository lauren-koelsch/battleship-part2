package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;

/**
 * Represents the setup part of the game in json format
 *
 * @param width desired width of the board
 * @param height desired height of the board
 * @param fleetSpec number of each ship type to be placed on the board
 */
public record SetupJson(

    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {
}
