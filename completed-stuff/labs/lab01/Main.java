import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// === Q1 ===

boolean isPrime(int n){
    return n > 1 && IntStream.range(2, n).noneMatch(x -> n % x == 0);
}

IntStream twinPrimes(int n){
    return IntStream.rangeClosed(2, n).filter(
        x -> isPrime(x) && (isPrime(x - 2) || isPrime(x + 2)));
}

// === Q2 ===

String reverse(String str){
    return Stream.of(str.split("")).reduce("", (a, b) -> b + a); // concat in reverse order
}

// === Q3 ===

int countRepeats(List<Integer> list){
    return IntStream
        .range(0, list.size() - 1)
        .filter(n -> 
            list.get(n) == list.get(n + 1) && 
            (n + 2 > (list.size() - 1) || // check if 3rd character exceeds bounds 
            (list.get(n) != list.get(n + 2) || list.get(n + 1) != list.get(n + 2))) // third element don't match first and second -> end of duplicate chunk
        )
        .reduce(0, (x, y) -> x + 1);
}

void main() {}
