import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Optional;

record Pair<T, U>(T t, U u){}

// String or Integer which implements Comparable<String> and Comparable<Integer>
class PQ<E extends Comparable<E>> {
    private final PriorityQueue<E> pq;

    PQ() {
        // let the internal PQ do it (delegation)
        this.pq = new PriorityQueue<E>();
    }

    PQ(Collection<E> source) {
        this.pq = new PriorityQueue<E>(source);
    }

    PQ(Comparator<E> cmp){
        this.pq = new PriorityQueue<E>(cmp);
    }

    PQ<E> add(E ele){
        // create a new copy and pass in the internal PQ (the copy is an immutabl version)
        PQ<E> copy = new PQ<E>(this.pq);
        copy.pq.add(ele);

        return copy;
    }

    // polling -> give an element out of the PQ; also need to return the resulting PQ
    Pair<Optional<E>, PQ<E>> poll(){
        PQ<E> copy = new PQ<E>(this.pq);

        Optional<E> ele = Optional.ofNullable(copy.pq.poll());

        return new Pair<Optional<E>, PQ<E>>(ele, copy);
    }

    boolean isEmpty() {
        return this.pq.isEmpty();
    }

    int size() {
        return this.pq.size();
    }

    @Override
    public String toString() {
        return this.pq.toString();
    }
}
