package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a fleet of ships in json format
 *
 * @param ships the list of ships to be converted to json format
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> ships) {

}
