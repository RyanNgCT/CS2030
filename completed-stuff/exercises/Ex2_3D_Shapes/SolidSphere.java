class SolidSphere extends Sphere implements Solid {
    private final double density;
    private final SolidImpl simpl;

    SolidSphere(double radius, double density) {
        super(radius);
        this.density = density;
        this.simpl = new SolidImpl(this, density);
    }

    public double mass() {
        return this.simpl.mass();
    }

    @Override
    public String toString() {
        return "solid-" + super.toString() + " with a mass of " +
            String.format("%.2f", this.simpl.mass());
    }
}
