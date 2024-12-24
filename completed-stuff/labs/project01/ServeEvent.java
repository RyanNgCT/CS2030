class ServeEvent extends Event {
    private final Server srv;

    ServeEvent(Customer cust, double eventTime, Server srv) {
        super(cust, eventTime);
        this.srv = srv;
    }

    Pair<Event, Shop> next(Shop shp) {
        double serviceTimeVal = shp.getServiceTime() + super.eventTime;
        return new Pair<Event, Shop>(
            new DoneEvent(super.cust, serviceTimeVal),
            shp.update(this.srv.serve(serviceTimeVal)));
    }

    @Override
    SimulationStatistics updateStats(SimulationStatistics stats) {
        return stats.updateAvgTime(this.cust.calcDifference(super.eventTime));
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.eventTime) + " " +
            super.cust.toString() + " serves by " + this.srv.toString();
    }
}
