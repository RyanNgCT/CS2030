import java.util.Optional;
import java.util.function.Function;

class Log<T> {
    private final T t;
    private final String log;

    static <T> Log<T> of(T t) {
        //if (t == null || t instanceof Log<T>){
        //    throw new IllegalArgumentException("Invalid arguments");
        //}
        //return new Log<T>(value);

        // use the second factory method to prevent orElseThrow repeat
        return Log.of(t, "");
    }

    static <T> Log<T> of(T t, String log) {
        // can have isLog -> check instanceof
        return Optional.ofNullable(t)
            .flatMap(x -> Optional.ofNullable(log))
            .map(x -> t)
            .filter(x -> !(x instanceof Log))
            .map(x -> new Log<T>(t, log))
            .orElseThrow(
                () -> new IllegalArgumentException("Invalid arguments"));
    }

    // == Level 2 ==

    <R> Log<R> map(Function<? super T, ? extends R> mapper) {
        return Log.<R>of(mapper.apply(this.t), this.log);
    }

    // == Level 3 ==

    <R> Log<R> flatMap(Function<? super T, ? extends Log<? extends R>> mapper) {
        // need to update the log -> append the log entry if have
        // if log is empty, we inherit the log

        Log<? extends R> other = mapper.apply(this.t);
        String otherLog = other.log;

        String newLog = "";

        if (this.log == "") {
            newLog = otherLog;
        } else if (this.log != "" && other.log == "") {
            newLog = this.log;
        } else if (this.log != "" && other.log != "") {
            newLog = this.log + "\n" + other.log;
        }

        // R res = mapper.apply(this.t).t;
        // return Log.<R>of(res, newLog);
        return Log.<R>of(other.t, newLog);
    }

    // == Level 4 ==
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Log<?> other) {
            if (this.t.equals(other.t) && this.log.equals(other.log)) {
                return true;
            }
        }
        return false;
    }

    private Log(T t) {
        this.t = t;
        this.log = "";
    }

    private Log(T t, String log) {
        this.t = t;
        this.log = log;
    }

    @Override
    public String toString() {
        if (this.log == "") {
            return "Log[" + this.t + "]";
        }

        return "Log[" + this.t + "]"
            + "\n" + this.log;
    }
}
