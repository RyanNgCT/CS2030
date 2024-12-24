import java.util.Optional;

class State {
    private final Shop shp;
    private final PQ<Event> pQueue;
    private final String addOns;
    private final boolean flag;
    private final SimulationStatistics stats;

    State(PQ<Event> pQueue, Shop shp) {
        this.shp = shp;
        this.pQueue = pQueue;
        this.addOns = "";
        this.flag = false;
        this.stats = new SimulationStatistics(); // initialize
    }

    State(PQ<Event> pQueue, Shop shp, String addOns,
          SimulationStatistics stats) {
        this.shp = shp;
        this.pQueue = pQueue;
        this.addOns = addOns;
        this.flag = false;
        this.stats = stats;
    }

    State(PQ<Event> pQueue, Shop shp, String addOns, boolean flag,
          SimulationStatistics stats) {
        this.shp = shp;
        this.pQueue = pQueue;
        this.addOns = addOns;
        this.flag = flag;
        this.stats = stats;
    }

    public boolean isEmpty() {
        return this.pQueue.isEmpty() && this.flag;
    }

    public State next() {
        Pair<Optional<Event>, PQ<Event>> pair = this.pQueue.poll();
        Optional<Event> optEvent = pair.t();
        PQ<Event> newPQ = pair.u();

        return optEvent
            .map(evt -> {
                Pair<Event, Shop> next = evt.next(this.shp);
                Event nextEvent = next.t();
                Shop newShop = next.u();

                // call for each event object
                SimulationStatistics updatedStats = evt.updateStats(this.stats);

                PQ<Event> nextPQ = Optional.ofNullable(nextEvent)
                                       .filter(e -> e != evt)
                                       .map(e -> newPQ.add(e))
                                       .orElse(newPQ);

                String desc = "";
                if (evt.toString() == "") {
                    desc = this.addOns;
                } else {
                    if (this.addOns == "") {
                        desc = evt.toString();
                    } else {
                        desc = this.addOns + "\n" + evt.toString();
                    }
                }
                return new State(nextPQ, newShop, desc, updatedStats);
            })
            .orElse(new State(newPQ, this.shp, this.addOns, true, this.stats));
    }

    public State updateStats() {
        return new State(this.pQueue, this.shp,
                         this.addOns + "\n" + this.stats.toString(), true,
                         this.stats);
    }

    @Override
    public String toString() {
        return this.addOns;
    }
}
