import java.util.Optional;

class State {
    private final Shop shp;
    private final PQ<Event> pQueue;
    private final String addOns;
    private final boolean flag;

    State(PQ<Event> pQueue, Shop shp) {
        this.shp = shp;
        this.pQueue = pQueue;
        this.addOns = "";
        this.flag = false;
    }

    State(PQ<Event> pQueue, Shop shp, String addOns) {
        this.shp = shp;
        this.pQueue = pQueue;
        this.addOns = addOns;
        this.flag = false;
    }

    State(PQ<Event> pQueue, Shop shp, String addOns, boolean flag) {
        this.shp = shp;
        this.pQueue = pQueue;
        this.addOns = addOns;
        this.flag = flag;
    }

    public boolean isEmpty() {
        return this.pQueue.isEmpty() && this.flag;
    }

    public State next() {
        Pair<Optional<Event>, PQ<Event>> pair = this.pQueue.poll();
        Optional<Event> optEvent = pair.t();
        PQ<Event> newPQ = pair.u();

        PQ<Event> nextPQ = optEvent.filter(evt -> evt != evt.next(this.shp).t())
                               .map(e -> newPQ.add(e.next(this.shp).t()))
                               .orElse(newPQ);

        // Optional<Event> nextOptEvent = optEvent.map(evt -> evt.next(this.shp).t());

        return optEvent
            .map(evt -> {
                String desc = (this.addOns == "")
                                  ? this.addOns + evt.toString()
                                  : this.addOns + "\n" + evt.toString();
                Shop newShop = evt.next(this.shp).u();
                return new State(nextPQ, newShop, desc);
            })
            .orElse(new State(nextPQ, this.shp, this.addOns, true));
    }

    @Override
    public String toString() {
        return this.addOns;
    }
}
