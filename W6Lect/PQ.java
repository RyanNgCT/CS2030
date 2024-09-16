import java.util.Collection;
import java.util.PriorityQueue;

class PQ {
    private final PriorityQueue<Integer> pq;

    PQ() {
        this.pq = new PriorityQueue<Integer>();
    }

    PQ(Collection<Integer> source) {
        this.pq = new PriorityQueue<Integer>(source);
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
