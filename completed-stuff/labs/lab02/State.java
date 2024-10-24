import java.util.Optional;

class State {
    private final Optional<Customer> cust;
    private final Shop shp;
    private final String addOns;

    State(Shop shp) {
        this.shp = shp;
        this.addOns = "";
        this.cust = Optional.empty();
    }

    State(Shop shp, Customer cust) {
        this.shp = shp;
        this.cust = Optional.of(cust);
        this.addOns =
            this.cust.map(c -> c.toString()).orElse("") + (" arrives\n");
    }

    State(Shop shp, String addOns) {
        this.shp = shp;
        this.cust = Optional.empty();
        this.addOns = addOns;
    }

    public State next(State curr) {
        // updates the toString (State object will always change)
        Optional<Customer> optCust = curr.cust;

        Optional<Server> rawSrv =
            optCust.flatMap(cust -> this.shp.findServer(cust));

        Shop updatedShop =
            rawSrv
                .flatMap(srv
                         -> optCust.map(cust -> srv.serve(cust, 1.0))
                                .map(newSrv -> shp.update(newSrv)))
                .orElse(shp);

        String eventDesc =
            this.addOns + curr.addOns +
            rawSrv
                .map(srv
                     -> optCust.map(cust -> cust.toString()).orElse("") +
                            " served by " + srv.toString() + "\n")
                .orElse(optCust.map(cust -> cust.toString()).orElse("") +
                        " leaves\n");

        return new State(updatedShop, eventDesc);
    }

    //State change -> toString same signature but contents will change
    @Override
    public String toString() {
        return this.addOns;
    }
}
