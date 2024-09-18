class Circle implements Shape, Movable{
    private final double radius;
    private final Point centre;

    Circle(double radius, Point centre){
        this.radius = radius;
        this.centre = centre;
    }

    public Circle moveBy(double x, double y){
        // we are just moving the centre point -> point also must be a moveby
        return new Circle(this.radius, this.centre.moveBy(x, y));
    }

    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    public String toString(){
        return "Circle with radius: " + this.radius + " and centre " + this.centre.toString();
    }
}