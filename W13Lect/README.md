# Lecture 12 -- Recapitulation & Exam Preparations
- The is the OOP part and the Functional Programming part
- OOP and Functional Programming are not mutually exclusive, supposed to complement each other
	- OOP makes code understandable by encapsulating moving parts (in the correct entities) $\implies$ makes software easier to reason (i.e. by encapsulating / hiding state changes)
	- FP makes code understandable by minimizing moving parts (i.e. to minimize state changes)

- motivation: to come up with a medium scale software which is easy to comprehend

---
## Testability / Maintainability of Software
### OOP
- to encapsulate moving parts
- is the high-level design $\implies$ modelling things with objects
- very important to consider the design of the product

#### How do we model a solution using OOP?
We model things with objects (i.e. using / applying the SOLID principles)
- are not given depth in this course

- **S**ingle Responsibility Principle
	- Utilizing the **Principle of Abstraction**
		1. **Data** Abstraction (using the `Point` class)
		2. **Functional** Abstraction (want to make every task a method, each method should only do one thing, very well)

	- the interactions between objects make OOP interesting and important
		- `has-a` relationship, which is a composition (a `Circle` has a `Point`)

	- properties are **private**

	- **Principle of Encapsulation** (as a complement to abstraction)
		- packaging
		- information hiding $\implies$ the **tell-don't-ask** principle
			- we should try to minimize external details to clients


- **O**pen-Closed & **L**iskov Substitution Principles
	- enables us to build tighter relationships between objects (includes parent-child classes)
	- super class and child classes
	- design **should support** further extensions to the system (i.e. scalability)
	- describes the `is-a` parent-child relationship (made possible by LSP)
	- OCP enables extension of more subclasses, but should **not break** when adding more functionalities

	- **Principle of Polymorphism**
		- allows for child classes 
			- inherit parent's functionalities
			- override parent's functionalities if needed

	- Compile-Time Type versus Run-Time Type
		- Compile-Time: static binding (determine which methods can be called)
			- overloaded method resolutions
	
		- Run-Time: dynamic binding
			- is only necessary when at compile time we choose the method and when we run, the method has different variants
			- more for overriding methods (for resolving overriding methods)

	- **Principle of Inheritance**


- **I**nterface Segregation Principle and **D**ependency Inversion Principle
	- interface are important because java makes use of interface to realize the Functional (Programming) aspect of java
	- If client $C_1$ and another client $C_2$ want to access an object
	- To keep things simple $C_1$ and $C_2$ will rely on $I_1$ and $I_2$ (which are the contracts) to ensure that the implementor $M$ provides services outlines in $I_i$

	- Dependency Inversion
		- the client should not directly depend on the implementer


### FP
- supposed to minimize moving parts (or side effects)
- we are talking about the individual low level implementations / functionalities (loops, conditionals, processing etc.)

- revolves around **Pure Functions**
	- aspects of pure functions include:
		1. Objects should be `private` and `final` (the concept of **immutability** $\implies$ effect free programming)

- Immutability + Pure Functions
	1. **Referential transparency** (when invoking a function with input(s) of immutable object(s), the outcome is always pre-determined)
	2. **Lazy Evaluation** (rather than having a pipeline of states, we have a pipeline of *functions in states)
		1. the states don't change eagerly, but it is through the functions that the state changes
		2. function composition if used for lazy evaluation (compose functions, before applying them)

	3. Function Currying
		1. can take multiple arguments (pushing in the arguments later on)


- also involves **Contexts**
	- state changes is part of the game
	- contexts allow us to encapsulate and hide these state changes

	1. `Stream` -- (infinite looping, parallelism)
	2. `Optional / Maybe` -- (support for missing values)
	3. `Try` -- (exception handling)
	4. `Lazy` -- (lazy evaluation with **caching**)
	5. `CompletableFuture` -- (Asynchronous Programming)


	- we can put the value in a box (i.e. context) and then transform it (i.e. apply a function $f$ on the object $c$)

```java
// map
c --- f:x -> f(x) ---> f(c)


// flatMap (takes in f(x) in a "box")
c --- f:x -> |f(x)| ---> f(c)
```


- Laws of Functor and Monad (helps to ensure that `map()` and `flatMap()` are written correctly)
	- Identity (for monad, there are left and right identity laws)
	- Associative


### Generics / Functional Interfaces
- provide a service to come up with contexts that can hold anything
	- in particular for mapping and `flatmap`-ing when we take in Functions
- upper and lower bounded wildcards