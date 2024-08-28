class distanceBetween {
    record Location(String desc, double x, double y)
    {

    }
    static double distanceBetween(Location p, Location q){
        double dx = q.x() - p.y();
        double dy = q.y() - p.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
    public static void main(String args[]){
        Location p = new Location("School", 1.0, 4.0);
        Location q = new Location("Home", 2.0, 3.0);
        double distance = distanceBetween(p, q);
        System.out.println("Distance: " + String.format("%4f", distance));
    }
}