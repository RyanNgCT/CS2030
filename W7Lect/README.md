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
- instantiating maybe $\implies$ don't mention the name itself? (i.e. ❌ `new Maybe<T>(...)`)
	- use the factory methods `.<T>of()` and `.<T>empty()`.

```java
// two ways to instantiating the Maybe context (similar to Optional)
jshell> Maybe.<Integer>of(1) // value of "1"
$6 ==> Maybe[1]

jshell> Maybe.<Integer>empty() // absence of value
$7 ==> Maybe.empty

jshell> Maybe<Integer> mayInt = Maybe.<Integer>of(1)
mayInt ==> Maybe[1]
```

### `.of()`
Hence we use the `of()` method
- `of` method is a class method and is a static method
	- Constructor should be **private** (*use `.of`* or using type inference and not `new Maybe<T>(...)`)
		- restrict situations where we don't want maybe to be created
	- have error handling within `of` method itself $\implies$ check if value is null

- static method does not have a chance for type `<T>` to be bounded
	- just focus on the method itself

**Breaking down this method**:
![factory-method-maybe-of](../assets/factory-method-maybe-of.png)
- `static` indicates a generic method
- `<T>` generic type declaration
- `Maybe<T>` is the **return type** (maybe wrapped around type T)
- method name is `of`
- `T value` is the parameters accepted by the `of` method

### `empty()`
![factory-method-maybe-empty](../assets/factory-method-maybe-empty.png)
- you are making the **optional empty** so inside cant be empty $\implies$ allowed to fill it with null, indicating absence of a value.

### `isEmpty()`
```java
// can be used by other functions to check if value stored is Maybe.empty
private boolean isEmpty() {
	return this.value == null;
}
```
- should make these helper methods **with the `private` access modifier** so that client's cannot use it.
	- only the **context itself** should be able to access them

### `get()`
```java
private T get() {
	return this.value;
}
```

### `equals()`
```java
@Override
public boolean equals(Object obj) {
	if (this == obj) {
		return true;
	}
	
	if (obj instanceof Maybe<?> other) {
		// can use the equals method after unboxing both values
		return this.get().equals(other.get());
	}
	return false;
}
```

- `this.get()` and `other.get()` retrieves the **value** stored in the Maybe itself
- Can indicate maybe of any type using `Maybe<?>`

- note that we **cannot** compare a `Maybe<Integer>` to an `int` in this case (as the `int` is not an instance of `Maybe<T>` in this case) 

##### Edge Case
- need to handle this (when comparing two empties of different types)
```java
jshell> Maybe<String> empStr = Maybe.<String>empty()
empStr ==> Maybe.empty

jshell> Maybe<Integer> empInt = Maybe.<Integer>empty()
empInt ==> Maybe.empty

jshell> empStr.equals(empInt)
|  Exception java.lang.NullPointerException: Cannot invoke "Object.equals(Object)" because the return value of "REPL.$JShell$15$Maybe.get()" is null
|        at Maybe.equals (#5:49)
|        at (#9:1)
```

##### Resolution
```java
 if (obj instanceof Maybe<?> other) {
	if (this.isEmpty() || other.isEmpty()) {
		return this.get() == other.get();
	}
	
	// can use the equals method after unboxing
	return this.get().equals(other.get());
}
```


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