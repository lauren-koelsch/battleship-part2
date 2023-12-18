package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a coordinate (shot) in json message format
 */
public record CoordinatesJson(

    @JsonProperty("coordinates") List<CoordJson> shots) {
}
