ForkJoinPool.getCommonPoolParallelism();
<T> T timed(T t) {
    try {
        System.out.println(t + " : " + Thread.currentThread().getName());
        Thread.sleep(500);
    } catch (InterruptedException ex) {}

    return t;
}
