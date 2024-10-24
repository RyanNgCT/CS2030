import java.util.Comparator;
import java.util.List;

class Booking implements Comparable<Booking> {
    private final Driver drv;
    private final Request rq;
    private final double fare;
    private final String svcType;

    Booking(Driver drv, Request rq) {
        Pair<Double, String> lowestPair =
            drv.calcFare(rq)
                .stream()
                .reduce((x, y) -> x.t().compareTo(y.t()) < 0 ? x : y)
                .get();

        this.drv = drv;
        this.rq = rq;
        this.fare = lowestPair.t();
        this.svcType = lowestPair.u();
    }

    Booking(Driver drv, Request rq, double fare, String svcType) {
        this.drv = drv;
        this.rq = rq;
        this.fare = fare;
        this.svcType = svcType;
    }

    @Override
    public int compareTo(Booking other) {
        if (this.fare < other.fare) {
            return -1;
        } else if (this.fare > other.fare) {
            return 1;
        } else {
            return this.drv.cmpWT(other.drv);
        }
    }

    public List<Booking> potentialBookings() {
        return this.drv.calcFare(this.rq)
            .stream()
            .map(pair -> new Booking(this.drv, this.rq, pair.t(), pair.u()))
            .toList();
    }

    @Override
    public String toString() {
        return "$" + String.format("%.2f", this.fare) + " using " +
            this.drv.toString() + " (" + this.svcType + ")";
    }
}
