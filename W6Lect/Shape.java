abstract class Shape implements Comparable<Shape> {
    public abstract double getArea();

    public int compareTo(Shape other) {
        return (int) Math.signum(this.getArea() - other.getArea());
    }
}

class Circle extends Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    public double getArea() { 
        return Math.PI * this.radius * this.radius;
    }

    public String toString() {
        return "Circle with radius " + this.radius;
    }
}

class Rectangle extends Shape {
    private final double length;
    private final double width;

    Rectangle(double length, double width) {
        this.length = length; 
        this.width = width;
    }

    public double getArea() {
        return this.length * this.width; 
    }

    public String toString() {
        return "Rectangle " + this.length + " by " + this.width;
    }
}
