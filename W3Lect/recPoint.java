record Point(double x, double y){
    double distanceTo(Point other){
        double dx = this.x() - other.x();
        double dy = this.y() - other.y();
        return Math.sqrt(dx * dx + dy * dy);
    }
}