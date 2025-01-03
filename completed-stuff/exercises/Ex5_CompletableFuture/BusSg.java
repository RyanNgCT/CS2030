import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Stream;

/**
 * A BusSg class encapsulate the data related to the bus services and
 * bus stops in Singapore, and supports queries to the data.
 *
 * @author: cs2030 (orig. Ooi Wei Tsang)
 */
class BusSg {

    /**
     * Returns the list of bus services that serve this bus stop by
     * querying the web server.
     * @return A list of BusService that serve this bus stop.
     */

    // does the actual API call
    public static CompletableFuture<List<BusService>> getBusServices(
        BusStop stop) {
        return BusAPI.getBusServicesAt(stop.getStopId()).thenApply(str -> {
            Scanner sc = new Scanner(str);
            List<BusService> busServices =
                sc.useDelimiter("\n")
                    .tokens()
                    .flatMap(line -> Stream.of(line.split(",")))
                    .map(id -> new BusService(id))
                    .sorted()
                    .toList();
            sc.close();
            return busServices;
        });
    }

    /**
     * Finds the bus services that serve between the given stop
     * and any bus stop with a matching name.
     * @param  stop The bus stop.  Assumed non-null.
     * @param  searchString The (partial) name of other bus stops, assumed non-null.
     * @return The bus routes between the stops.
     */
    public static CompletableFuture<BusRoutes> findBusServicesBetween(
        BusStop stop, String searchString) {
        // try {
        return getBusServices(stop)
            .thenApply(
                srvcs
                    -> srvcs.stream().collect(
                        ()
                            -> new LinkedHashMap<
                                BusService, CompletableFuture<List<BusStop>>>(),
                        (map, service)
                            -> map.put(service,
                                   service.findStopsWith(searchString)),
                        (map1, map2) -> map1.putAll(map2)))
            .thenApply(validServices
                       -> new BusRoutes(stop, searchString, validServices))
            .handle((res, e) -> {
                if (res == null) {
                    System.err.println("Unable to complete query: " + e);
                    return new BusRoutes(stop, searchString, Map.of());
                }
                return res;
            });
        // } catch (CompletionException e) {
        //     System.err.println("Unable to complete query: " + e);
        //     return new BusRoutes(stop, searchString, Map.of());
        // }
    }
}
