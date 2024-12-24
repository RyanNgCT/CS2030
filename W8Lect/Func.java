import java.util.Optional;

abstract class Func<T, R> {
    abstract R apply(T x);
    private final Optional<Func<T, R>> prev;

    Func(Func<T, R> prev) {
        this.prev = Optional.<Func<T, R>>of(prev);
    }

    Func() {
        this.prev = Optional.<Func<T, R>>empty();
    }

    // we need to have f.compose(g)
    <V> Func<V, R> compose(Func<V, T> other) {
        // return outcome of the composition
        return new Func<V, R>() {
            R apply(V x) {
                T res = other.apply(x);
                return Func.this.apply(res);
            }
        };
    }

    <V> Func<T, V> andThen(Func<R, V> other) {
        return new Func<T, V>() {
            V apply(T x){
                R res = Func.this.apply(x);
                return other.apply(res);
            }
        };
    }

    Func<T, R> decompose() {
        System.out.println(this.prev);
        return this.prev.orElse(this);
    } 
}

// Func f = new Func(){
//     @Override
//     int apply(int x){
//         return x + 2;
//     }
// }

// Func g = new Func(){
//     @Override
//     int apply(int x){
//         return x * 2;
//     }
// }

// Func<String, Integer> f = new Func<String, Integer>() {
//     Integer apply(String x) {
//         return x.length();
//     }
// };

// Func<Integer, Integer> g = new Func<Integer, Integer>() {
//     Integer apply(Integer x) {
//         return x * 2;
//     }
// };