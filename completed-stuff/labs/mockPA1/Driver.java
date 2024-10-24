import java.util.List;

abstract class Driver {
    private final String lPlate;
    private final int wtTime;

    Driver(String lPlate, int wtTime) {
        this.lPlate = lPlate;
        this.wtTime = wtTime;
    }

    abstract List<Pair<Double, String>> calcFare(Request rq);

    public int cmpWT(Driver oth) {
        if (this.wtTime < oth.wtTime) {
            return -1;
        } else if (this.wtTime > oth.wtTime) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return this.lPlate + " (" + this.wtTime + " mins away)";
    }
}
