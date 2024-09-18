import java.util.Collection;
import java.util.PriorityQueue;

record Pair(Integer t, PQ u){}

class PQInt {
    private final PriorityQueue<Integer> pq;

    PQ() {
        // let the internal PQ do it (delegation)
        this.pq = new PriorityQueue<Integer>();
    }

    PQ(Collection<Integer> source) {
        this.pq = new PriorityQueue<Integer>(source);
    }

    PQ add(Integer ele){
        // create a new copy and pass in the internal PQ (the copy is an immutabl version)
        PQ copy = new PQ(this.pq);
        copy.pq.add(ele);

        return copy;
    }

    // polling -> give an element out of the PQ; also need to return the resulting PQ
    Pair poll(){
        PQ copy = new PQ(this.pq);

        Integer ele = copy.pq.poll();

        return new Pair(ele, copy);
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
