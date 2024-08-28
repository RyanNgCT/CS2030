import java.util.*;

public class distanceAdder{
	public static void main(String[] args){
		Location ref = new Location("My Home", 0.0, 0.0);
		Location l1 = new Location("Test", 0.9, 1.0);	
		Location l2 = new Location("Site", 1.5, 2.4);
		List<Location> locations = List.of(ref, l1, l2);
		double res = sumDistances(ref, locations);
		System.out.println(res);
	}

	record Location(String desc, double lat, double lng){
		double distanceTo(Location other)
		{
			double dY = this.lat() - other.lat();
		    double dX = this.lng() - other.lng();
            return (dX * dX + dY * dY);
   		}
   	}			
	public static double sumDistances(Location ref, List<Location> locationList){
		return locationList.stream().map(location -> ref.distanceTo(location)).sum();
	}			              
}
