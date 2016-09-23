package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	
	public void setup() {
		// setting up PAppler
		size(1350,700, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 1300, 650);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		HashMap<Integer, String> airport_country = new HashMap<Integer,String>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			airport_country.put(Integer.parseInt(feature.getId()), feature.getStringProperty("country"));
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			// get source and destination airportIds
		
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			//String country_name = new String();
			String dest_country_name =  airport_country.get(dest);
			dest_country_name = dest_country_name.replace("\"", "");
			boolean destIsIndia = false;
			if(dest_country_name.equals("India")){
				destIsIndia=true;
			}
				
			
			String sourc_country_name = airport_country.get(source);
			sourc_country_name = sourc_country_name.replace("\"", "");
			boolean sourceIsIndia = false;
			if(sourc_country_name.equals("India")){
				sourceIsIndia = true;
			}
			// get locations for airports on route
			if((airports.containsKey(source) && airports.containsKey(dest)&& destIsIndia) || (airports.containsKey(source) && airports.containsKey(dest)&& sourceIsIndia )) {

				route.addLocation(airports.get(source));
				
				route.addLocation(airports.get(dest));
				//System.out.println(airports.get(4));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
	}
	
	public void draw() {
		background(0);
		map.draw();
		
	}
	

}
