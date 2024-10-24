import java.util.List;

class NormalCab extends Driver {

    NormalCab(String lPlate, int wtTime) {
        super(lPlate, wtTime);
    }

    //NormalCab(String lPlate, int wtTime, String srv) {
    //    super(lPlate, wtTime);
    //    this.srv = srv;
    //}

    // @Override
    // public Pair<Double, String> calcFare(Request rq) {
    //     double justRide = (double)rq.computeFare(new JustRide()) / 100;
    //     double tACab = (double)rq.computeFare(new TakeACab()) / 100;

    //     // return the cheaper option
    //     if (justRide < tACab) {
    //         return new Pair<>(justRide, "JustRide");
    //     }
    //     return new Pair<>(tACab, "TakeACab");
    // }

    @Override
    public List<Pair<Double, String>> calcFare(Request rq) {
        double justRide = (double)rq.computeFare(new JustRide()) / 100;
        double tACab = (double)rq.computeFare(new TakeACab()) / 100;

        return List.of(new Pair<>(justRide, "JustRide"),
                       new Pair<>(tACab, "TakeACab"));
    }

    @Override
    public String toString() {
        return super.toString() + " NormalCab";
    }
}
