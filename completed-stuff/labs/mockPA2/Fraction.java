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
        //Optional<Num> newNmtr =
        //    this.opt.flatMap(x -> other.opt.map(y -> x.t().mul(y.t())));

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
