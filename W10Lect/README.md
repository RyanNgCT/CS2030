# Week 10 Lecture -- Parallel Streams
## Learning Outcomes
- stream pipeline should be inherently parallel --> must assume it is parallel
- streams are not only about looping, it is also about parallelizing tasks
	- Streams has an in-built parallel processing processing algorithm

- not every problem is to be solved with parallel solutions $\implies$ make use of parallel streams if so

- need to keep in mind that we might be passed a parallel stream to work with
	- have certain considerations to take care of

- What are stateless operations and stateful operations
	- stateless operations suit streams very well
	- stateless operations -- independent elements that don't depend on each other
	- stateful -- elements are not created as independent, requires knowledge about neighbors

What are non-interfering streams

Looking at the `reduce()` terminal operation $\implies$ associative accumulation function
- accumulator and combiner rules

Appreciate the overhead of parallelizing tasks and what tasks that we should parallelize
- utilizing multi-cores of the machine

```java
jshell> ForkJoinPool.getCommonPoolParallelism()
$1 ==> 15
```

## Concurrency vs Parallelism
- single core machine executes one instruction at one time
- context switching allows for multi-tasking $\to$ single CPU core has multiple threads
	- can delegate threads to different tasks / instructions

- seems as if it is working in parallel, but it is not in actuality

**Concurrent Programs**
- Run concurrently using threads

**Parallel Computing**
- multiple subtasks running at the same time

### From Sequential to Parallel
- Concurrent: the OS is the one that switches between the threads
	- at any instance of time, there is only one think that it is doing

Level of Parallelism can be controlled using the `-D` flag.
```bash
$ java -Djava.util.concurrent.ForkJoinPool.common.parallelism=3 ...

$ jshell -R -Djava.util.concurrent.ForkJoinPool.common.parallelism=3 ...
```

- To "switch on" parallel, we use the `parallel()` method
	- can be anywhere, from the initial / source to the terminal operator
	- Streams are declarative and checks everything only when it reaches the terminal operator
	- There is a notion of element order $\to$ certain operations enable one to maintain the order, while others cannot

- To "switch off" parallel, we use the `sequential()` method
	- Note: never take a stream from others that has parallelism enabled and put `sequential()` $\implies$ turning off the parallelism

### `forEachOrdered()`
- during termination, elements are processed in encounter order (how the elements are ordered by the source operation)

### Watching / Debugging Parallel Streams
- cannot control what is done when in parallel streams, but we still can debug these streams
- We can slow down the execution of the parallel stream using `Thread.sleep()`

## Parallel Streams
- can use `IntStream`s as well

```java
jshell> IntStream.
   ...> range(0, 10).
   ...> map (x -> timed(x)).
   ...> forEach(x -> System.out.println(x))
```

- using `Fork.JoinPool... = 3`, we can have up to `4` tasks running at once
```java
jshell> IntStream.
   ...> range(0, 10).
   ...> map (x -> timed(x)).
   ...> forEachOrdered(x -> System.out.println(x))
```

- we can speed up using the intermediate operation portions


## Stateless versus Stateful Operations
- `map()`and `filter()` are subjected to the same predicate and hence are stateless operations
	- ideal to have operations that are all stateless in the Stream itself $\implies$ try to make streams as stateless as possible

- Stateful intermediate operations include `sorted()`, `limit()`, `distinct()`, which means that full parallelism is not exploited since there is some form of waiting
	- impedes the parallelism a little, although still can be present

- the printing step needs to ensure that the elements are sorted

## Correctness of Streams
- in streams, nothing in the pipeline should change the source $\to$ **should not be using** mutable types like `ArrayList`
	- stream operations **must not** interfere with stream data

- In streams, we would prefer operations to be stateless (i.e. using `map()` over `sorted()`) and **without any side effects**

```java
list.stream()
.parallel()
.filter(x -> isPrime(x))
.forEach(x -> result.add(x))
```

- if we have a pipeline with a side effect, we could have an incorrect answer
	- there might be instances where two process complete the computation and attempt to have their result added to `ArrayList`

- we can terminate with `toList()` instead (as it has no side-effects)

## Association Accumulation Function
- i.e. `reduce()`
	- reduce with an identity and `BinaryOperator` (final value is an aggregated `T` value)
	- reduce with no seed (returns an `Optional<T>` $\implies$ when stream is empty, return `Optional.empty`)
	- reduce with an identity, a`BiFunction` and a `BinaryOperator` $\implies$ when we want to convert types, but we can do away with this
		- should be used when doing things in parallel

![stream-reduction-methods](../assets/stream-reduction-methods.png)

```java
jshell> Stream.of(1.0, 2.0, 3.0, 4.0).
   ...> reduce(24.0, (x, y) -> x / y)
$4 ==> 1.0

jshell> Stream.of(1.0, 2.0, 3.0, 4.0).parallel().
   ...> reduce(24.0, (x, y) -> x / y)
$5 ==> 1.5
```
- sequential stream's result is always correct using the `reduce()` method
- parallel stream's result is wrong???
	- we cannot assume that the reduction is done nicely (24/1, then 24/2, then 12/3, then 4/4)

- multiplication and addition is associative, but *division is not associative*!

- function compositions are also associative!


We need to be careful when picking portions of the stream in which we want to exploit parallelism


### The 2-argument reduce
```java
reduce(T identity, BinaryOperator<T> accumulator)
```

makes use of 
```java
reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)
```

Misusing the 3-argument reduce (**should not** do this way)!
```java
Stream.of("one", "two", "three")
.parallel()
.reduce(0, (x,y) -> y.length() + x, (x, y) -> x + y);
```

In the accumulator, the identity value doesn't change

#### Stream
The stream is partitioned for each worker to do the combining in `reduce()`.
The stream is partitioned as a tree where there are no crossing lines (to prevent double accumulation).

## Accumulator and Combiner Rules
- don't perform typecasting within the combiner method

- identity are the same type that we are combining with (when using `reduce()`)


### Overhead of Parallelism
- time it takes to handle or manage the threads is taking much more overhead and time compared to just running the trivial task itself
	- only has benefit when it is a heavy task, might not be advisable to use for trivial tasks

- have to identify the correct task to parallelize 