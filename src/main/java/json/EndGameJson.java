package json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * Represents an endgame in json format
 *
 * @param result the result of the game (win or lose)
 * @param reason reason for the outcome
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
