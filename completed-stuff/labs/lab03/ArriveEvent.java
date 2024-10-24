class ArriveEvent extends Event {
    private final Customer cust;

    ArriveEvent(Customer cust, double eventTime) {
        super(eventTime, eventTime);
        this.cust = cust;
    }

    Pair<Event, Shop> next(Shop shp) {
        Event evt =
            shp.findServer(this.cust)
                .<Event>map(srv
                     -> {
                            shp.update(srv.serve(this.cust, 1.0));
                            return new ServeEvent(this.cust, srv, super.eventTime, super.arrivalTime);
                        })
                .orElse(new LeaveEvent(this.cust, super.eventTime, super.arrivalTime));

        return new Pair<Event, Shop>(evt, shp);
    }

    @Override
    public String toString() {
        return super.eventTime + " " + this.cust.toString() + " arrives";
    }
}
