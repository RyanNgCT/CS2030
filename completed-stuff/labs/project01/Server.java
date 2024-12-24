class Server {
    private final int serverId;
    private final int queueLen;
    private final double initTime;

    Server(int serverId) {
        this.serverId = serverId;
        this.queueLen = 0;
        this.initTime = 0.0;
    }

    Server(int serverId, int queueLen, double initTime) {
        this.serverId = serverId;
        this.queueLen = queueLen;
        this.initTime = initTime;
    }

    public boolean canServe(double arrivalTime) {
        return arrivalTime >= this.initTime;
    }

    public Server serve(double timeTaken) {
        return new Server(this.serverId, this.queueLen, timeTaken);
    }

    public boolean canQueue(int maxLen) {
        return (this.queueLen >= 0) && (this.queueLen < maxLen);
    }

    public Server queue() {
        return new Server(this.serverId, this.queueLen + 1, this.initTime);
    }

    public Server dequeue() {
        return new Server(this.serverId, this.queueLen - 1, this.initTime);
    }

    public boolean sameServer(Server srv) {
        return srv.serverId == this.serverId;
    }

    public double getAvailTime() {
        return this.initTime;
    }

    @Override
    public String toString() {
        return "server " + this.serverId;
    }
}
