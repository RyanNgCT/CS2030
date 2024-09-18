class Rectangle implements Shape {
    private final double length;
    private final double width;

    Rectangle(double length, double width){
        this.length = length;
        this.width = width;
    }

    public double getArea(){
        return this.length * this.width;
    }

    public String toString(){
        return "Rectangle of dimensions " + this.length + " by " + this.width;
    }
}