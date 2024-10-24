class Point {
    private final double x;
    private final double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    private double getX() {
        return this.x;
    }

    private double getY() {
        return this.y;
    }

    public double distanceTo(Point otherPt) {
        double dx = this.getX() - otherPt.getX();
        double dy = this.getY() - otherPt.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public Point midPoint(Point other) {
        double mpCoordX = (this.getX() + other.getX()) / 2.0;
        double mpCoordY = (this.getY() + other.getY()) / 2.0;
        return new Point(mpCoordX, mpCoordY);
    }

    public double angleTo(Point otherPt) {
        double dx = otherPt.getX() - this.getX();
        double dy = otherPt.getY() - this.getY();
        return Math.atan2(dy, dx);
    }

    public Point moveTo(double angle, double dist) {
        return new Point(this.getX() + dist * Math.cos(angle),
                         this.getY() + dist * Math.sin(angle));
    }

    @Override
    public String toString() {
        return "point (" + String.format("%.3f", this.x) + ", " +
            String.format("%.3f", this.y) + ")";
    }
}
