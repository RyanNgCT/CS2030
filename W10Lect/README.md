# Week 10 Lecture -- Parallel Streams
## Learning Outcomes
- stream pipeline should be **inherently parallel** $\implies$ must assume it is parallel
- streams are not only about looping, it is also about parallelizing tasks
	- Streams has an in-built parallel processing processing algorithm

- not every problem is to be solved with parallel solutions $\implies$ make use of parallel streams only when it is beneficial to do so

- need to keep in mind that we might be passed a parallel stream to work with
	- have certain considerations to take care of

- What are stateless operations and stateful operations
	- stateless operations suit streams very well
	- **stateless operations** -- independent elements that don't depend on each other
	- **stateful operations** -- elements are not created as independent, requires knowledge about neighboring elements

What are non-interfering streams
- do not have any side effects and modification on the other stream types

Looking at the `reduce()` terminal operation $\implies$ associative accumulation function
- accumulator and combiner rules

Appreciate the overhead of parallelizing tasks and what tasks that we should parallelize
- utilizing multi-cores of the machine

```java
jshell> ForkJoinPool.getCommonPoolParallelism()
$1 ==> 15
```

## Concurrency vs Parallelism
- single core machine executes *one process at one time*
- context switching allows for multi-tasking $\to$ single CPU core has multiple threads
	- each thread can delegate work to different tasks / instructions
	- we have threads that are spawned to work on individual tasks

- seems as if it is working in parallel, but it is not in actuality

**Concurrent Programs**
- Run concurrently using threads

**Parallel Computing**
- multiple subtasks running at the same time
- parallel programs are concurrent, but not all concurrent programs are parallel

### From Sequential to Parallel
- **Concurrent**: the OS is the one that switches between the threads
	- at any instance of time, there is only one thing that **one particular thread** is doing at a time

![sequential-concurrent-parallel](../assets/sequential-concurrent-parallel.png)

Level of Parallelism can be controlled using the `-D` flag.
```bash
$ java -Djava.util.concurrent.ForkJoinPool.common.parallelism=3 ...

$ jshell -R -Djava.util.concurrent.ForkJoinPool.common.parallelism=3 ...
```

- To "switch on" parallel, we use the `parallel()` method
	- can be anywhere **between the initial / source to the terminal operators**
		- the effect is exactly the same!
	- Streams are declarative and checks for parallelism in operations **only when** it reaches the **terminal operator**
	- There is a notion of **element order** $\to$ certain operations enable one to maintain the order, while others cannot
		- brings back the point of associative versus non-associative operations (where order may matter)

- To "switch off" parallel, we use the `sequential()` method
	- ⚠️ *Note:* never take a stream from others that has parallelism enabled and put `sequential()` $\implies$ turning off the parallelism, which does not make any sense

![sequential-factory-methods](../assets/sequential-factory-methods.png)

### `forEachOrdered()`
- during termination, elements are processed in encounter order (how the elements are ordered by the source operation)

### Watching / Debugging Parallel Streams
- cannot control what is done when in parallel streams, but we still can debug these streams
- We can slow down the execution of the parallel stream using `Thread.sleep()`

## Parallel Streams
- Streams are *inherently parallelizable*
- able to perform reduction of stream elements using a divide and conquer strategy (i.e. the normal convention of left-reduction versus the other possibility of right-reduction $\to$ see recitation 09)
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

---
## Association Accumulation Function
- `reduce()` has 3 different possible types
	1. reduce with an identity and `BinaryOperator` (final value is an aggregated `T` value)
	
	2. reduce with **no seed** (returns an `Optional<T>` $\implies$ when stream is empty, return `Optional.empty`)
		- quite typically used
		- `reduce()` with the use of only a `BiFunction` or accumulator

	3. reduce with an identity, a`BiFunction` and a `BinaryOperator` $\implies$ when we want to convert types, but we can do away with this
		- should be used when doing things in parallel
		- ⚠️ not really good to use to convert types  (i.e. transform from element type to seed's type) $\implies$ better practice to use `map()` instead in this case


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
	- we **cannot assume** that the reduction is done nicely (`24/1`, then `24/2`, then `12/3`, then `4/4`) $\implies$ sequential execution from one to another to ensure correctness

- multiplication and addition is associative, but *division is not associative*!
	- this will affect the reduction (is not done from left to right)

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

- identity are the **same type** that we are combining with (when using `reduce()`) $\implies$ take in type `T` seed and type `T` values


### Overhead of Parallelism
- time it takes to handle or manage the threads is taking much more overhead and time compared to just running the trivial task itself
	- only has benefit when it is a heavy task, might not be advisable to use for trivial tasks

- have to identify the correct task to parallelize 