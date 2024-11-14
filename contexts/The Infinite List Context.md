```java
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

interface IFL<T> {

    private static <T> IFL<T> of(Supplier<Optional<T>> hd, Supplier<IFL<T>> tl) {
        return new IFL<T>() {
            public Optional<T> get() {
                return hd.get();
            }

            public IFL<T> next() {
                return tl.get();
            }

            public boolean isEmpty() {
                return false;
            }

            public IFL<T> limit(int n) {
                if (n == 0) {
                    return IFL.<T>empty();
                }
                return IFL.<T>of(hd, () -> this.next().limit(n - 1));
            }

            public void forEach(Consumer<? super T> action) {
                IFL<T> curr = this;
                while (!curr.isEmpty()) {
                    curr.get().ifPresent(action);
                    curr = curr.next();
                }
            }

            public <R> IFL<R> map(Function<? super T, ? extends R> mapper) {
                return IFL.<R>of(
                    () -> this.get().map(mapper),
                    () -> this.next().map(mapper)
                );
            }

            public IFL<T> concat(IFL<T> other) {
                return IFL.<T>of(
                    hd,
                    () -> this.next().concat(other)
                );
            }

			public <R> IFL<R> flatMap(
                Function<? super T, ? extends IFL<? extends R>> mapper) {
                
                return IFL.<R>of(
                    () -> this.get()
                              .map(mapper)
                              .flatMap(x -> x.get().map(Function.<R>identity())),
                    () -> this.get()
                              .map(mapper)
                              .map(x -> x.next()
                                         .map(Function.<R>identity())
                                         .concat(this.next().flatMap(mapper)))
                              .orElse(IFL.<R>empty())
                );
            }
        };
    }

    private static <T> IFL<T> empty() {
        return new IFL<T>() {
            public Optional<T> get() {
                return Optional.empty();
            }

            public IFL<T> next() {
                return this;
            }

            public boolean isEmpty() {
                return true;
            }

            public IFL<T> limit(int n) {
                return this;
            }

            public void forEach(Consumer<? super T> action) {}

            public <R> IFL<R> map(Function<? super T, ? extends R> mapper) {
                return IFL.<R>empty();
            }

			public <R> IFL<R> flatMap(
                Function<? super T, ? extends IFL<? extends R>> mapper) {
                return IFL.<R>empty();
            }

            public IFL<T> concat(IFL<T> other) {
                return other;
            }
        };
    }

    static <T> IFL<T> iterate(T seed, Function<T, T> next) {
        return IFL.<T>of(
            () -> Optional.of(seed),
            () -> IFL.iterate(next.apply(seed), next)
        );
    }

    Optional<T> get();
    IFL<T> next();
    boolean isEmpty();
    IFL<T> limit(int n);
    void forEach(Consumer<? super T> action);
    <R> IFL<R> map(Function<? super T, ? extends R> mapper);
    <R> IFL<R> flatMap(Function<? super T, ? extends IFL<? extends R>> mapper);
    IFL<T> concat(IFL<T> other);
}

```