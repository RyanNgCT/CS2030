class Server {
    private final int serverId;
    private final double initTime;

    // default constructor -> set initTime.
    Server(int serverId) {
        this.serverId = serverId;
        this.initTime = 0.0;
    }

    Server(int serverId, double initTime) {
        this.serverId = serverId;
        this.initTime = initTime;
    }

    public boolean canServe(Customer cust) {
        return cust.canBeServed(this.initTime);
    }

    public Server serve(Customer cust, double srvTime) {
        //double newTime = this.initTime + srvTime;
        // this.initTime = newTime; // error
        //return new Server(this.serverId, newTime);
        return new Server(this.serverId, cust.serveTill());
    }

    public boolean sameServer(Server srv) {
        return srv.serverId == this.serverId;
    }

    @Override
    public String toString() {
        //return "server " + this.serverId + "  --  " + this.initTime;
        return "server " + this.serverId;
    }
}
