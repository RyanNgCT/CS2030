// this is the client program

import java.util.stream.Stream;

double findMaxDistance(Location ref, List<Location> locs) {
    return locs
            .stream()
            .map(x -> x.distanceTo(ref))
            .reduce(0.0, (x, y) -> Math.max(x, y));
}

