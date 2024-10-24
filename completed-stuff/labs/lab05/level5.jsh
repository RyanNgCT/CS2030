Log<Integer> sum(int n) {
    if (n == 0) {
        return Log.<Integer>of(0, "hit base case!");
    }
    else {
        return sum(n - 1)
                .map(ele -> ele + n)
                .flatMap(log -> Log.<Integer>of(log, "adding " + n));
    }
}

Log<Integer> f(int n) {
    if (n == 1){
        return Log.of(1, "1");
    }
    else if (n % 2 == 0){
        // prepend the log entry before the recursive call
        return Log.<Integer>of(n / 2, n + " / 2").flatMap(x -> f(n / 2));
        
        //return f(n / 2).flatMap(log -> Log.<Integer>of(log, n + " / 2"));
    }
    else {
        return Log.<Integer>of(3 * n + 1, "3(" + n + ") + 1").flatMap(x -> f((3 * n) + 1));
        
        //return f(3 * n + 1).flatMap(log -> Log.<Integer>of(log, "3(" + n + ") + 1"));
    }
}

