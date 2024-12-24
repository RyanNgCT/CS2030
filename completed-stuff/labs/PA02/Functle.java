import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

class Functle<T extends Movable<T>> {
    private final Consumer<T> move;
    private final Consumer<T> revMove;

    private Functle(Consumer<T> move) {
        this.move = move;
        this.revMove = t -> {};
    }

    private Functle(Consumer<T> move, Consumer<T> revMove) {
        this.move = move;
        this.revMove = revMove;
    }

    static <T extends Movable<T>> Functle<T> of() {
        Consumer<T> move = t -> {};
        return new Functle<T>(move);
    }

    public void run(T tObject) {
        this.move.accept(tObject);
    }

    public Functle<T> left(int theta) {
        Consumer<T> rev = s -> s.turnLeft(-theta);
        return new Functle<T>(this.move.andThen(t -> t.turnLeft(theta)),
                              rev.andThen(this.revMove));
    }

    public Functle<T> forward(int steps) {
        Consumer<T> rev = s -> s.moveForward(-steps);
        return new Functle<T>(this.move.andThen(t -> t.moveForward(steps)),
                              rev.andThen(this.revMove));
    }

    public Functle<T> backward(int steps) {
        Consumer<T> rev = s -> s.moveForward(steps);
        return new Functle<T>(this.move.andThen(t -> t.moveForward(-steps)),
                              rev.andThen(this.revMove));
    }

    public Functle<T> right(int theta) {
        Consumer<T> rev = s -> s.turnLeft(theta);
        return new Functle<T>(this.move.andThen(t -> t.turnLeft(-theta)),
                              rev.andThen(this.revMove));
    }

    public Functle<T> reverse() {
        return new Functle<T>(this.move.andThen(revMove));
    }

    public Functle<T> andThen(Functle<T> other) {
        return new Functle<T>(this.move.andThen(other.move),
                              this.revMove.andThen(other.revMove));
    }

    public Functle<T> loop(int n) {
        return Stream.iterate(this, currFunctle -> currFunctle.andThen(this))
            .limit(n)
            .reduce(new Functle<T>(t -> {}),
                    (prev, next) -> next);
    }

    public Functle<T> comeHome() {
        return new Functle<T>(
            ele
            -> Stream
                   .iterate(1, x
                            -> {
                                Functle<T> newFc = this.loop(x);
                                if (ele.equals(() -> {
                                        newFc.move.accept(ele);
                                        return ele;
                                    }))
                                    return false;
                                newFc.revMove.accept(ele);
                                return true;
                            },
                            x -> x + 1)
                   .map(x -> this.loop(x).move)
                   .reduce(this.move, (c1, c2) -> c2));
    }

    @Override
    public String toString() {
        return "Functle";
    }
}
