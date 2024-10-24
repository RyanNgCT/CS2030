class Sphere implements Shape3D {
    private final double radius;
    private static final double constant = 4.0 / 3.0;

    Sphere(double radius) {
        this.radius = radius;
    }

    public double volume() {
        return constant * Math.PI * this.radius * this.radius * this.radius;
    }

    public String toString() {
        return "sphere [" + String.format("%.2f", this.radius) + "]";
    }
}
