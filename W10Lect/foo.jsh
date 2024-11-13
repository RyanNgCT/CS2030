boolean isPrime(int n) {
        return n > 1 && IntStream.range(2, n).noneMatch(x -> n % x == 0);
}

void foo() {
    List<Integer> list = List.of(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
    List<Integer> result = new ArrayList<Integer>();

    // list.
    //     stream().
    //     parallel().
    //     filter(x -> isPrime(x)).
    //     forEachOrdered(x -> result.add(x)); // we first used forEach -> result in incorrectness


     result = list.
        stream().
        parallel().
        filter(x -> isPrime(x)).
        toList();
        
    System.out.println(result);
}
