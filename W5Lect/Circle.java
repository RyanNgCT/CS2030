class Circle implements Shape{
    protected final double radius; // change to protected to let children access prop in constructor

    Circle(double radius){
        this.radius = radius;
    }

    // re-produces the circle ("copy")
    Circle(Circle c){
        //this.radius = c.radius;

        // more succinct way
        this(c.radius);
    }

    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }


    @Override
    public String toString(){
        return "Circle with radius: " + this.radius;
    }
}