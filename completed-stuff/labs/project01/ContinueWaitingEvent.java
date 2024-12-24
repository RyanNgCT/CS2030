class ContinueWaitingEvent extends Event {
    private final Server srv;

    ContinueWaitingEvent(Customer cust, double eventTime, Server srv) {
        super(cust, eventTime);
        this.srv = srv;
    }

    Pair<Event, Shop> next(Shop shp) {
        Server newSrv = shp.getServer(this.srv);

        if (newSrv.canServe(super.eventTime)) {
            Server dequeued = newSrv.dequeue();

            return new Pair<Event, Shop>(
                new ServeEvent(super.cust, super.eventTime, dequeued),
                shp.update(dequeued));
        }

        Shop newShp = shp.update(newSrv);
        return new Pair<Event, Shop>(
            new ContinueWaitingEvent(super.cust, newSrv.getAvailTime(), newSrv),
            newShp);
    }

    @Override
    public String toString() {
        return "";
    }
}
