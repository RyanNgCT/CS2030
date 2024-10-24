import java.util.List;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final List<Pair<Integer, Pair<Double, Double>>> arrivals;

    Simulator(int numOfServers, int numOfCustomers,
              List<Pair<Integer, Pair<Double, Double>>> arrivals) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
    }

    State run() {
        PQ<Event> pq = new PQ<Event>();

        for (Pair<Integer, Pair<Double, Double>> arr : arrivals) {
            int custId = arr.t();
            double arrTime = arr.u().t();
            double srvTime = arr.u().u();

            pq = pq.add(new ArriveEvent(new Customer(custId, arrTime, srvTime),
                                        arrTime));
        }

        Shop shp = new Shop(this.numOfServers);

        return new State(
            pq, shp,
            Stream
                .iterate(new State(pq, shp),
                         state -> !state.isEmpty(), state -> state.next())
                .map(state -> state.toString())
                .reduce("", (x, y) -> y));
    }
}
