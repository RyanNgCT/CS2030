double reduceFunc(Stream<Double> st) {
    return st.parallel().reduce(24.0, (x, y) -> x / y);
}
