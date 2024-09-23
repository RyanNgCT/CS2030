import java.awt.Color;

class FilledCircle extends FilledShape{
    private final double radius;

    FilledCircle(double radius, Color color){
        super(color);
        this.radius = radius;
    }

    public double getArea(){
        return Math.PI * radius * radius;
    }

    @Override
    public String toString(){
        return super.toString() + " with radius of " + this.radius;
    }
}