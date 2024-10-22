abstract class Func<T, R> {
    abstract R apply(T x);

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
}

// Func f = new Func(){
//     int apply(int x){
//         return x + 2;
//     }
// }

// Func g = new Func(){
//     int apply(int x){
//         return x * 2;
//     }
// }
