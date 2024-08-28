// the "Blueprint"
class Point{

    // variables or properties
    private final double x;
    private final double y;

    // Constructor -> how to create the point itself
    Point(double x, double y){
        // this keyword -> referring to the properties of the instance of the class
        this.x = x;
        this.y = y;
    }
    
    // Methods
    double distanceTo(Point other){
        double dx = this.x - other.x;
        double dy = this.y - other.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
}