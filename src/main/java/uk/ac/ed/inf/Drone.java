package uk.ac.ed.inf;


import java.awt.geom.Line2D;
import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;

/**
 * This class deals with the whole movement of the drone
 */
public class Drone {
    ArrayList<LngLat> positions=new ArrayList<>(); // Stores all the current positions of the drone
    ArrayList<LngLat> new_positions=new ArrayList<>();// Stores the next positions of the drone

    ArrayList<LngLat> flightPath=new ArrayList<>(); //Stores all the coordinates of the drone from start to finish
    ArrayList<Long> time=new ArrayList<>(); // Stores the time taken to deliver the pizza for each order

    ArrayList<Double> anglez=new ArrayList<>();// Stores the angle of the drone for each movement
    long calculate=0; // This variable is used to increment the time from the previous time;

    /**
     * For debugging
     * @throws IOException When URL can't be found
     */
    public Drone() throws IOException {
    }


    int moves=0; // Calculates all the moves of the drone and makes sure it's less than 2000

    /**
     *  This function deals with the whole movement of the drone
     * @param destination Coordinate where the drone must go
     * @param current Current position of the drone
     * @throws IOException When can't find URL
     */


    public void direction(LngLat destination,LngLat current) throws IOException {

            while (!current.closeTo(destination)) {


                if ((destination.latitude - current.latitude <= 0.00015 && destination.latitude - current.latitude >= -0.00015) && destination.longitude > current.longitude && getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.E).longitude, current.nextPosition(Direction.E).latitude) && current.nextPosition(Direction.E).longitude <= destination.longitude) {
                    System.out.println("East");
                    anglez.add(0.0);

                    positions.add(new LngLat (current.longitude,current.latitude)); // Adds the position of the drone now
                    long begin = System.nanoTime(); //Starts the time for the movement of the drone

                    current = current.nextPosition(Direction.E); //Moves the drone
                    long end = System.nanoTime(); //Ends the time for the movement of the drone
                    long times = (end-begin)+calculate; // Find the difference of the time
                    time.add(times); //Stores the time in an ArrayList calles time
                    calculate=times; // The time would increment for next movement
                    new_positions.add(new LngLat (current.longitude,current.latitude)); // Stores the next position of the drone
                    moves += 1;
                    System.out.println(current.longitude);
                    System.out.println(current.latitude);
                    flightPath.add(current);


                } else if ((destination.latitude - current.latitude <= 0.00015 && destination.latitude - current.latitude >= -0.00015) && destination.longitude < current.longitude && getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.W).longitude, current.nextPosition(Direction.W).latitude)) {
                    System.out.println("West");
                    anglez.add(180.0);

                    positions.add(new LngLat (current.longitude,current.latitude));
                    long begin = System.nanoTime();

                    current = current.nextPosition(Direction.W);
                    long end = System.nanoTime();
                    long times = (end-begin)+calculate;
                    time.add(times);
                    calculate=times;
                    new_positions.add(new LngLat (current.longitude,current.latitude));
                    moves += 1;
                    System.out.println(current.longitude);
                    System.out.println(current.latitude);
                    flightPath.add(current);


                } else if ((destination.longitude - current.longitude <= 0.00015 && destination.longitude - current.longitude >= -0.00015) && destination.latitude > current.latitude && getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.N).longitude, current.nextPosition(Direction.N).latitude)) {
                    System.out.println("North");
                    anglez.add(90.0);

                    positions.add(new LngLat (current.longitude,current.latitude));
                    long begin = System.nanoTime();


                    current = current.nextPosition(Direction.N);
                    long end = System.nanoTime();
                    long times = (end-begin)+calculate;
                    time.add(times);
                    calculate=times;
                    new_positions.add(new LngLat (current.longitude,current.latitude));
                    moves += 1;
                    System.out.println(current.longitude);
                    System.out.println(current.latitude);

                    flightPath.add(current);
                }
                else if (destination.longitude > current.longitude && destination.latitude > current.latitude) {
                    System.out.println("ENE");
                    if (!getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.ENE).longitude, current.nextPosition(Direction.ENE).latitude)) {
                        //Checks if the nextPostion of the drone would be close to the nofly zone and if it is change the position of the drone
                        current.latitude = current.latitude - 0.00015;
                        moves += 1;
                    } else {
                        positions.add(new LngLat (current.longitude,current.latitude));
                        anglez.add(22.5);

                        long begin = System.nanoTime();
                        current = current.nextPosition(Direction.ENE);
                        long end = System.nanoTime();
                        long times = (end-begin)+calculate;
                        time.add(times);
                        calculate=times;
                        new_positions.add(new LngLat (current.longitude,current.latitude));
                        moves += 1;
                        System.out.println(current.longitude);
                        System.out.println(current.latitude);
                        flightPath.add(current);
                    }
                } else if (destination.longitude > current.longitude && destination.latitude < current.latitude) {
                    System.out.println("ESE");
                    if (!getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.ESE).longitude, current.nextPosition(Direction.ESE).latitude)) {
                        current.latitude = current.latitude + 0.00015;
                        moves += 1;
                    } else {
                        positions.add(new LngLat (current.longitude,current.latitude));
                        anglez.add(337.5);
                        long begin = System.nanoTime();
                        current = current.nextPosition(Direction.ESE);
                        long end = System.nanoTime();
                        long times = (end-begin)+calculate;
                        time.add(times);
                        calculate=times;
                        new_positions.add(new LngLat (current.longitude,current.latitude));
                        moves += 1;
                        System.out.println(current.longitude);
                        System.out.println(current.latitude);
                        flightPath.add(current);
                    }
                } else if (destination.longitude < current.longitude && destination.latitude > current.latitude) {
                    System.out.println("WNW");


                    if (!getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.WNW).longitude, current.nextPosition(Direction.WNW).latitude)) {

                        current.latitude = current.latitude - 0.00015;
                        moves += 1;

                    } else {
                        anglez.add(157.5);
                        positions.add(new LngLat (current.longitude,current.latitude));
                        long begin = System.nanoTime();
                        current = current.nextPosition(Direction.WNW);
                        long end = System.nanoTime();
                        long times = (end-begin)+calculate;
                        time.add(times);
                        calculate=times;
                        new_positions.add(new LngLat (current.longitude,current.latitude));
                        moves += 1;
                        System.out.println(current.longitude);
                        System.out.println(current.latitude);
                        flightPath.add(current);
                    }
                } else if (destination.longitude < current.longitude && destination.latitude < current.latitude) {
                    System.out.println("WSW");
                    if (!getNoFly(current.longitude, current.latitude, current.nextPosition(Direction.WSW).longitude, current.nextPosition(Direction.WSW).latitude)) {
                        current.latitude = current.latitude + 0.00015;
                        moves += 1;

                    } else {
                        positions.add(new LngLat (current.longitude,current.latitude));
                        anglez.add(202.5);
                        long begin = System.nanoTime();

                        current = current.nextPosition(Direction.WSW);
                        long end = System.nanoTime();
                        long times = (end-begin)+calculate;
                        time.add(times);
                        calculate=times;
                        new_positions.add(new LngLat (current.longitude,current.latitude));
                        moves += 1;
                        System.out.println(current.longitude);
                        System.out.println(current.latitude);
                        flightPath.add(current);
                    }

                }

            }


            moves += 1; //The drone is hovering
            finalPositions.add(current.longitude); //These coordinates are used to start the drone for the next order
            finalPositions.add(current.latitude);
            System.out.println(moves);
        }






    ArrayList<Double> finalPositions=new ArrayList<>();
    ArrayList<Double> getNoFly_ArrayList=new ArrayList<>(); //These are all arrayLists which stores the coordinates for the noflyzones
    ArrayList<Double> getGetNoFly_ArrayList2=new ArrayList<>();
    ArrayList<Double> getGetGetNoFly_ArrayList3=new ArrayList<>();
    ArrayList<Double> getGetGetGetNoFly_ArrayList4=new ArrayList<>();
    ArrayList<Line2D> lines=new ArrayList<>();// These lines are used to make sure the drone and the noflyzones don't intersect
    ArrayList<Line2D> lines2=new ArrayList<>();
    ArrayList<Line2D> lines3=new ArrayList<>();
    ArrayList<Line2D> lines4=new ArrayList<>();
    NoFlyZones[] nofly=NoFlyZones.getNoFlyFromRestServer(new URL("https://ilp-rest.azurewebsites.net/noFlyZones")); //Gets data pf the NoFlyZones

    /**
     * This helper function basically has all the coordinates of the noflyzones
     */
    public void helper() {

        for (int i = 0; i < nofly[0].getCoordinates().length; i++) {
            for (int j = 0; j <2; j++) {
                getNoFly_ArrayList.add(Double.parseDouble(nofly[0].getCoordinates()[i][j])); //Stores the coordinates in this arrayList

            }
        }
        for (int i = 0; i < nofly[1].getCoordinates().length; i++) {
            for (int j = 0; j <2; j++) {
                getGetNoFly_ArrayList2.add(Double.parseDouble(nofly[1].getCoordinates()[i][j]));

            }

        }
        for (int i = 0; i < nofly[2].getCoordinates().length; i++) {
            for (int j = 0; j <2; j++) {
                getGetGetNoFly_ArrayList3.add(Double.parseDouble(nofly[2].getCoordinates()[i][j]));

            }


        }
        for (int i = 0; i < nofly[3].getCoordinates().length; i++) {
            for (int j = 0; j < 2; j++) {
                getGetGetGetNoFly_ArrayList4.add(Double.parseDouble(nofly[3].getCoordinates()[i][j]));

            }

        }
        for (int i = 0; i < getGetNoFly_ArrayList2.size()-3; i+=2) {
            lines2.add(new Line2D.Double(getGetNoFly_ArrayList2.get(i),getGetNoFly_ArrayList2.get(i+1),getGetNoFly_ArrayList2.get(i+2),getGetNoFly_ArrayList2.get(i+3))); //Draw lines of all the points so it would be a proper shape


        }
        for (int i = 0; i < getGetGetNoFly_ArrayList3.size()-3; i+=2) {
            lines3.add(new Line2D.Double(getGetGetNoFly_ArrayList3.get(i),getGetGetNoFly_ArrayList3.get(i+1),getGetGetNoFly_ArrayList3.get(i+2),getGetGetNoFly_ArrayList3.get(i+3)));


        }
        for (int i = 0; i < getGetGetGetNoFly_ArrayList4.size()-3; i+=2) {
            lines4.add(new Line2D.Double(getGetGetGetNoFly_ArrayList4.get(i),getGetGetGetNoFly_ArrayList4.get(i+1),getGetGetGetNoFly_ArrayList4.get(i+2),getGetGetGetNoFly_ArrayList4.get(i+3)));


        }
        for (int i = 0; i < getNoFly_ArrayList.size()-3; i+=2) {

            lines.add(new Line2D.Double(getNoFly_ArrayList.get(i),getNoFly_ArrayList.get(i+1),getNoFly_ArrayList.get(i+2),getNoFly_ArrayList.get(i+3)));
        }


    }

    /**
     *
     * @param x0 Coordinates for the drone position now
     * @param y0 Coordinates for the drone position now
     * @param x1 Coordinates for the next drone position
     * @param y1 Coordinates for the next drone position
     * @return False if the drone is not about to intersect the noFly zone else true
     */

    public boolean getNoFly(Double x0, Double y0, Double x1, Double y1) {
        Line2D line2D1=new Line2D.Double(x0,y0,x1,y1);

        for (int i = 0; i < lines.size(); i++) {
            if(line2D1.intersectsLine(lines.get(i))){ // If the drone position is about to intersect the nofly zone
                System.out.println("about to intersect");
                return false;

            }

        }
        for (int i = 0; i < lines2.size(); i++) {
            if(line2D1.intersectsLine(lines2.get(i))){
                System.out.println("about to intersect");
                return false;
            }

        }
        for (int i = 0; i < lines3.size(); i++) {
            if(line2D1.intersectsLine(lines3.get(i))){
                System.out.println("about to intersect");
                return false;
            }

        }
        for (int i = 0; i < lines4.size(); i++) {
            if(line2D1.intersectsLine(lines4.get(i))){
                System.out.println("about to intersect");
                return false;
            }

        }
        return true;

    }

    int counter=0; // This counter is used to see the position where the drone is about to start
    boolean x=true; //Checks if it is from AppleTon tower or not

    /**
     * Delivers Pizza to this Restaurant and back
     *
     * @throws IOException  when the URL doesn't work
     */
    public void restaurantsPlaceSorraLella() throws IOException {


        if(x) {
            finalPositions.add(-3.186874);
            finalPositions.add(55.944494);
            x=false;
        }
        direction(new LngLat(-3.202541470527649, 55.943284737579376),new LngLat(finalPositions.get(counter),finalPositions.get(counter+1))); //Moves the drone to this position
        direction(new LngLat(-3.186874, 55.944494), new LngLat(finalPositions.get(counter+2),finalPositions.get(counter+3) ));
        counter+=4;
        getNoFly_ArrayList.clear(); //Clearing all the ArrayList so it won't increase in length as the orders increase
        getGetNoFly_ArrayList2.clear();
        getGetGetNoFly_ArrayList3.clear();
        getGetGetGetNoFly_ArrayList4.clear();
        lines.clear();
        lines2.clear();
        lines3.clear();
        lines4.clear();

    }

    /**
     * Delivers Pizza to this Restaurant and back
     * @throws IOException when the URL doesn't work
     */
    public void Sodeberg() throws IOException {
        if(x) {
            finalPositions.add(-3.186874);
            finalPositions.add(55.944494);
            x=false;
        }
        direction(new LngLat( -3.1940174102783203, 55.94390696616939),new LngLat(finalPositions.get(counter),finalPositions.get(counter+1)));
        direction(new LngLat(-3.186874, 55.944494), new LngLat(finalPositions.get(counter+2),finalPositions.get(counter+3) ));
        counter+=4;
        getNoFly_ArrayList.clear();
        getGetNoFly_ArrayList2.clear();
        getGetGetNoFly_ArrayList3.clear();
        getGetGetGetNoFly_ArrayList4.clear();
        lines.clear();
        lines2.clear();
        lines3.clear();
        lines4.clear();

    }

    /**
     * Delivers Pizza to this Restaurant and back
     * @throws IOException when the URL doesn't work
     */
    public void Civerionos() throws IOException {
        if(x) {
            finalPositions.add(-3.186874);
            finalPositions.add(55.944494);
            x=false;
        }
        direction(new LngLat( -3.1912869215011597, 55.945535152517735),new LngLat(finalPositions.get(counter),finalPositions.get(counter+1)));
        direction(new LngLat(-3.186874, 55.944494), new LngLat(finalPositions.get(counter+2),finalPositions.get(counter+3) ));
        counter+=4;
        getNoFly_ArrayList.clear();
        getGetNoFly_ArrayList2.clear();
        getGetGetNoFly_ArrayList3.clear();
        getGetGetGetNoFly_ArrayList4.clear();
        lines.clear();
        lines2.clear();
        lines3.clear();
        lines4.clear();

    }

    /**
     * Delivers Pizza to this Restaurant and back
     * @throws IOException when the URL doesn't work
     */
    public void dominos() throws IOException {
        if(x) {
            finalPositions.add(-3.186874);
            finalPositions.add(55.944494);
            x=false;
        }
        direction(new LngLat(  -3.1838572025299072, 55.94449876875712),new LngLat(finalPositions.get(counter),finalPositions.get(counter+1)));
        direction(new LngLat(-3.186874, 55.944494), new LngLat(finalPositions.get(counter+2),finalPositions.get(counter+3) ));
        counter+=4;
        getNoFly_ArrayList.clear();
        getGetNoFly_ArrayList2.clear();
        getGetGetNoFly_ArrayList3.clear();
        getGetGetGetNoFly_ArrayList4.clear();
        lines.clear();
        lines2.clear();
        lines3.clear();
        lines4.clear();

    }

}
