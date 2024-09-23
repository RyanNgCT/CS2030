import java.awt.Color;
abstract class FilledShape implements Shape{
    private final Color color;

    FilledShape(Color color){
        this.color = color;
    }

    public abstract double getArea();

    Color getColor(){
        return this.color;
    }

    @Override
    public String toString(){
        return "Filled Shape with " + this.color; 
    }
}