import java.util.Optional;

class ServeEvent extends Event {

    private final Server srv;
    private final Customer cust;

    ServeEvent(Customer cust, Server srv, double eventTime,
               double arrivalTime) {
        super(eventTime, arrivalTime);
        this.srv = srv;
        this.cust = cust;
    }

    Pair<Event, Shop> next(Shop shp) {
        // shop updating should be in the ArriveEvent class

        // Optional<Server> optSrv = shp.findServer(this.cust);
        // Shop updatedShop = optSrv.map(srv -> srv.serve(this.cust, 1.0))
        //                        .map(newServ -> shp.update(newServ))
        //                        .orElse(shp);

        return new Pair<Event, Shop>(
            new DoneEvent(this.cust, this.cust.serveTill(), super.arrivalTime),
            shp);
    }

    @Override
    public String toString() {
        return super.eventTime + " " + this.cust.toString() + " serve by " +
            this.srv.toString();
    }
}
