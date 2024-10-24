class LeaveEvent extends Event {

    private final Customer cust;

    LeaveEvent(Customer cust, double eventTime, double arrivalTime) {
        super(eventTime, arrivalTime);
        this.cust = cust;
    }

    Pair<Event, Shop> next(Shop shp) {
        return new Pair<>(this, shp);
    }

    @Override
    public String toString() {
        return super.eventTime + " " + this.cust.toString() + " leaves";
    }
}
