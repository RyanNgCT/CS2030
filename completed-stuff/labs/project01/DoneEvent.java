class DoneEvent extends Event {
    DoneEvent(Customer cust, double eventTime) {
        super(cust, eventTime);
    }

    Pair<Event, Shop> next(Shop shp) {
        return new Pair<Event, Shop>(this, shp);
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.eventTime) + " " +
            super.cust.toString() + " done";
    }
}
