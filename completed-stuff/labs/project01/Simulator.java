import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServers;
    private final int qMax;
    private final int numOfCustomers;
    private final Supplier<Double> svcTime;
    private final List<Pair<Integer, Double>> arrivals;

    Simulator(int numOfServers, int qMax, Supplier<Double> svcTime,
              int numOfCustomers, List<Pair<Integer, Double>> arrivals) {
        this.numOfServers = numOfServers;
        this.qMax = qMax;
        this.numOfCustomers = numOfCustomers;
        this.svcTime = svcTime;
        this.arrivals = arrivals;
    }

    private PQ<Event> initPQ(List<Pair<Integer, Double>> arrivals) {
        PQ<Event> pq = new PQ<Event>();
        for (Pair<Integer, Double> arr : arrivals) {
            int custId = arr.t();
            double arrTime = arr.u();

            pq =
                pq.add(new ArriveEvent(new Customer(custId, arrTime), arrTime));
        }
        return pq;
    }

    public String run() {
        PQ<Event> pq = initPQ(this.arrivals);
        Shop shp = new Shop(this.numOfServers, this.svcTime, this.qMax);

        State s = Stream
                      .iterate(new State(pq, shp),
                               state -> !state.isEmpty(), state -> state.next())
                      .reduce((x, y) -> y)
                      .orElse(new State(pq, shp));

        return s.updateStats().toString();
    }
}
