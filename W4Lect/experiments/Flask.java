// outside the scope of this lecture
class Flask{
    private final double height;
    private final Circle circ;

    Flask(double height, Circle circ){
        this.circ = circ;
        this.height = height;
    }

    public double findVolume(){
        return this.circ.getArea() * this.height;
    }

    public String toString(){
        return "Flask with height: " + this.height;
    }
}