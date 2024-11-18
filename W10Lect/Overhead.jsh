import java.time.*;

boolean isPrime(int n) {
    return n > 1 && IntStream
                        .range(2, (int) Math.sqrt(n))
                        .parallel()
                        .noneMatch(x -> n % x == 0);                       
}

long numOfPrimes(int from, int to) {
    Instant start = Instant.now(); // start the timing

    long howMany = IntStream.rangeClosed(from, to)
        .parallel()
        .filter(x -> isPrime(x))
        .count();

    Instant stop = Instant.now(); // end the timing

    System.out.println("Duration: " + 
        Duration.between(start, stop).toMillis() + "ms");
    return howMany;
}
