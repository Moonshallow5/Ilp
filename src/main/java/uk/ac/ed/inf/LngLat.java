package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import java.net.URL;

/**
 * This class involves the latitude and longitude of the restaurant
 */

@JsonIgnoreProperties({"name"}) //We are ignoring name as we only care for the latitude and longitude in this class
public class LngLat {
    public double longitude;
    public double latitude;

    /**
     * Creates a new JSON object with the JSON properties of longitude and latitude
     * @param lng - The longitude of the restaurant
     * @param lat - The latitude of the restaurant
     */
    @JsonCreator
    public LngLat(@JsonProperty("longitude") double lng, @JsonProperty ("latitude")double lat){
        this.latitude=lat;
        this.longitude=lng;
    }

    /**
     * Getter function to get longitude of the restaurant
     * @return - The longitude of the restaurant
     */
    public double getLng() {
        return longitude;
    }

    /**
     * Getter function to get latitude of the restaurant
     * @return - The latitude of the restaurant
     */
    public double getLat() {
        return latitude;
    }

    /**
     * Determines if this position is in the central area or not
     * @return - true if this position is in the Central Area or false if it is not
     * @throws IOException when the URL can't be accessed
     */

    public boolean inCentralArea() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var endPoints = objectMapper.readValue(new URL("https://ilp-rest.azurewebsites.net/centralArea"), LngLat[].class);
        if(getLat()> 55.942617 && getLat()<55.946233 && getLng()> -3.192473 && getLng()<  -3.184319){

            return true; //Checks if a specific position in the REST Server is in the Central area or not
        }
        return false;
    }

    /**
     * Returns the Euclidean distance between 2 positions
     * @param lngLat - The distance is measured against this point
     * @return the Euclidean distance between 2 positions
     */
    public double distanceTo(LngLat lngLat) {
        double x1 = longitude;
        double x2 = lngLat.longitude;
        double y1=latitude;
        double y2=lngLat.latitude;
        return Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)); //This is the Euclidean distance formula
    }

    /**
     * Checks if the distance between 2 positions is close or not
     * @param lngLat - Checks if the distance against this point is less than 0.00015
     * @return true if the distance is less than 0.00015 and false otherwise
     */
    public boolean closeTo(LngLat lngLat){
        if(distanceTo(lngLat)< 0.00015){
            return  true;
        }return  false;


    }

    /**
     * Gives the next position the drone is going from its position now
     * @param direction - The direction the drone is going
     * @return The next position of the drone
     */
    public LngLat nextPosition(Direction direction){
        if(direction==null){
            return (new LngLat(longitude,latitude)); // If the drone is hovering the latitude and longitude is the same
        }
        switch (direction){// This is the compass direction
            case E:
                return (new LngLat(longitude+0.00015,latitude));
            case ENE:
                return (new LngLat(longitude+0.00015*Math.cos(Math. PI/8),latitude+0.00015*Math.sin(Math. PI/8)));

            case  NE:
                return (new LngLat(longitude+0.00015*Math.cos(Math.PI/4),latitude+0.00015*Math.sin(Math.PI/4)));

            case NNE:
                return (new LngLat(longitude+0.00015*Math.sin(Math.PI/8),latitude+0.00015*Math.cos(Math.PI/8)));

            case N:
                return (new LngLat(longitude,latitude+0.00015));

            case NNW:
                return (new LngLat(longitude-0.00015*Math.sin(Math.PI/8),latitude+0.00015*Math.cos(Math.PI/8)));

            case NW:
                return (new LngLat(longitude-0.00015*Math.cos(Math.PI/4),latitude+0.00015*Math.sin(Math.PI/4)));
            case WNW:
                return (new LngLat(longitude-0.00015*Math.cos(Math.PI/8),latitude+0.00015*Math.sin(Math.PI/8)));
            case W:
                return (new LngLat(longitude-0.00015,latitude));
            case WSW:
                return (new LngLat(longitude-0.00015*Math.cos(Math.PI/8),latitude-0.00015*Math.sin(Math.PI/8)));
            case SW:
                return (new LngLat(longitude-0.00015*Math.cos(Math.PI/4),latitude-0.00015*Math.sin(Math.PI/4)));
            case SSW:
                return (new LngLat(longitude-0.00015*Math.sin(Math.PI/8),latitude-0.00015*Math.cos(Math.PI/8)));
            case S:
                return (new LngLat(longitude,latitude-0.00015));
            case SSE:
                return (new LngLat(longitude+0.00015*Math.sin(Math.PI/8),latitude-0.00015*Math.cos(Math.PI/8)));
            case SE:
                return (new LngLat(longitude+0.00015*Math.cos(Math.PI/4),latitude-0.00015*Math.sin(Math.PI/4)));
            case ESE:
                return (new LngLat(longitude+0.00015*Math.cos(Math.PI/8),latitude-0.00015*Math.sin(Math.PI/8)));

            default:
                throw new IllegalArgumentException("Please provide a correct direction");
        }


    }


}
