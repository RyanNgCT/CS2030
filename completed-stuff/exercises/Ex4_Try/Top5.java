import java.util.List;
import java.util.stream.IntStream;

class Top5 {
    private final List<String> list;
    private static final int top = 5;

    Top5() {
        this.list = List.<String>of("", "", "", "", "");
    }

    Top5(List<String> list) {
        this.list = list;
    }

    Try<Top5> add(int i, String str) {
        return Try.<Top5>of(() -> {
            if (i < 0 || i >= this.list.size()) {
                throw new IllegalStateException("index " + i + " out of range");
            }
            List<String> updatedList =
                IntStream.rangeClosed(0, top - 1)
                    .boxed()
                    .map(x -> (x == i) ? str : this.list.get(x))
                    .toList();
            return new Top5(updatedList);
        });
    }

    Try<String> get(int i) {
        return Try.<String>of(() -> {
            if (i < 0 || i >= this.list.size()) {
                throw new IllegalStateException("index " + i + " out of range");
            }

            return this.list.get(i);
        });
        // return this.list.stream()
        //         .skip(i)
        //         .findFirst()
        //         .orElse("");
    }

    Try<Integer> find(String s) {
        return Try.<Integer>of(() -> {
            int idx = this.list.indexOf(s);

            // cannot find the element
            if (idx == -1) {
                throw new IllegalStateException(s + " not among top 5");
            }

            return idx;
        });
    }

    public String toString() {
        return this.list.toString();
    }
}
