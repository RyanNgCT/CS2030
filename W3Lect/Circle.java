import java.util.Optional;

class Circle {
    private final Optional<Point> centre;
    private final double radius;

    // overloaded constructors
    Circle(Point centre, double radius){
        this.centre = Optional.of(centre);
        this.radius = radius;
    }

    Circle(double radius){
        this.radius = radius;

        // this.centre = null ???
        this.centre = Optional.empty();
    }


    boolean contains(Point pt){
        // this.centre is an optional point (use map to obtain another optional)
        return this.centre.map(c -> c.distanceTo(pt) < this.radius).orElse(false);
    }

    // void scale(double factor){
    //     this.radius = this.radius * factor;
    // }

    Circle scale(double factor){
        return this.centre.map(c -> new Circle(c, this.radius * factor)).orElse(new Circle(this.radius * factor));
    }
    // boolean isOverlap(Circle otherCircle){
    //     return this.centre
    //         .map(c -> otherCircle.centre.map(d -> this.distanceTo(d) < c + d))
    //         .orElse(false);
    // }

    public String toString(){
        return "Circle at " + this.centre + " with radius " + this.radius;
    }
}