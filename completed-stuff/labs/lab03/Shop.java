import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Shop {
    private final int noOfServers;
    private final List<Server> serverList;

    Shop(int noOfServers) {
        this.noOfServers = noOfServers;
        this.serverList = IntStream.rangeClosed(1, noOfServers)
                              .mapToObj(srv -> new Server(srv))
                              .toList();
    }

    // accepts list to update servers
    Shop(int noOfServers, List<Server> serverList) {
        this.noOfServers = noOfServers;
        this.serverList = serverList;
    }

    public Optional<Server> findServer(Customer cust) {
        return this.serverList.stream()
            .filter(srv -> srv.canServe(cust))
            .findFirst();
    }

    public Shop update(Server upSrv) {
        return new Shop(this.noOfServers,
                        this.serverList.stream()
                            .map(srv -> srv.sameServer(upSrv) ? upSrv : srv)
                            .toList());
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

        //return serverList.stream()
        //    .map(Server::toString)
        //    .collect(Collectors.joining(", ", "[", "]"));
    }
}
