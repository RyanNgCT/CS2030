### Question 1
Without modifying the Circle class, write the `FilledCircle` class with the `unfill()` method according to the sample test cases.

```java
class Circle implements Shape {
    private final double radius;

    Circle(double radius) {
        this.radius = radius;
    }
    
    Circle(Circle c) {
        this(c.radius);
    }
    
    public double getArea() {
        return Math.PI * this.radius * this.radius;
    }
    
    public String toString() {
        return "Circle with radius " + this.radius;
    }
}
```

Answer:
```java
import java.awt.Color;

class FilledCircle extends Circle {
    private final Color color;

    FilledCircle(Circle circle, Color color) {
        super(circle);
        this.color = color;
    }

    public Circle unfill() {
        return new Circle(this);
    }

    @Override
    public String toString() {
        return "Filled" + super.toString() + " and " + this.color;
    }
}
```

### Question 2
Write the class `FilledSquare` by implementing the `unfill()` method specified by the interface `Fillable<T>`.

```java
interface Shape {
	public double getArea();
}
```

```java 
class Square implements Shape {
    private final double side;
    
    Square(double side) {
        this.side = side;
    }
    
    Square(Square c) {
        this(c.side);
    }
    
    public double getArea() {
        return this.side * this.side;
    }
    
    public String toString() {
        return "Square with side " + this.side;
    }
}
```

Answer:
```java
import java.awt.Color;

class FilledT<T> implements Fillable<T> {
	private final Color color;

	// capture the T when it has yet to be filled
	private final T t;

	FilledT(T t, Color color) {
		this.t = t;
		this.color = color;
	}

	T unfill() {
		// return new T(this.t); ❌
		return this.t;
	}
}
```

```java
import java.awt.Color;

class FilledSquare extends FilledT<Square> {
	FilledSquare(Square sq, Color color) {
		super(sq, color);
	}
}
```

```java
jshell> Square s = new Square(1)
s ==> Square with side 1.0

jshell> Shape shape = s
shape ==> Square with side 1.0

jshell> FilledSquare fs = new FilledSquare(s, Color.RED)
fs ==> FilledSquare@57855c9a

jshell> s = fs
|  Error:
|  incompatible types: FilledSquare cannot be converted to Square
|  s = fs
|      ^^
```

Corrected Implementation:
```java
import java.awt.Color;

class FilledSquare extends Square implements Fillable<Square> {
    private final Fillable<Square> fs;

    FilledSquare(Square sq, Color color) {
        super(sq);
        this.fs = new FilledT<Square>(sq, color);
    }

    public Square unfill() {
        return this.fs.unfill();
    }
}
```

### Question 3
- the variable capture applies in both capture methods
	- `Func.this` can be only be used in the `apply()` methods
	- can use the `prev` property anywhere (as opposed to the above)

```java
import java.util.Optional;

abstract class Func {
    abstract Integer apply(Integer x);
    private final Optional<Func> prev;

    Func(Func prev) {
        this.prev = Optional.<Func>of(prev);
    }

    Func() {
        this.prev = Optional.<Func>empty();
    }

    Func compose(Func other) {
        // this is here to assign the prev property
        return new Func(this) {
            Integer apply(Integer x) {
                return Func.this.apply(other.apply(x));
            }
        };
    }

    Func decompose() {
        // return this for the first function
        return this.prev.orElse(this);
    }
}
```

```java
Func f = new Func() {
	Integer apply(Integer x) {
		return 2 * x;
	}
}

Func g = new Func() {
	Integer apply(Integer x) {
		return 2 + x;
	}
}
```

### Question 4
Skip, using `ImList`.
```java
Stream<Integer> stream = Stream.of(1, 2, 3, 4);

// x and y need to be of the same types, need to convert every element to an immutable list
stream.map(x -> new ImList<Integer>().add(x)).reduce(new ImList<Integer>(), (x, y) -> x.addAll(y));
```

### Question 5
Write a generic method `issorted()` with the appropriate method header that takes in a `Stream<T>` and returns `true` if the stream elements are sorted in *non-decreasing order* via the natural order of `T`, or `false` otherwise.

The `Pair` class
```java
public class Pair<T, U> {
    private final T t;
    private final U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }

    public T t() {
        return this.t;
    }

    public U u() {
        return this.u;
    }

    @Override
    public String toString() {
        return "(" + this.t + ", " + this.u + ")";
    }
}
```

Answer:
```java
import java.util.Comparator;
import java.util.stream.Stream;
  
boolean isSorted(Stream<T> str) {
    Comparator<T> cmp = (x, y) -> x > y;
    return Stream
            .map(x ->
                cmp.compareTo(x - 1))
            .noneMatch(x -> x > 0);
}
```

Correct Answer:
- rather than using the two argument reduce, we should use the one argument reduce (since it is possible for the stream to be empty.
- hint regarding using `Pair(t, u)` $\implies$ have to work on the `boolean` and the `t` value in the reduce
	- need to preset `boolean` value to `true`
```java
import java.lang.Comparable;
import java.util.stream.Stream;

<T extends Comparable<T>> boolean issorted(Stream<T> str) {
    return str
        .map(x -> new Pair<Boolean, T>(true, x))
        .reduce((x, y) -> new Pair<Boolean, T>(x.t() && x.u().compareTo(y.u()) <= 0, y.u()))
        .map(x -> x.t())
        .orElse(false);
}

```

### Question 6
Replace the implementation of `playGame` by a single Stream pipeline.
```java
void printHint(int guess, int secret) {
	if (guess < secret) {
		System.out.println(guess + " is too low"); 
	}
	if (guess > secret) {
		System.out.println(guess + " is too high");
	}
}
```

Have to call `nxtInteger.get()` somewhere to invoke the Supplier.
- not `anyMatch` since it returns a `boolean`, whereas `playGame` is **not supposed** to return anything.

*Answer:*
```java
void playGame(int secret, Supplier<Integer> nxtInteger) {
	return Stream.iterate(0, x -> nxtInteger.get()).filter(x -> x == secret).anyMatch();
}
```

***Corrected Implementation***
```java
import java.util.function.Supplier;
import java.util.stream.Stream;

void printHint(int guess, int secret) {
    if (guess < secret) {
        System.out.println(guess + " is too low"); 
    }
    if (guess > secret) {
        System.out.println(guess + " is too high");
    }
}

void playGame(int secret, Supplier<Integer> nxtInteger) {
	// use the takeWhile method
    Stream.generate(nxtInteger).takeWhile(guess -> guess != secret).forEach(guess -> printHint(guess, secret));
}

Scanner sc = new Scanner(System.in);
```

### Question 7
By writing a `void test(List<Integer> list, int secret)` method that takes in a list of integers and the secret correct guess, show how the test can be constructed such that an appropriate call to `playGame` from within the `test` method gives the following output:

```java
import java.util.function.Supplier;
import java.util.stream.Stream;

void printHint(int guess, int secret) {
    if (guess < secret) {
        System.out.println(guess + " is too low"); 
    }
    if (guess > secret) {
        System.out.println(guess + " is too high");
    }
}

void playGame(int secret, Supplier<Integer> nxtInteger) {
	// use the takeWhile method
    Stream.generate(nxtInteger).takeWhile(guess -> guess != secret).forEach(guess -> printHint(guess, secret));
}

Scanner sc = new Scanner(System.in);

void test(List<Integer> list, int secret) {
    Stream.iterate(0, x -> x < list.size(), x -> x + 1).map(x -> playGame(secret, () -> x));
}
```

Correct Answer:
```java
void test(List<Integer> list, int secret) {
    Iterator<Integer> iter = list.iterator();
    playGame(secret, () -> iter.next());
}
```

### Question 8
Complete the Either class by defining the following methods in order:
- `applyA`
- `applyB`
- `applyAorB`
```java
import java.util.Optional;
import java.util.function.Function;

class Either<A, B> {
    private final Optional<A> a;
    private final Optional<B> b;

    Either(Optional<A> a, Optional<B> b) {
        this.a = a;
        this.b = b;
    }

    static <A,B> Either<A,B> ofA(A a) {
        return new Either<A,B>(Optional.of(a), Optional.empty());
    }

    static <A,B> Either<A,B> ofB(B b) {
        return new Either<A,B>(Optional.empty(), Optional.of(b));
    }
  
    <C> C applyA(Function<A, C> mapper) {
        return a.map(mapper).orElseThrow();
    }

    <C> C applyB(Function<B, C> mapper) {
        return b.map(mapper).orElseThrow();
    }

    <C> C applyAorB(Function<A, C> mapper1, Function<B,C> mapper2) {
        return a.map(mapper1).or(() -> b.map(mapper2)).orElseThrow();
    }

    public String toString() {
        return this.applyAorB(x -> "A:" + x, y -> "B:" + y);
    }
}
```

### Question 9
Include additional methods into the Task class for the above stream pipeline to work. You need only write these methods in your answer.
```java
class Task {
	private final Either<UnaryOperator<Integer>,
	CompletableFuture<Integer>> UOpOrCF;

	Task(UnaryOperator<Integer> f) {
		this.UOpOrCF = Either.ofA(f);
	}
	
	Task(Integer t) {
		this.UOpOrCF = Either.ofB(CompletableFuture.completedFuture(t));
	}
	
	Task(CompletableFuture<Integer> cf) {
		this.UOpOrCF = Either.ofB(cf);
	}

	Task thenApply(Task task) {
		return new Task(this.UOpOrCF.applyB(cf -> cf.thenApplyAsync(task.UOpOrCF.applyA(x -> x))));
	}

	int get() {
		return UOporCF.applyB(cf -> cf.join());
	}
}
```