// needed
import java.awt.Color;

class FilledCircle extends Circle{
    private final Color color;

    FilledCircle(double radius, Color color){
        super(radius); // must always come first (refers to Circle's radius prop)
        this.color = color;
    }

    public String toString(){
        return "Filled " + super.toString() + " and color " + this.color;
    }
}