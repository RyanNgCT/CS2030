// the "Blueprint" of the object
class Point{

    // define the variables or properties
    private final double x;
    private final double y;

    // Constructor -> how to create the point itself
    Point(double x, double y){

        // using the "this" keyword 
        // -> referring to the properties of the instance of the class / current object
        this.x = x;
        this.y = y;
    }

    // compute distance from itself to another point
    double distanceTo(Point other){
        double dx = this.x - other.x; // use the properties themselves rather than methods
        double dy = this.y - other.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    public String toString(){
        // prints (a, b)
        return "(" + this.x + ", " + this.y + ")";
    }
}