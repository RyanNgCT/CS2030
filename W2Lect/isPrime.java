import java.util.stream.IntStream;

void main() {}


boolean isPrime(int n){
	return n > 1  && IntStream.range(2, n)
	.filter(x -> n % x == 0)
	.sum() == 0;
	
}
