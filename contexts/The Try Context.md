```java
import java.util.function.Function;
import java.util.function.Supplier;

interface Try<T> {
    static <T> Try<T> of(Supplier<? extends T> supplier) {
        try {
            return succ(supplier.get());
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    private static <T> Try<T> succ(T t) {
        return new Try<T>() {
            public T orElse(T other) {
                return t;    // capturing the variable of the enclosing method
            }

            public T orElseGet(Supplier<? extends T> supp) {
                return t;
            }

            public String toString() {
                return "Success: " + t;
            }

            public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
                return Try.<R>of(() -> mapper.apply(t));    // a supplier of res
            }

            public <R> Try<R> flatMap(
                Function<? super T, ? extends Try<? extends R>> mapper) {
                // Try<? extends R> res = mapper.apply(t);
                return mapper.apply(t).map(Function.<R>identity());
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj instanceof Try<?> other) {
                    return this.flatMap(x -> other.map(val -> val.equals(x)))
                        .orElse(false);
                }
                return false;
            }
        };
    }

    private static <T> Try<T> fail(Exception ex) {
        return new Try<T>() {
            public T orElse(T other) {
                return other;
            }

            public T orElseGet(Supplier<? extends T> supp) {
                // we do this because the supplier holds the default value
                return supp.get();
            }

            public String toString() {
                return "Failure: " + ex;
            }

            public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
                return Try.<R>fail(ex);    // return just the exception
            }

            public <R> Try<R> flatMap(
                Function<? super T, ? extends Try<? extends R>> mapper) {
                return Try.<R>fail(ex);
            }

            @Override
            public boolean equals(Object obj) {
                if (this.toString().equals(obj.toString())) {
                    return true;
                }
                return false;
            }
        };
    }

    T orElse(T t);
    T orElseGet(Supplier<? extends T> supp);
    <R> Try<R> map(Function<? super T, ? extends R> mapper);
    <R> Try<R> flatMap(Function<? super T, ? extends Try<? extends R>> mapper);
    public boolean equals(Object obj);
}
```