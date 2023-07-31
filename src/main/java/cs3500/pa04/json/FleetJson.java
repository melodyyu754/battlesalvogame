package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

/**
 * A Json representing a fleet of ShipAdapters to send to the server.
 *
 * @param ships an ArrayList of ShipAdapters to be sent to the server
 */
public record FleetJson(@JsonProperty("fleet") ArrayList<ShipAdapter> ships) {
}