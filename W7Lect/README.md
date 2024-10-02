# Lecture 7 - Contexts
## What is a Computational Context
- provides a service to the client (a sandbox)
	- wraps around some value and abstracts away computations associated with the particular context

- only wraps around **one** value

- need to test every branch of contexts

### Components
- a way to wrap the value within the box (i.e. using the factory methods, i.e. `of`)
- a way to pass behaviour into the box with a higher order function

- How does the client interact with the context $\implies$ using cross-barrier manipulation
	- clients should be able to tell the functionality that they require

### Cross-barrier Manipulation
- client passes the value to context and context manipulate on the client's behalf (doing it through the `filter()` method).

## Maybe
- mimics optional, which is a computational context $\implies$ meant to do something for the user
```java
// type declaration
class Maybe<T>{

}
```

- creating maybe $\implies$ don't mention the name itself?

```java
// two ways to instantiating the Maybe context
Maybe.<Integer>of(1);
Maybe.<Integer>empty();
```

- `of` method is a class method and is a static method
	- Constructor should be private (use `.of` and not `new Maybe<T>(...)`)
		- restrict situations where we don't want maybe to be created
	- have error handling within `of` method itself $\implies$ check if value is null

- static method does not have a chance to be bounded

![[Pasted image 20240930122422.png]]
- `static<T>` indicates a generic method
- `Maybe<T>` is the return type (maybe wrapped around type T)
- `T value` is the params accepted by the `of` method


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


What is eager evaluation???