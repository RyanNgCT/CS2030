class SolidImpl implements Shape3D {
    private final Shape3D s3D;
    private final double density;

    SolidImpl(Shape3D s3D, double density) {
        this.s3D = s3D;
        this.density = density;
    }

    public double volume() {
        return this.s3D.volume();
    }

    public double mass() {
        // return this.s3D.mass();
        return this.s3D.volume() * this.density;
    }

    public String toString() {
        return "SolidImpl";
    }
}
