package uk.ac.ed.inf;

/**
 * This class deals with any bad combination of pizzas and is used in Order.java class
 */

public class InvalidPizzaCombinationException extends Exception {
    /**
     * This method makes the invalidPizzaCombinationException and returns an error message when the exception is true
     * @param errorMessage returns errorMessage when the exception is true
     */
    public InvalidPizzaCombinationException(String errorMessage) {
        super(errorMessage);
    }

}
