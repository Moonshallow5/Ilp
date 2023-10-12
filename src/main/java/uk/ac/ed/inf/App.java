package uk.ac.ed.inf;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.HashMap;

/**
 * This class basically starts the whole program
 *
 */
public class App 
{
    /**
     *
     * @param args Contains the date and the URL for the REST server
     * @throws IOException  When URL can't be reached
     * @throws InvalidPizzaCombinationException //When the pizza are wrong
     */

    public static void main(String[] args ) throws IOException, InvalidPizzaCombinationException {

        String po=args[0]; // Stores the date for the drone to fly
        var yo=Order.getOrdersFromRestServer((new URL(args[1]))); // Gets data from the REST server for the specific date

        yo[0].orderOutcome(po);// This basically starts everything
        JSONArray employeeList = new JSONArray();
        JSONArray json_lists=new JSONArray();


        int counter=0;


        for (int i = 0; i < yo.length; i++) {
            HashMap<String,Object> ss=new HashMap<>();


             if(i>=yo[0].everything_order_outcome.size()){
                ss.put("orderNo", yo[0].third_yo.get(counter).getOrderNo()); // Makes the json file with these parameters
                 ss.put("outcome","ValidButNotDelivered");
                 ss.put("costInPence",yo[0].third_yo.get(counter).getPriceTotalInPence());

                counter++;
            }

            else if(yo[0].everything_order_outcome.get(i).equals("Delivered")) {
                ss.put("orderNo", yo[0].third_yo.get(counter).getOrderNo());
                 ss.put("outcome","Delivered");
                ss.put("costInPence",yo[0].third_yo.get(counter).getPriceTotalInPence());

                counter++;
            }
            else if(!yo[0].everything_order_outcome.get(i).equals("Delivered") && !yo[0].everything_order_outcome.get(i).equals("ValidButNotDelivered")) {
                ss.put("orderNo", yo[i].getOrderNo());
                ss.put("outcome",yo[0].everything_order_outcome.get(i));
                ss.put("costInPence",yo[i].getPriceTotalInPence());

            }

            JSONObject employeeDetails = new JSONObject(ss); // Makes a new JSONObject after each iteration
            employeeList.add(employeeDetails);// Stores everything in a JSON array

        }

        for (int i = 0; i < yo[0].orders_collecting.size(); i++) {

            for (int j = 0; j < yo[0].copy_positions.size(); j++) {
                HashMap<String,Object> deep=new HashMap<>();
                deep.put("orderNo",yo[0].third_yo.get(i).orderNo); // Makes the json file with these parameters
                deep.put("fromLongitude",yo[0].copy_positions.get(j).longitude);
                deep.put("fromLatitude",yo[0].copy_positions.get(j).latitude);
                deep.put("angle",yo[0].anglez_copy.get(j));
                deep.put("toLongitude",yo[0].copy_positions_next.get(j).longitude);
                deep.put("toLatitude",yo[0].copy_positions_next.get(j).latitude);
                deep.put("ticksSinceStartOfCalculation",yo[0].time_yo.get(j));

                JSONObject urmom = new JSONObject(deep);// Makes a new JSONObject after each iteration
                json_lists.add(urmom);// Stores everything in a JSON array
            }

        }


        try (FileWriter file = new FileWriter("deliveries-"+po+".json")) {// These functions basically write the proper json files
            //We can write any JSONArray or JSONObject instance to the file
            file.write(employeeList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter file = new FileWriter("flightpath-"+po+".json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json_lists.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }



        String finals=Map.generateGeoJson(yo[0].copy);// Makes a geojson file based on the total flightpath of the drone
        PrintWriter output=new PrintWriter("drone-"+po+".geojson");
        output.println(finals);
        output.close();



    }

}
