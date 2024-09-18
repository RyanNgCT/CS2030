import java.util.Optional;

class Circle {
    private final Point centre;
    private final double radius;

    // overloaded constructors
    Circle(Point centre, double radius){
        this.centre = centre;
        this.radius = radius;
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

    public String toString(){
        return "Circle at " + this.centre + " with radius " + this.radius;
    }
}