# Streams
- bring functional programming to Java

- consists of:
    - a source (i.e. `range`, `rangeClosed` etc.)
    - ≥ 0 intermediate operations
    - a terminal operation

## Stream Source
- can be created from Collections, Lists, sets, ints, doubles, arrays, lines of a file etc.

## Stream operations
- either intermediate (`filter`, `map`, `sort` etc., to return a stream -> allows chaining) or terminal operations (`forEach`, `collect`, `reduce` -> either `void` or non-stream return value).

1. Intermediate Operations - zero or more
    - order of these operations matter for large datasets (generally advised to use `filter` before `sort` or `map`). 

2. Terminal operation - only one is allowed
    - `forEach()` -> apply same function across multiple elements (to each element)
    - `collect()` -> saves elements into a collection
    - `count()`, `max()`, `min()`, `reduce()`, `summaryStatistics()`

### Advantages of Streams
- efficiency
- make heavy use of lambda expressions (a.k.a. "disposable" functions)
- `ParallelStreams` -> allow for easy to implement multi-thread operations
