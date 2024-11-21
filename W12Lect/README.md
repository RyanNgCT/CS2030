# Lecture 11 -- Asynchronous Programming
## Comparing Async to Parallel Programming
- In Parallel Programming, we work with parallel streams and all our threads are doing the same work, on different parts of the problem

- Asynchronous Programming is more **general** $\implies$ involves different workers doing things, but each worker may not be doing the same thing (instead of multiple workers executing different instructions)
	- useful when dealing with latency issues (i.e. waiting quite a while for something $\implies$ i.e. a response from the client browser to be sent back to the code)

### Asynchronous Programming as a Context
We will take a look at promises (need some time to complete)
- Context of `CompletableFuture` will help us do async programming
	- functions that we have deal with in other contexts like mapping and flatmap-ing will also surface.
	- Asynchronous Computations can make use of Java's `CompletableFuture<T>` interface / context

We will look at async programs spawning other processes (**fork**ing)
- a process get spawned off to different processes and they need to be joined back into a single process

Asynchronous programs are the idea of having two or more processes done at the same time
## Fork and Join
We refer to the computation graph shown below
- the output value of  $f$ will be passed to $g$ and $h$.
	- we fork the task $g$ to execute in conjunction with $h$ and then join back task $g$ later on

- If $g$ and $h$ *don't have side effects* (which they shouldn't have), they don't depend on any external states and **do not modify external state**
	- both $g$ and $h$ can work independent of each other but both require an argument of $b$, i.e. $g(b)$ and $h(b)$, **and should be done at the same time**

- note that functions $g$ and $h$ are associative (i.e. either one can come first, yet this has no effect on the result)
	- but we are actually coding "imperatively", we need to specify which one takes precedence $\implies$ why not we do $g$ and $h$ at the same time?

- the "extra task" spawned off another thread is $g$, the *main thread* will do the `join()` operation

![Fork-Join-chart](../assets/Fork-Join-chart.png)

### Sequential Processing *without* Asynchronism
```java
void foo(int m, int n) {
    // do f for 5 seconds
    B b = f(new A(5));
    
    // g is done for "m" seconds
    C c = g(b, m);
    D d = h(b, n);
    E e = n(c, d);
}
```

As mentioned above, we can do $g$ and $h$ together to save the time (to do both), instead of doing it sequentially.

### Using Threads
```java
void foo(int m, int n) {
    // do f for 5 seconds
    B b = f(new A(5));
	Thread t = new Thread(() -> g(b, m));
	t.start(); // starts the thread

    D d = h(b, n);
	System.out.println("n proceeds");
}
```
#### Runnable as an argument to `Thread()`
- runnable is a functional interface of thread and has an **abstract method of `run()`**
	- quite similar to Supplier's `get()`, as it takes in no arguments $\implies$ provides a value back
	- `run()` returns no value (i.e. `void`) 

- cannot be effect free in threads (need to **change some external state** via the `run()` lambda to say that the result is there)
#### `foo()`
- if `g()` takes a shorter time than `h()` and the main thread is waiting for `g()`, `g()` doesn't have to be a chance of returning (see the output below)
	- make use of **`join()` to wait for all threads to finish**
```java
jshell> foo(5, 10)
f: start
f: done
h: start
g: start
g: done
h: done
n proceeds

jshell> foo(10, 5)
f: start
f: done
h: start
g: start
h: done
n proceeds

jshell> g: done
```

### Busy wait
- use `while (t.isAlive()){...}` to compensate for the fact that $h$ finishes before $g$ and does not wait for $g$ to complete
	- keep calling the "dummy" method `doWork(1)` $\implies$ is **not the right way** (potentially can be doing other things instead)
```java
void foo(int m, int n) {
    // do f for 5 seconds
    B b = f(new A(5));
	Thread t = new Thread(() -> g(b, m));
	t.start(); // starts the thread

    D d = h(b, n);
	while (t.isAlive()) {
		doWork(1);
	}

	System.out.println("n proceeds");
}
```

### Blocking wait
- once the threat finishes, the thread should signal to us that it is done.
	- utilizing `t.join()`
	- prevent main thread from continue *until a signal is sent back*
- is a much better practice compared to using busy wait, but is still quite inefficient

## `CompletableFuture<T>`
- promises one a value, **if you want it later**
	- if we don't want it then it is wasted
### Creation of a `CompletableFuture` -- using `supplyAsync()`
- We use the `supplyAsync()` static factory method to allow us to have threads spawned for a specific process
	- *The bottom line:* `supplyAsync()` is the **wrapper** for the `CompletableFuture` context
	- takes in a Supplier $\implies$ make it such does the context can do another thing
	- spawn off process in a thread, the thread starts to process immediately (so no time is wasted)

- `supplyAsync()` achieves the purpose of effect-free programming by returning some value
	- syntax is as below. We want the computation of `f(new A(5))` to be given to the `CompletableFuture` context while we do something else

```java
jshell> CompletableFuture.<B>supplyAsync(() -> f(new A(5)));
f: start
$13 ==> java.util.concurrent.CompletableFuture@2b9627bc[Not completed]

jshell> System.out.println("do something")
do something

jshell> System.out.println("do something")
do something

jshell> System.out.println("do something")
do something

jshell> f: done
```
In the above, we use the print line to illustrate we can be doing something while the computation is being done until the computation is complete

**Proper method**
- can then issue `CompletableFuture.join()` method to wait for the thread to finish before continuing on

```java
CompletableFuture.<B>supplyAsync(() -> f(new A(5))).join();
```
### Creation of a `CompletableFuture` -- using `runAsync()`
- another alternative method is `runAsync()` that takes in a Runnable
	- recall from below that we should have `thenRun()` and `thenRunAsync()` for separate instances of the `CompletableFuture` to fully maximize Asynchronism (spawn in separate threads)

- we can pass in a Runnable which is sort of *similar to a consumer*

## Callbacks
We spawn a thread to do $f$ and after it finishes $f$, we do $g$ immediately $\implies$ have to tag on something to the pipeline

#### `thenApply()` 
- is the map for `CompletableFuture` context
- `thenApplyAsync()`: executing functions 
- utilizing the `thenApply()` method $\to$ is actually a `map()`
- `thenApply()` takes in a function and returns a `CompletableFuture`
	- the starting of execution of function $g$ is done *automatically* by the context
```java
CompletableFuture.<B>supplyAsync(() -> f(new A(5))).thenApply(b -> g(b, 5));
```

- callback is a piece of code that will be called after some other code is done 
	- a .k. a. a continuation $\implies$ passed to something else to compute **after it is done**

![cf-thenApply](../assets/cf-thenApply.png)

#### `thenCompose()`
- is like the "`flatMap()`" method, but in this case in the context (pun intended) of `CompletableFuture`
```java
<U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn)
```

#### Application of Laws
`thenApply()` and `thenCompose()` also conform to functor and monad laws, as do `map()` and `flatMap()` in Streams.

#### `thenAccept()`
- `forEach()` of CFs
- If we want a callback that doesn't return a value, then we will provide an argument as a `Consumer`.

#### `thenCombine()`
- like `reduce()`
- takes in the result of `CompletableFuture.thenApply()` and how you combine them

#### `join()`
- returns the type of the value inside (i.e. `T`)
- should only be using once (i.e. make sure we only use `thenApply()` inside `thenCompose`)

```java
E fooAsync(int m, int n) {
    CompletableFuture<B> cfb = CompletableFuture.<B>supplyAsync(() -> f(new A(5)));
    CompletableFuture<C> cfc = cfb.thenApply(b -> g(b, m));
	
	// spawn another thread using thenApplyAsync()
    CompletableFuture<D> cfd = cfb.thenApplyAsync(b -> h(b, n));
    CompletableFuture<E> cfe = cfc.thenCombine(cfd, (c, d) -> n(c, d));

    return cfe.join();
    
    // E e = n(c, d);
}
```

We can also alternatively do `cfe` as:
- we need `thenCompose()` and `thenApply()` inside sorta like `flatMap()` and then using map inside
```java
CompletableFuture<E> cfe = cfc.thenCompose(c -> cfd.thenApply(d -> n(c, d)));
```
### Converting Synchronous Code to Asynchronous
- instead of using `supplyAsync`, we can just use a `completedFuture()` for **already computed values**
	- somewhat like the `of()` factory method

```java
if (x < 0) {
	return CompletableFuture.<Integer>completedFuture(0);
}
```

Two types of wrapper
1. for work (supplier), which we spawn off in a thread to do
2. to wrap a value that has already been completed (see above code)

can use `thenApply()` when dealing with combinations of synchronous and asynchronous versions (i.e. implies the need to have a `join()` to wait for it to finish)
- `foo()` is asynchronous, `bar()` is synchronous
```java
foo(5).thenApply(x -> bar(x)).join();
```

can use `thenCompose()` or `flatmap` when dealing with **two or more** asynchronous functions
```java
foo(5).thenCompose(x -> bar(x)).join();
```