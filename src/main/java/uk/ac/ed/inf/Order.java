package uk.ac.ed.inf;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * This class deals with the order of the customer
 */
public class Order {
    public Order(){

    }
    ArrayList< String> orders_collecting=new ArrayList<>(); // Collects all the orders of the drone


    public String orderNo;
    public String orderStatus;
    public String orderValidationCode;

    public String orderDate;
    //public String customer;
    public String creditCardNumber;
    public String creditCardExpiry;
    public String cvv;
    public int priceTotalInPence;
    public String[] pizzasInOrder;


    /**
     * Checks if the pizza the customer wants exists, or are from different restaurants, or no pizza's, or too many are entered
     * @param i random integers made from the getDeliveryCost method to make it simple to pass the correct exception
     * @throws InvalidPizzaCombinationException - when pizza's don't exist, or are from different restaurants, or no pizza's, or too many pizza's are entered
     */
    int nams=0; //Counter to check the number of deliveries done


    @JsonCreator
    public Order(@JsonProperty("orderNo") String orderNo, @JsonProperty("orderDate") String orderDate, @JsonProperty("orderStatus") String orderStatus, @JsonProperty ("orderValidationCode") String orderValidationCode,@JsonProperty("priceTotalInPence") int priceTotalInPence, @JsonProperty("pizzasInOrder") String[] pizzasInOrder,   String customer,@JsonProperty("creditCardNumber") String creditCardNumber,@JsonProperty("creditCardExpiry") String creditCardExpiry,@JsonProperty("cvv") String cvv){
        this.orderNo=orderNo;
        this.orderDate=orderDate;
        this.orderStatus=orderStatus;
        this.orderValidationCode=orderValidationCode;
        this.creditCardNumber=creditCardNumber;
        this.cvv=cvv;
        this.creditCardExpiry=creditCardExpiry;
        this.priceTotalInPence=priceTotalInPence;
        this.pizzasInOrder=pizzasInOrder;
        //this.customer=customer;


    }

    /**
     *
     * @param serverBaseAddress URL for the orders
     * @return Data for the orders
     * @throws IOException Can't find URL
     */
    static Order[] getOrdersFromRestServer(URL serverBaseAddress) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var participants =  objectMapper.readValue(serverBaseAddress, Order[].class);
        return participants; //Gets data from the REST server and return array of restaurants
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getCreditCardNumber(){
        return creditCardNumber;
    }

    public String getCreditCardExpiry() {
        return creditCardExpiry;
    }

    public String getCvv() {
        return cvv;
    }

    public String[] getPizzasInOrder() {
        return pizzasInOrder;
    }

    public int getPriceTotalInPence() {
        return priceTotalInPence;
    }
 static OrderOutcome outcome;
    ArrayList<LngLat> copy=new ArrayList<>();
    ArrayList< LngLat> copy_positions=new ArrayList<>();
    ArrayList< LngLat> copy_positions_next=new ArrayList<>();
    ArrayList<Double> anglez_copy=new ArrayList<>();
    ArrayList<Long> time_yo=new ArrayList<>();
    ArrayList<Order> second_yo=new ArrayList<>();
    ArrayList<Order> third_yo=new ArrayList<>();
     public ArrayList <String> everything_order_outcome=new ArrayList<>();

    /**
     *  Shows the outcome for each order
     * @param orderDate Date for the order
     * @throws IOException when URL can't be found
     */

    public  void orderOutcome(String orderDate) throws IOException{



        var restaur = Restaurant.getRestaurantsFromRestServer(new URL("https://ilp-rest.azurewebsites.net/restaurants"));


        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();

        var yo = Order.getOrdersFromRestServer(new URL("https://ilp-rest.azurewebsites.net/orders/" + orderDate));
        //Gets order for a specific date
        for (int i = 0; i < yo.length; i++) {


                char x = yo[i].getCreditCardExpiry().charAt(3); // Checks if the expiry of the card is correct
                char y = yo[i].getCreditCardExpiry().charAt(4);
                char a = yo[i].getOrderDate().charAt(2); //Checks if the date of the order is correct
                char b = yo[i].getOrderDate().charAt(3);
                sb2.append(a);
                sb2.append(b);
                String str2 = sb2.toString();
                int number2 = Integer.parseInt(str2);
                sb.append(x);
                sb.append(y);
                String str = sb.toString();
                int number = Integer.parseInt(str);
                sb2.delete(0, 2);
                sb.delete(0, 2);
                char month_1 = yo[i].getCreditCardExpiry().charAt(0);
                char month_2 = yo[i].getCreditCardExpiry().charAt(1);
                sb3.append(month_1);
                sb3.append(month_2);
                String str3 = sb3.toString();
                sb3.delete(0, 2);

                if (yo[i].getCreditCardNumber().length() != 16) {
                    outcome = OrderOutcome.InvalidCardNumber;
                    everything_order_outcome.add("InvalidCardNumber"); //Adds all the order outcomes


                } else if (yo[i].getCvv().length() != 3) {
                    outcome = OrderOutcome.InvalidCvv;
                    everything_order_outcome.add("InvalidCvv");

                } else if (number2 >= number) {

                    outcome = OrderOutcome.InvalidExpiryDate;
                    everything_order_outcome.add("InvalidExpiryDate");

                } else if (getDeliveryCost(restaur, yo[i].getPizzasInOrder()) == -2) {
                    outcome = OrderOutcome.InvalidPizzaCount;
                    everything_order_outcome.add("InvalidPizzaCount");

                } else if (getDeliveryCost(restaur, yo[i].getPizzasInOrder()) == -1) {

                    outcome = OrderOutcome.InvalidPizzaNotDefined;
                    everything_order_outcome.add("InvalidPizzaNotDefined");

                } else if (getDeliveryCost(restaur, yo[i].getPizzasInOrder()) == -5) {

                    outcome = OrderOutcome.InvalidPizzaCombinationMultipleSuppliers;
                    everything_order_outcome.add("InvalidPizzaCombinationMultipleSuppliers");

                } else if (getDeliveryCost(restaur, yo[i].getPizzasInOrder()) != yo[i].getPriceTotalInPence()) {

                    outcome = OrderOutcome.InvalidTotal;
                    everything_order_outcome.add("InvalidTotal");

                } else {
                    System.out.println(Arrays.toString(yo[i].getPizzasInOrder()));

                    second_yo.add(yo[i]); //Adds the valid orders into a new arraylist

                }

        }



        for (int i = 0; i < second_yo.size(); i++) {// Group the orders from closest to furthest
            if(second_yo.get(i).getPizzasInOrder()[0].equals("Super Cheese")|| second_yo.get(i).getPizzasInOrder()[0].equals("All Shrooms")){
                third_yo.add(second_yo.get(i));

            }
        }
        for (int i = 0; i < second_yo.size(); i++) {
            if(second_yo.get(i).getPizzasInOrder()[0].equals("Margarita")|| second_yo.get(i).getPizzasInOrder()[0].equals("Calzone")){
                third_yo.add(second_yo.get(i));

            }

        }
        for (int i = 0; i < second_yo.size(); i++) {
            if(second_yo.get(i).getPizzasInOrder()[0].equals("Proper Pizza")|| second_yo.get(i).getPizzasInOrder()[0].equals("Pineapple & Ham & Cheese")){
                third_yo.add(second_yo.get(i));

            }

        }
        for (int i = 0; i < second_yo.size(); i++) {
            if(second_yo.get(i).getPizzasInOrder()[0].equals("Meat Lover")|| second_yo.get(i).getPizzasInOrder()[0].equals("Vegan Delight")){
                third_yo.add(second_yo.get(i));

            }

        }

        hi();






    }

    @SuppressWarnings("unchecked")
    /**
     * Moves the drone to the specific restaurants
     */
    public void hi() throws IOException {


        Drone drone=new Drone();
        for (int i = 0; i < second_yo.size(); i++) {//Starts moving the drone and makes sure the moves is <2000

            if (third_yo.get(i).getPizzasInOrder()[0].equals("Super Cheese") || third_yo.get(i).getPizzasInOrder()[0].equals("All Shrooms")) {
                System.out.println("moves " + drone.moves);

                if (new LngLat(-3.186874, 55.944494).distanceTo(new LngLat(-3.1838572025299072, 55.94449876875712)) * 2 > (2000 - drone.moves)-50) { // Maes sure the moves are less than 2000
                    outcome=OrderOutcome.ValidButNotDelivered;
                    break;
                }
                outcome=OrderOutcome.Delivered;
                everything_order_outcome.add("Delivered");

                nams++;
                orders_collecting.add(third_yo.get(i).orderNo);

                drone.helper();
                drone.dominos();

            } else if (third_yo.get(i).getPizzasInOrder()[0].equals("Proper Pizza") || third_yo.get(i).getPizzasInOrder()[0].equals("Pineapple & Ham & Cheese")) {
                System.out.println("moves " + drone.moves);

                if (new LngLat(-3.186874, 55.944494).distanceTo(new LngLat(-3.1940174102783203, 55.94390696616939)) * 2 > (2000 - drone.moves)-50) {
                    outcome=OrderOutcome.ValidButNotDelivered;
                    break;
                }
                outcome=OrderOutcome.Delivered;
                everything_order_outcome.add("Delivered");

                nams++;
                orders_collecting.add(third_yo.get(i).orderNo);
                drone.helper();
                drone.Sodeberg();

            } else if (third_yo.get(i).getPizzasInOrder()[0].equals("Margarita") || third_yo.get(i).getPizzasInOrder()[0].equals("Calzone")) {
                System.out.println("moves " + drone.moves);

                if (new LngLat(-3.186874, 55.944494).distanceTo(new LngLat(-3.1912869215011597, 55.945535152517735)) * 2 > (2000 - drone.moves)-50) {
                    outcome=OrderOutcome.ValidButNotDelivered;

                    break;
                }
                outcome=OrderOutcome.Delivered;
                everything_order_outcome.add("Delivered");
                nams++;
                orders_collecting.add(third_yo.get(i).orderNo);
                drone.helper();
                drone.Civerionos();


            }
             else if (third_yo.get(i).getPizzasInOrder()[0].equals("Meat Lover") || second_yo.get(i).getPizzasInOrder()[0].equals("Vegan Delight")) {
                        System.out.println("moves "+ drone.moves);

                        if(new LngLat(-3.186874, 55.944494).distanceTo( new LngLat(-3.202541470527649, 55.943284737579376))*2>(2000- drone.moves)-50){
                            outcome=OrderOutcome.ValidButNotDelivered;

                            break;
                        }
                outcome=OrderOutcome.Delivered;
                everything_order_outcome.add("Delivered");

                            nams++;
                orders_collecting.add(third_yo.get(i).orderNo);
                            drone.helper();
                            drone.restaurantsPlaceSorraLella();
                    }

        }

        System.out.println("orders done "+nams);
        for (int i = 0; i < drone.flightPath.size(); i++) { // Copying these arrayList so it can be used in App.java
             copy.add(drone.flightPath.get(i));

        }


        for (int i = 0; i < drone.positions.size(); i++) {
            copy_positions.add(drone.positions.get(i));

        }
        for (int i = 0; i < drone.positions.size(); i++) {
            copy_positions_next.add(drone.positions.get(i));

        }
        for (int i = 0; i < drone.time.size(); i++) {
            time_yo.add(drone.time.get(i));

        }
        for (int i = 0; i < drone.anglez.size(); i++) {
            anglez_copy.add(drone.anglez.get(i));

        }


    }


    /**
     * This function returns the cost of making the pizza the customer wants and the delivery cost
     * @param restaurant - Has all the restaurant's data, the name of the restaurant, the menu, the price of pizza, and the position of restaurant
     * @param pizza_name - The pizzas the customers wants
     * @return The cost of making the pizza and the delivery cost
     *
     */
    public int getDeliveryCost(Restaurant[] restaurant,String... pizza_name) {

        HashSet<String> restaurants_being_used = new HashSet<>();
        ArrayList<String> pizza_name_arraylist = new ArrayList<>();
        if (pizza_name.length < 1 || pizza_name.length > 4) { // If no pizza's or too many are asked exception is produced

            return -2;

        }
        Collections.addAll(pizza_name_arraylist, pizza_name); //Converts the pizza_name array to an arraylist for ease of use

        int sum = 0;
        ArrayList<String> menu_name = new ArrayList<>();
        ArrayList<Integer> prices = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                menu_name.add(restaurant[i].getMenu()[j].getName()); //Stores all the menus of each restaurant
                prices.add(restaurant[i].getMenu()[j].getPriceInPence()); //Stores all the price of the pizza
            }

        }


        ArrayList<String> restaurants_name = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            restaurants_name.add(restaurant[i].getName()); //Stores the name of the restaurants
            restaurants_name.add(restaurant[i].getName());//Added twice to make it simple to match 1 restaurant to 2 pizzas
        }

        for (int i = 0; i < pizza_name.length; i++) {
            for (int j = 0; j < menu_name.size(); j++) {

                if (pizza_name[i].equals(menu_name.get(j))) {
                    restaurants_being_used.add(restaurants_name.get(j)); //Gets the name of the restaurant of the pizza ordered
                }
            }
        }

        if (!menu_name.containsAll(pizza_name_arraylist)) { // Checks if the pizza exists in a restaurant

            return -1;

        }
        if(restaurants_being_used.size()!=1){
            return -5;

        }
        for (int j = 0; j < pizza_name.length; j++) {
            for (int i = 0; i < menu_name.size(); i++) {
                if (menu_name.get(i).equals(pizza_name[j])) { //If the pizza is in a restaurant calculate the cost of the pizza
                    sum += prices.get(i);
                }
            }
        }
        sum = sum + 100; //Add 100 for the delivery cost

        return sum;

    }

}
