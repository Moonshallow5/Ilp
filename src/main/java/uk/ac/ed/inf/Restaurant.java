package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

/**
 *  This class has the name, the position of the restaurant and the menu of each restaurant
 */

public class Restaurant {

    public String name; //Name of the Restaurant
    public double longitude; // Position of the restaurant
    public double latitude;
    public Menu[] menu; //Menu of th restaurant


    /**
     * Creates a new JSON object with the JSON properties of name, longitude, latitude and menu
     * @param name - The name of the restaurant
     * @param longitude - The longitude of the restaurant
     * @param latitude - The latitude of the restaurant
     * @param menu - The menu of the pizza's in the restaurant
     */
    @JsonCreator
    public Restaurant(@JsonProperty("name") String name, @JsonProperty("longitude") double longitude, @JsonProperty("latitude") double latitude, @JsonProperty("menu") Menu[] menu){
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.menu = menu;


    }

    /**
     * Gets data for each restaurant from the REST Server and returns the restaurants
     * @param serverBaseAddress - The URL to the REST Server
     * @return The array of restaurants
     * @throws IOException - when can't access the REST Server
     */

    static Restaurant[] getRestaurantsFromRestServer(URL serverBaseAddress) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var participants =  objectMapper.readValue(serverBaseAddress, Restaurant[].class);
        return participants; //Gets data from the REST server and return array of restaurants
    }

    /**
     * Getter to return the name of the restaurant
     * @return - The name of the Restaurant
     */
    public String getName() {
        return name;
    }
    public  LngLat getLocation(){
        return new LngLat(longitude,latitude);

    }

    public double getLongitude() {
        return longitude;
    }


    /**
     * Getter to return the menu of the restaurant
     * @return The Menu of the restaurants
     */
    public Menu[] getMenu() {
        return menu;
    }



}


