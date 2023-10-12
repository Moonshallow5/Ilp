package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 * Checks all the NoFlyZones
 */
public class NoFlyZones {

    public String name;
    public String[][] coordinates;

    /**
     *
     * @param name Name of the noflyzone
     * @param coordinates Where the noflyzone is
     */

    public NoFlyZones(@JsonProperty("name") String name,@JsonProperty("coordinates") String [][] coordinates){
        this.name=name;
        this.coordinates=coordinates;
    }

    /**
     *  Gets the data for all the noflyzones
     * @param serverBaseAddress Gets the data of the noflyzone
     * @return The noflyzones
     * @throws IOException When can't find URL
     */
    static NoFlyZones[] getNoFlyFromRestServer(URL serverBaseAddress) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var participants =  objectMapper.readValue(serverBaseAddress, NoFlyZones[].class);


        return participants; //Gets data from the REST server and return array of restaurants

    }

    /**
     *
     * @return Name of the noflyzone
     */


    public String getName() {
        return name;
    }

    public String[][] getCoordinates() {
        return coordinates;
    }
}
