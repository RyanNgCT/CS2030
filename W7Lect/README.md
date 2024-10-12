# Lecture 7 - Contexts
## What is a Computational Context
- for every context, the pattern or thought process in creating it is the same.

- Contexts provides a **service** to the client
	- wraps around some value and **abstracts away computations** associated with the particular context
	- sandbox that allows functions to be safely executed
	- example: `Optional` $\implies$ context that handles invalid or missing values

- only wraps around **one** value

- need to test every branch in the methods of contexts

### Components of a Context
- a way to wrap the value within the box (i.e. using the *factory methods*, i.e. `of`)
```java
jshell> Optional<Integer> optInt = Optional.<Integer>of(1);
optInt ==> Optional[1]

jshell> Optional<Integer> optInt = Optional.<Integer>empty();
optInt ==> Optional.empty
```

- a way to pass behaviour into the box with a **higher order function**
	- have the client pass in what they wish to do (i.e. `map()`, `filter()`, `reduce()`).

- How does the client interact with the context $\implies$ using cross-barrier manipulation
	- clients should be able to tell the functionality that they require

### Cross-barrier Manipulation
Describes how a client would interact with a context
- client passes the value to context and context manipulate on the client's behalf (doing it through the `filter()` method).

## Maybe
- mimics Java's optional, which is a computational context $\implies$ meant to do something for the user
	- implements all the methods that an optional may have.
```java
// type declaration
class Maybe<T>{

}
```

**Maybe** is a class that takes in values of any type (i.e. `Maybe<T>`, so it can take in `String`, `Double`, `Integer` etc.)
- instantiating maybe $\implies$ don't mention the name itself? (i.e. ❌ `new Maybe(...)`)

```java
// two ways to instantiating the Maybe context
Maybe.<Integer>of(1);
Maybe.<Integer>empty();
```

### `.of()`
Hence we use the `of()` method
- `of` method is a class method and is a static method
	- Constructor should be private (use `.of` and not `new Maybe<T>(...)`)
		- restrict situations where we don't want maybe to be created
	- have error handling within `of` method itself $\implies$ check if value is null

- static method does not have a chance to be bounded

![[Pasted image 20240930122422.png]]
- `static<T>` indicates a generic method
- `Maybe<T>` is the return type (maybe wrapped around type T)
- `T value` is the parameters accepted by the `of` method

![[Pasted image 20240930122431.png]]
- you are making the optional empty so inside cant be empty
### Equals
![[Pasted image 20240930123532.png]]

- `this.get()` and `other.get()` retrieves the value itself
- Can indicate maybe of any type using `Maybe<?>`


mapper is also a producer


### Flatmap
- does not allow for `Optional.<Integer>of(1).flatMap(x -> x + 1)`
- lambda function in flatMap itself must have the same type as the one we started with

### Supplier
- supplier provides us with lazy evaluation (i.e. Streams)

###  Consumer
- producer extends consumer super

---
## Eager versus Lazy Evaluation
What is eager evaluation???

### Effects of Lazy Evaluation
- instead of relying on streams to provide the laziness, we want to ourselves create something (a context) that provides lazy evaluation