import java.util.List;

class PrivateCar extends Driver {
    PrivateCar(String lPlate, int wtTime) {
        super(lPlate, wtTime);
    }

    @Override
    public List<Pair<Double, String>> calcFare(Request rq) {
        double justRide = (double)rq.computeFare(new JustRide()) / 100;
        double sARide = (double)rq.computeFare(new ShareARide()) / 100;

        // return the cheaper option
        return List.of(new Pair<>(justRide, "JustRide"),
                       new Pair<>(sARide, "ShareARide"));
    }

    @Override
    public String toString() {
        return super.toString() + " PrivateCar";
    }
}
