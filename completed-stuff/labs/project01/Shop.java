import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

class Shop {
    private final int noOfServers;
    private final List<Server> serverList;
    private final Supplier<Double> svcTime;
    private final int qMax;

    Shop(int noOfServers, Supplier<Double> svcTime, int qMax) {
        this.noOfServers = noOfServers;
        this.serverList = IntStream.rangeClosed(1, noOfServers)
                              .boxed()
                              .map(srv -> new Server(srv))
                              .toList();
        this.svcTime = svcTime;
        this.qMax = qMax;
    }

    // accepts list to update servers
    Shop(int noOfServers, List<Server> serverList, Supplier<Double> svcTime,
         int qMax) {
        this.noOfServers = noOfServers;
        this.serverList = serverList;
        this.svcTime = svcTime;
        this.qMax = qMax;
    }

    public Optional<Server> findServer(double eventTime) {
        return this.serverList.stream()
            .filter(srv -> srv.canServe(eventTime))
            .findFirst();
    }

    public Optional<Server> findQueue() {
        return this.serverList.stream()
            .filter(srv -> srv.canQueue(this.qMax))
            .findFirst();
    }

    public Shop update(Server upSrv) {
        return new Shop(this.noOfServers,
                        this.serverList.stream()
                            .map(srv -> srv.sameServer(upSrv) ? upSrv : srv)
                            .toList(),
                        this.svcTime, this.qMax);
    }

    public double getServiceTime() {
        return this.svcTime.get();
    }

    public Server getServer(Server curr) {
        return this.serverList.stream()
                    .filter(srv -> srv.sameServer(curr))
                    .reduce(curr, (x, y) -> y);
    }

    @Override
    public String toString() {
        String shopToString = "[";
        for (int i = 1; i <= this.serverList.size(); i = i + 1) {
            if (i < this.serverList.size()) {
                shopToString = shopToString + "server " + i + ", ";
            } else {
                shopToString = shopToString + "server " + i;
            }
        }
        shopToString = shopToString + "]";
        return shopToString;
    }
}
