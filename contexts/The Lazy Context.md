```java
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

class Lazy<T> implements Supplier<T> {
    private final Supplier<? extends T> supplier;
    private Optional<T> cache;    // why not final?? -> need to modify

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        this.cache = Optional.<T>empty();    // added this prop
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier) {
        if (supplier == null) {
            throw new NullPointerException();
        }
        return new Lazy<T>(supplier);
    }

    private T caching() {
        T v = this.supplier.get();
        this.cache = Optional.<T>of(v);
        return v;
    }

    public T get() {
        // why use orElseGet() ?
        return this.cache.orElseGet(() -> caching());
    }

    private boolean isEmpty() {
        return this.supplier == null;
    }

    <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
        // 1. get the value using get()
        // 2. apply the function -> mapper.apply()
        // 2a. Store the return value so that we can create Lazy<R>

		// storing in supplier delays evaluation and prevents eager eval
        Supplier<? extends R> supp = ()
            -> mapper.apply(
                this.get());
        return new Lazy<R>(supp);
    }

    <R> Lazy<R> flatMap(
        Function<? super T, ? extends Lazy<? extends R>> mapper) {
        Supplier<? extends R> supp =
            () -> mapper.apply(this.get()).get();  // obtains R or R subtypes

        return new Lazy<R>(supp);
    }
}
```