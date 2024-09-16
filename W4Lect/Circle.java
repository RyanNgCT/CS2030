class Circle implements Shape, Moveable{
    private final double radius;

    Circle(double radius){
        this.radius = radius;
    }

    public Circle moveBy(double x, double y){
        return new Circle(centre.moveBy(x, y), this.radius);
    }

    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    public String toString(){
        return "Circle with radius: " + this.radius;
    }
}