class SolidCuboid extends Cuboid implements Solid {
    private final double density;
    private final SolidImpl simpl;

    SolidCuboid(double height, double width, double length, double density) {
        super(height, width, length);
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
