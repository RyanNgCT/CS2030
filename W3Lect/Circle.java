import java.util.Optional;
import java.util.Comparator;

class Circle implements Comparable<Circle>{
    private final Point centre;
    private final double radius;

    // overloaded constructors
    Circle(Point centre, double radius){
        this.centre = centre;
        this.radius = radius;
    }

    private double getRadius(){
        return this.radius;
    }

    boolean contains(Point pt){
        return this.centre.distanceTo(pt) < this.radius;
    }

    Circle scale(double factor){
       return new Circle(this.centre, this.radius * factor);
    }

    boolean isOverlap(Circle otherCircle){
        return this.centre.distanceTo(otherCircle.centre) < (this.radius + otherCircle.radius);
    }

    // quite overkill but
    // determine which circle is the bigger one by using the radius for comparison
    public int isBigger(Circle other) {
        Comparator<Circle> basis = (x, y) -> Double.compare(x.getRadius(), y.getRadius());
        return basis.compare(this, other);
    }

    public int compareTo(Circle other) {
        if (this.radius > other.radius) {
            return 1;
        }
        else if (this.radius < other.radius) {
            return -1;
        }
        return 0;
    }

    public String toString(){
        return "Circle at " + this.centre + " with radius " + this.radius;
    }
}