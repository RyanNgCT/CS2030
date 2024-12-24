class LeaveEvent extends Event {

    LeaveEvent(Customer cust, double eventTime) {
        super(cust, eventTime);
    }

    Pair<Event, Shop> next(Shop shp) {
        return new Pair<Event, Shop>(this, shp);
    }

    @Override
    SimulationStatistics updateStats(SimulationStatistics stats) {
        return stats.updateNotServed();
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.eventTime) + " " +
            super.cust.toString() + " leaves";
    }
}
