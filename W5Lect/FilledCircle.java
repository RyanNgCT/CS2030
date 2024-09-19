// needed
import java.awt.Color;

class FilledCircle extends Circle { // implements Shape
    // private final double radius;
    private final Color color;

    FilledCircle(double radius, Color color){
        super(radius); // must always come first (refers to Circle's radius prop)
        this.color = color;
    }

    // change the colour of the FilledCircle, with the same radius defined
    public FilledCircle fillColor(Color color){
        // better to use super.radius then this.radius
        return new FilledCircle(super.radius, color);
    }

    public FilledCircle changeRadius(double rad){
        return new FilledCircle(rad, this.color);
    }

    @Override
    public String toString(){
        return "Filled " + super.toString() + " and color " + this.color;
    }
}