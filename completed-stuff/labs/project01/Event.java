abstract class Event implements Comparable<Event> {
    protected final Customer cust;
    protected final double eventTime;

    Event(Customer cust, double eventTime) {
        this.cust = cust;
        this.eventTime = eventTime;
    }

    abstract Pair<Event, Shop> next(Shop shp);

    // override when want to update in subclasses
    SimulationStatistics updateStats(SimulationStatistics stats) {
        return stats;
    }

    @Override
    public int compareTo(Event evt) {
        if (this.eventTime < evt.eventTime) {
            return -1;
        } else if (this.eventTime > evt.eventTime) {
            return 1;
        }
        return this.cust.compareTo(evt.cust);
    }
}
