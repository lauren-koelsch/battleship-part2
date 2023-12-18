package json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the response from the client to join the game in json format
 *
 * @param name name of the player playing
 * @param gameType the desired game type either SINGLE or MULTI
 */
public record JoinJson(

    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gameType) {

  public JoinJson(String name, String gameType) {
    this.name = name;
    this.gameType = gameType;
  }
}
