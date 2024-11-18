```java
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
}
```

```java
import java.util.Optional;
import java.util.stream.Stream;

class Fraction extends AbstractNum<Frac> {
    private Fraction(Frac fr) {
        super(Optional.<Frac>of(fr));    // alternatively n, d
    }

    // accommodate for invalid values in numerator and denominator
    private Fraction(Optional<Frac> optFr) {
        super(optFr);
    }

    static Fraction of(Integer n, Integer d) {
        if (!AbstractNum.valid.test(n) || !AbstractNum.valid.test(d) ||
            AbstractNum.zero().equals(Num.of(d))) {
            return new Fraction(Optional.empty());
        }
        return new Fraction(Frac.of(Num.of(n), Num.of(d)));
    }

    public Fraction add(Fraction other) {
        Optional<Frac> newFraction = this.opt.flatMap(
                x
				-> other.opt
					.map(y
						-> Frac.of((x.t().mul(y.u())).add(x.u().mul(y.t())),
									x.u().mul(y.u()))));

        return new Fraction(newFraction);
    }

    public Fraction sub(Fraction other) {
        Fraction newFrac = new Fraction(this.opt.flatMap(
			x
			-> other.opt
				.map(y
					-> Frac.of((x.t().mul(y.u())).sub(x.u().mul(y.t())),
								x.u().mul(y.u())))));
        
        Fraction updated = this.validate(newFrac);

        return updated;
    }

    public Fraction mul(Fraction other) {
        Optional<Frac> newFraction = this.opt.flatMap(
                x
				-> other.opt
					.map(y -> Frac.of(x.t().mul(y.t()), x.u().mul(y.u()))));

        return new Fraction(newFraction);
    }

    public Fraction validate(Fraction fr) {
        return new Fraction(fr.opt.flatMap(
			x
			-> x.t().opt.flatMap(
				tVal
					-> x.u().opt.flatMap(uVal
						-> AbstractNum.valid.test(tVal) && 
						AbstractNum.valid.test(uVal) 
						? Optional.of(Frac.of(x.t(), x.u()))
						: Optional.empty()))));
    }
}
```