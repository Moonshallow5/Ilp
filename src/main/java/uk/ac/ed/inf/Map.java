package uk.ac.ed.inf;

import java.util.ArrayList;
import java.util.List;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;

/**
 * Generates the geojson for the flightpath of the drone
 */
public class Map {
    /**
     *
     * @param path Takes all the data of the drone's movement
     * @return The json string
     */
    public static String generateGeoJson(List<LngLat> path){
        var features = new ArrayList<Feature>();
        var points=new ArrayList<Point>();
        for (int i = 0; i < path.size(); i++) {
            var longitude=path.get(i).longitude;
            var latitude=path.get(i).latitude;
            var point=Point.fromLngLat(longitude,latitude);
            points.add(point);
        }
        var lineString=LineString.fromLngLats(points);
        var droneFlightPathFeature=Feature.fromGeometry(lineString);
        features.add(droneFlightPathFeature);
        var featureCollection=FeatureCollection.fromFeatures(features);
        return featureCollection.toJson();

    }
}
