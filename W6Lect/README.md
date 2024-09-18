# Week 6 - Java Generics
- in its simplest form, it is invariant -> can only have PQ<Shape> accept Collection<Shape>

- why we use our own Priority Queue
    - Java's implementation of `PriorityQueue<E>` is mutable (we don't use mutable stuff in class)
    - `<E>` has to be the primitive type

- mutating structure
- using `pq.poll()` returns null

## Creating our own PQueue
- hidden from the client is actually Java's implementation of the mutable Priority Queue.
- doing this for quick prototyping (something that works with nice design)

- dedicate the PQueue class for the `Integer` class type

## Delegation pattern
- make an immutable PQ off the original mutable PQ provided by Java

## Functionality
- original `PQ.add()` -> add the thing inside
- our implementation -> create a copy, modify it and then return a copy

### Poll
- new `PQ` returns modified PQ and int
- using the new PQ allows one to have pipelining

## Using generics
Allows the PQ class to take in different class type.

### Conventions
`E` - element
`V` - values
`R` - return type
`T` - type

- jshell -> do type declaration in method scope

### PQ Empty
- original PQ method return null if empty

### Polymorphism using type `E`
What makes the types admissible --> the class type `E` must be comparable

Use `E extends Comparable<E>` to restrict types

## Upper bounded Wildcard
- Enables the abstract class to take in anything below it (classes and subclasses).
    - use `<? extends E>` or "any extends E"

- allows PQ<Shape> to accept both List.of(Circle) and List.of(Rectangle)

## Lower bounded Wildcard
- Circle does not implement Comparable<Circle> but only implements Comparable<Shape>

- enables things above Circle to be admissible
    `E extends Comparable<? super E>` -- also termed "any super"


- PECS
Function consumes T (hence super)
Function produces something (hence extends)

Comparator/Comparable consumes
Collection produces

- List.of() or PQ.of() is called from the class -> does not know what is E that is declared (use static keyword)

## type witnessing
- explicitly states the type (don't leave it to type inference)
    - `PQ.<String>of().add("1")`
    - `PQ.<Integer>of().add(2)`