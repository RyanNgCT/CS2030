class distanceBetween {
    record Location(String desc, double x, double y){
    }
    double distanceBetween(Location p, Location q){
            double dx = q.x() - p.y();
            double dy = q.y() - p.y();
            return Math.sqrt(dx * dx + dy * dy);
    }
    public static void Main(String args[]){
        Location p = new Location("School", 1.0, 2.0);
        Location q = new Location("Home", 2.0, 3.0);
        // double dist = distanceBetween(p, q);
        // System.out.println("Distance: "+ dist);

        // Create an instance of the class
        double distance = new distanceBetween(p, q);
        System.out.println("Distance: " + distance);
    }
}