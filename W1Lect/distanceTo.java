public class distanceTo{
	public static void main(String[] args)
	{
		Location myHome = new Location("This is my home!", 0.0, 1.02);
		Location theShop = new Location("Ship", 10.56, 25.82);	
		double homeShopDist = myHome.distanceTo(theShop);
		System.out.println("The distance from my home to the shop is: " + String.format("%.3f", homeShopDist));
	}

	record Location(String desc, double lat, double lng){
		double distanceTo(Location other)
		{
			double dY = this.lat() - other.lat();
		    double dX = this.lng() - other.lng();
            return (dX * dX + dY * dY);
		}
	}
}
