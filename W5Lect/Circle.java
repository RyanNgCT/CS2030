class Circle implements Shape{
    private final double radius;

    Circle(double radius){
        this.radius = radius;
    }

    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    public String toString(){
        return "Circle with radius: " + this.radius;
    }
}