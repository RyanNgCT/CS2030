import java.util.Optional;

class CircleOpt {
    private final Optional<Point> centre;
    private final double radius;

    // overloaded constructors
    CircleOpt(Point centre, double radius){
        this.centre = Optional.of(centre);
        this.radius = radius;
    }

    CircleOpt(double radius){
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

    CircleOpt scale(double factor){
        return this.centre.map(c -> new CircleOpt(c, this.radius * factor)).orElse(new CircleOpt(this.radius * factor));
    }
    boolean isOverlap(CircleOpt otherCircle){
        return this.centre
            .map(
                c -> otherCircle.centre
                    .map(
                        d -> c.distanceTo(d) < (this.radius + otherCircle.radius)
                    )
                    .orElse(false)
                )
            .orElse(false);
    }

    public String toString(){
        // return "Circle at " + this.centre + " with radius " + this.radius;

        return this.centre
            .map(c -> "Circle at "+ c +  " with radius " + this.radius)
            .orElse("Circle with radius " + this.radius);
    }
}