import java.util.function.Predicate;

class A {
    private final int z;

    A(int z) {
        this.z = z;
    }

    Predicate<Integer> foo(int y) {

        // is the same as writing "return x -> x == y + z;"
        return new Predicate<Integer>() {
            @Override
            public boolean test(Integer x) {
                return x == y + z;
            }
        };
    }
}