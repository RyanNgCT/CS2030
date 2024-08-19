import java.util.stream.IntStream;

public class isPrime1{
	public static void main(String[] args) {
		System.out.println(isPrime1(21));  // Should print: false
        System.out.println(isPrime1(7));   // Should print: true
	}


	public static boolean isPrime1(int n){
		return n > 1  && IntStream.range(2, n)
		.noneMatch(x -> n % x == 0);
		
	}
}
