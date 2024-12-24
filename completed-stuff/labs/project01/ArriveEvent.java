class ArriveEvent extends Event {

    ArriveEvent(Customer cust, double eventTime) {
        super(cust, eventTime);
    }

    Pair<Event, Shop> next(Shop shp) {
        // 3 branches - serve, wait, and leave
        return shp.findServer(super.eventTime)
            .map(srv
                 -> new Pair<Event, Shop>(
                     new ServeEvent(super.cust, super.eventTime, srv), shp))
            .orElse(
                shp.findQueue()
                    .map(srv
                         -> new Pair<Event, Shop>(
                             new WaitEvent(super.cust, super.eventTime, srv),
                             shp))
                    .orElse(new Pair<Event, Shop>(
                        new LeaveEvent(super.cust, super.eventTime), shp)));
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.eventTime) + " " +
            super.cust.toString() + " arrives";
    }
}
