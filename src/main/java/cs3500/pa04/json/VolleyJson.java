package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * A Json representing a Volley of CoordAdapter to send to and receive from
 * the server.
 *
 * @param shots a List of CoordAdapters to be sent to/received from the server
 */
public record VolleyJson(@JsonProperty("coordinates") List<CoordAdapter> shots) {
}
