class WaitEvent extends Event {
    private final Server srv;

    WaitEvent(Customer cust, double eventTime, Server srv) {
        super(cust, eventTime);
        this.srv = srv;
    }

    Pair<Event, Shop> next(Shop shp) {
        Server qServer = this.srv.queue();

        return new Pair<Event, Shop>(
            new ContinueWaitingEvent(super.cust, super.eventTime,
                                     qServer),
            shp.update(qServer));
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.eventTime) + " " +
            super.cust.toString() + " waits at " + this.srv.toString();
    }
}
