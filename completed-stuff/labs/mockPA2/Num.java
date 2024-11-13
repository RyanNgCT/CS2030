import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

class Num extends AbstractNum<Integer> {
    private Num(Integer i) {
        super(i);
    }

    private Num(Optional<Integer> oi) {
        super(oi);
    }

    private Num(AbstractNum<Integer> abs) {
        super(abs.opt);
    }

    static Num of(int t) {
        if (AbstractNum.valid.test(t)) {
            return new Num(Optional.<Integer>of(t));
        }
        return new Num(Optional.empty());
    }

    static Num zero() {
        return new Num(AbstractNum.zero());
    }

    static Num one() {
        // return new Num(1);
        return Num.zero().succ();
    }

    public Num succ() {
        // return AbstractNum.s.apply(super.opt);
        return new Num(super.opt.map(AbstractNum.s));
    }

    public Num add(Num other) {
        if (!this.isValid() || !other.isValid()) {
            return new Num(Optional.empty());
        }

        return Stream.iterate(Num.zero(), x -> !x.equals(other), x -> x.succ())
            .reduce(this, (x, y) -> x.succ());
    }

    public Num sub(Num other) {
        if (!this.isValid() || !other.isValid()) {
            return new Num(Optional.empty());
        }

        // Num where value is in super opt
        Num newNumber = new Num(other.opt.map(x -> super.n.apply(x)));
        return new Num(newNumber.add(this)).validateResult();

        // return other.opt.flatMap(x -> this.add(Num.of(AbstractNum.n.apply(x))));
        // Optional<Integer> toMinus = other.opt.map(x -> AbstractNum.n.apply(x));
        // return this.add(Num.of(toMinus));
    }

    public Num validateResult() {
        return new Num(this.opt.flatMap(
            x -> AbstractNum.valid.test(x) ? this.opt : Optional.empty()));
    }


    public Num mul(Num other) {
        // do this + this for "other" times
        if (!this.isValid() || !other.isValid()) {
            return new Num(Optional.empty());
        }

        return Stream.iterate(Num.zero(), x -> !x.equals(other), x -> x.succ())
            .reduce(Num.zero(), (x, y) -> x.add(this))
            .validateResult();
    }

    // public boolean validNum(Num other) {
    //     return other.opt.map(x -> AbstractNum.valid.test(x));
    // }
}
