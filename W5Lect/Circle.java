class Circle implements Shape {
    private final double radius; // change to protected to let children access prop in constructor

    Circle(double radius){
        this.radius = radius;
    }

    // re-produces the circle ("makes a copy") -> for FilledCircle to use
    protected Circle(Circle c){
        //this.radius = c.radius;
        
        // more succinct way than the above
        this(c.radius);
    }

    // it is in the core, no need to redefine int in the child class
    public double getArea(){
        return Math.PI * this.radius * this.radius;
    }

    // overloaded equals ❌
    // boolean equals(Circle c){
    //     return this.radius == c.radius;
    // }

    @Override
    public boolean equals(Object obj){
        // trivial check -> for the same object
        if (obj == this){
            return true;
        }

        // type check
        if (obj instanceof Circle circle){
            return this.radius == circle.radius;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Circle with radius: " + this.radius;
    }
}