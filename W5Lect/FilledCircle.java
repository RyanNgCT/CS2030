// needed
import java.awt.Color;

class FilledCircle extends Circle { // implements Shape
    // private final double radius;
    private final Color color;

    FilledCircle(double radius, Color color){
        super(radius); // must always come first (refers to Circle's radius prop)
        this.color = color;
    }

    // overloaded constructor method to enable parent to manage radius creation
    FilledCircle(Circle c, Color color){
        super(c);
        this.color = color;
    }

    // change the colour of the FilledCircle, with the same radius defined
    public FilledCircle fillColor(Color color){
        // better to use super.radius then this.radius
        // return new FilledCircle(super.radius, color);

        return new FilledCircle(this, color); // current object is a child of Circle
    }

    public FilledCircle changeRadius(double rad){
        return new FilledCircle(rad, this.color);
    }

    @Override
    public String toString(){
        return "Filled " + super.toString() + " and color " + this.color;
    }
}