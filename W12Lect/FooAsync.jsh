E fooAsync(int m, int n) {
    CompletableFuture<B> cfb = CompletableFuture.<B>supplyAsync(() -> f(new A(5)));
    CompletableFuture<C> cfc = cfb.thenApply(b -> g(b, m));
    CompletableFuture<D> cfd = cfb.thenApplyAsync(b -> h(b, n)); // spawn another thread
    // CompletableFuture<E> cfe = cfc.thenCombine(cfd, (c, d) -> n(c, d));

    CompletableFuture<E> cfe = cfc.thenCompose(c -> cfd.thenApply(d -> n(c, d)));

    return cfe.join();
    
    // E e = n(c, d);
}
