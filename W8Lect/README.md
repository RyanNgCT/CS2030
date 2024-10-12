# Lecture 8 - Functional Programming
- what are currying functions (i.e. functions with multiple arguments)
- what is closure and variable capture
- ability to perform function composition
## Function
- a function is a mapping of a set of values/elements to a set of outputs within a co-domain / range
	- every input has exactly one output
	- multiple inputs can be mapped to the same output (i.e. $-2^2$ and $2^2$ is 4)
	- not all values in the co-domain are mapped

i.e. `x -> x | y` (for y $\ne$ 0)
- need to extend the co-domain to include the `y`-value of zero $\implies$ using optionals
- make the co-domain defined such that the outcome of the mapping of every element will end up inside the co-domain

### Pure Function
- takes in argument(s) and returns a *deterministic value* **every time**
- must be effect free (does not change anything outside of it)
	- modifying *external state*
	- program input/output (rather the practice is to collect all the outputs via `toString()`)
	- throwing exceptions $\implies$ is a type of side-effect

*Next Week: Lecture on Exception Handling*
- downstream mod requires exception handling
- context that handles exception handling to remove side-effects
	- Writing `Try` class 

Absence of side-effects is a necessary condition for *referential transparency*
- overflow results in the same number produced (is pure, but not correct), (a) is pure
- zero division exception is a side effect, (b) is not pure
- (c) is not pure as we are changing external state $\implies$ physically modifying the list, not returning a new object
- (d) is pure/not pure depends on whether the class containing it is pure

## Higher Order Functions
- need HOF to support calling with args and functions, as well as returning functions
- functions can be treated as a value based on their return type.

Function taking in another function as its input
![[Pasted image 20241007121958.png]]

`reduce()` method
- `BinaryOperator<T>` is found in this method (taken in as an arg)
- takes in type `T` and returns type `T`

- `BinaryOperator` is a sub-interface of `BiFunction`
	- `BiFunction<T, U, R>` takes in two input (T and U) and give out one input (R)

### Curried Functions
- no need for tri-/quad-functions since we can utilize function currying
	- can use `f.apply(1).apply("string")`
		- `f.apply(1)` is a partial application 

- piggy-bagging arguments
	- one of the argument is a function

i want to die lah prof

### Partial Application - under the hood
- need to have the apply method since it is an implementation of the `Function<T, R>` interface $\implies$ implementation of abstract method `.apply()`

```
func = x -> y -> x + y
```

- `x` is scoped in the "outside" `.apply()` two layers up
	- we are creating a method inside a method
	- the `x` is kind of "crossing boundaries", it is defined in the outer function but crosses into the inner function

> *def:* An instance of a local class is returned as a closure (is nothing more than an object)
- returning an instance of a local class (a.k.a. closure), in this case its returning Predicate

`Predicate.test()` enclosed within Predicate method `foo()`
- knows the properties of its enclosing method

`Predicate.test()` enclosed within class `A`
- knows the properties of the enclosing class (i.e. `this.x`)

```java
x -> x = y + z // from where one???
```
- `y` and `z` are determined by methods or classes *enclosing the local class*
	- obtained from external constructs (i.e. was already set somewhere)

### Variable Capture
- in order for the y value to be known, it has to make a copy of the value `y`

- captures the reference of the enclosing class
	- value of `y` is captured
```java
Predicate p1 = new A(1).foo(2)
```

The reference is called `A.this()` $\implies$ using a qualified list (i.e. to reference the `z` initialized in class A, we use `A.this.z`).

## Composing Functions
- using `compose()`
- alternative composition that is more natural using `andThen()`
	- apply through `f` first, `andThen(g)`

- there are defined methods in the interface (Java interfaces are impure)
	- using `default` $\implies$ legacy compatibility issues

### Under the Hood: Composition
What actually happens to compose and how do we define it?
- function needs to be an abstract class

![func-compose-imp](../assets/function-compose-imp.png)

- need to use `Func.this.apply()` to access the `abstract int apply()`


### Lazy Evaluation with Function Composition
- Streams itself is lazily evaluated (but we can use methods to force out the value) $\implies$ terminal operator forces out the value
	- `reduce()` enables one to obtain the result???
	- using `apply()` also enables the function to be evaluated

- function composition is associative


**What should we do?**
```java
jshell> Stream.<String>of("one", "two", "three").
   ...> map(x -> x.length())
$1 ==> java.util.stream.ReferencePipeline$3@a67c67e

jshell> Stream.<String>of("one", "two", "three").
   ...> map(x -> y -> x.length() + y)
|  Error:
|  incompatible types: cannot infer type-variable(s) R
|      (argument mismatch; bad return type in lambda expression
|        java.lang.Object is not a functional interface)
|  Stream.<String>of("one", "two", "three").
|  ^----------------------------------------...
```

### Identity Function
i.e. `x -> x` (but can't do like this because cannot reconcile types)
- can use an identity method

![[Pasted image 20241007132928.png]]


$$
A_k = \{n \in \mathbb Z^+ : ( n \le k) \: \wedge \: (n = 3k for \: some \: k \in \mathbb Z)  \}
$$

$$
\begin{aligned}
\{3q + 0: q, d \in \mathbb Z\}
\newline
\newline
\{3q + 1: q, d \in \mathbb Z\}
\newline
\newline
\{3q + 2 : q, d \in \mathbb Z\}
\newline
\newline

\cup_{i=4}^{14}A_{k}
\end{aligned}
$$