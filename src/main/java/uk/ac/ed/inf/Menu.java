package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * This class deals with all the restaurants menu
 */

public class Menu {

    public String name;
    public  int priceInPence;


    /**
     * Creates a new JSON object with the JSON properties of name, and priceInPence
     * @param name  - The name of the menu items in the restaurant
     * @param priceInPence The price of the pizza in the menu
     */
    @JsonCreator
    public Menu(@JsonProperty("name") String name, @JsonProperty("priceInPence") int priceInPence) {
        this.name = name;
        this.priceInPence = priceInPence;
    }

    /**
     * Getter to return the price of the pizza
     * @return the price of the pizza
     */
    public int getPriceInPence() {
        return priceInPence;

    }

    /**
     * Getter to get the name of the pizza
     * @return the name of the pizza
     */

    public String getName() {
        return name;
    }
}

