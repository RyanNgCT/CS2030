class Request {
    private final int distance;
    private final int noOP;
    private final int time;

    Request(int distance, int noOP, int time) {
        this.distance = distance;
        this.noOP = noOP;
        this.time = time;
    }

    public int computeFare(Service srv) {
        return srv.computeFare(this.distance, this.noOP, this.time);
    }

    @Override
    public String toString() {
        return this.distance + "km for " + this.noOP + "pax @ " + this.time +
            "hrs";
    }
}
