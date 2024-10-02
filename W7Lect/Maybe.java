import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
// Optional.of(1);
// new Maybe<Integer>(1);
// Maybe.<Integer>of(1);
// Maybe.<Integer>empty();

class Maybe<T> {
    // context -> to wraps around a SINGLE value

    private final T value;

    // don't need to bind the constructor
    private Maybe(T value) {
        this.value = value;
    }

    static <T> Maybe<T> of(T value) {
        if (value == null) {
            throw new NullPointerException();
        }

        return new Maybe<T>(value);
    }

    static <T> Maybe<T> empty() {
        return new Maybe<T>(null);
    }

    private boolean isEmpty() {
        return this.value == null;
    }

    private T get() {
        return this.value;
    }

    Maybe<T> filter(Predicate<? super T> pred) {
        if (this.isEmpty()) {
            return this;    // no need to filter anymore
        }
        // abstract method of Predicate
        if (pred.test(this.get())) {
            return this;    // allow the element through
        }
        return Maybe.<T>empty();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Maybe<?> other) {
            // can use the equals method after unboxing
            this.get().equals(other.get());
        }
        return false;
    }

    <R> Maybe<R> map(Function<? super T, ? extends R> mapper) {
        if (this.isEmpty()) {
            return Maybe.<R>empty();
        }
        // consumes this.get() -> provides
        R ret = mapper.apply(this.get());
        return Maybe.<R>of(ret);
    }

    <R> Maybe<R> flatMap(Function<? super T, ? extends Optional<R>> mapper) {

        // to implement
        return null;
    }

    T orElse(T other) {
        if (this.isEmpty()) {
            return this;
        }
        return this.get();
    }

    T orElseGet(Supplier<? extends T> supplier) {
        if (this.isEmpty()) {
            return this;
        }
        return supplier.get();
    }

    @Override
    public String toString() {
        // need to check for the presence of the value itself
        if (this.isEmpty()) {
            return "Maybe.empty";
        }
        return "Maybe[" + this.get() + "]";
    }
}
