# Lecture 5 - Inheritance and Substitutability
- inheritance supports the `is-a` relationship (implementing interfaces)
- is about classes now
### Principles to follow
1. *Don't Repeat Yourself*
	- if we need to have duplicated quote, it should just reside in one location/place (i.e. one file only)

2. *Abstraction Principle*
	- each significant piece of functionality should be implemented in **one place** in the source code
	- combine codes with similar functionalities by **abstracting out the varying parts**
	- example: using `map()` in Streams
		- go through every element and do something (perform a mapping)
		- varying parts refers to the Predicate or function that is passed into the `map()` function

### Defining Sub-classes
- sub-classes are very similar to their parent classes in terms of implementations (have similar properties)
- aim is to create sub-classes that don't repeat what is already defined in the super class/parent
	- make use of the parent's constructor for certain properties that are inherited

- If `Circle` is a super class of `FilledCircle` and `Circle` implements the `getArea()` method as specified by the interface `Shape` that it implements, then `FilledCircle` need not re-define a `getArea()` method (unless there is a need to do so).

- we can only do `super.smthg()`, not just invoke `super`.

- a class **can** *implement multiple interfaces*, but a **child class CANNOT extend** from *multiple parents*
	- `class A extends B, C {...}` ❌
	- cannot extend from multiple parents especially when their methods are the same (cannot disambiguate which to use)

#### Conventions
- `S` $\implies$ subclass (type S)
- `T` $\implies$ superclass (type T)

- **we can say `S` is-a `T`**
	- $\therefore$ methods of `S` can be invoked by `T` (i.e. `FilledCircle` can call the `getArea()` method as defined by `Circle`, which is the parent of `FilledCircle`)


### `protected` access modifier
- provides access to properties / methods to all other classes within the same package (not only for the child classes' access itself)

- if we have to use `private final` for the super classes' property that the child class accesses
	- idea: let the parent class take charge of  constructing the `radius`

### Method Overloading
- methods of the same name, but using different argument lists.
- number, type and order of args matter! $\implies$ focus on that is inside the brackets.
	- not the return type or accessibility
	-  i.e. `toString()` versus `toString(String prompt)`

#### Constructor Overloading
- constructor is just a special variant of method to instantiate object
- ensure that there are clear differences in method signature (***number of args, input type, order of arguments*** etc.) to ensure that there is no ambiguity btwn constructors so that code can compile.
- can have one constructor make use of another overloaded one by using `this(...)`