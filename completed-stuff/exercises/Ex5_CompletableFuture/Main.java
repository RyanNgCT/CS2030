import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * This program finds different ways one can travel by bus (with a bit 
 * of walking) from one bus stop to another.
 *
 * @author: cs2030 (orig. Ooi Wei Tsang)
 */
class Main {

    static CompletableFuture<BusRoutes> processQuery(String query) {
        Scanner sc = new Scanner(query);
        BusStop srcBusStop = new BusStop(sc.next());
        String searchString = sc.next();
        sc.close();
        return BusSg.findBusServicesBetween(srcBusStop, searchString);
    }

    public static void main(String[] args) {
        Instant start = Instant.now();
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n")
            .tokens()    // get a Stream<String>
            .map(s
                 -> processQuery(
                     s))    // obtain a Stream<CompletableFuture<BusRoutes>>
            .toList()
            .stream()
            .forEach(routes
                     -> routes.thenCompose(r -> r.description())
                            .thenAccept(x -> System.out.println(x))
                            .join());    // generate description
        sc.close();
        Instant stop = Instant.now();
        System.out.printf("Took %,dms\n",
                          Duration.between(start, stop).toMillis());
    }
}
