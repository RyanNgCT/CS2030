# Lecture 3 - Abstraction and Encapsulation

## Data Process Model vs OOP
- i.e. `distanceBtwn(Point a, Point b)` method
    - Points -> are the data (method takes in two params)
    - seperate from the process (which is the `distanceBtwn` function itself)

- i.e. `distanceTo(Point othr)` method
    - requires just one other point to find the difference between itself and the other point

## Big ideas from this lecture
1. Abstraction: data & functional abstraction
    - **data abstraction**: point comprises of two floating-point values (for the implementation details, client doesn't need to know or care about the internal representation or functionality).
    - **functional abstraction**: e.g. a point can determine the distance from itself to another point on the same plane.

2. Encapsulation: packaging and hiding (of info that client doesn't need to know)
    - *packaging*: use of classes to package lower level **functionality and data**
        - packaging of lower level data (i.e. coord points)
        - packaging of lower level functionality (i.e. calculating distance btwn points in the `Point` class)
        - allows one to "make a client" (a.k.a. `JShell`)

	- allows one to hide information from the client / client classes
		- can't get the internal data/methods without first utilizing the external `public` method/service provided by the class

3. Object
    - abstraction of closely-related data (properties / instance fields) and behaviour or functionality (methods).
    - a blueprint of the object is called a **class**.


## Tips
⚠️ write methods **only when they are needed** by the client (take a conservative approach)!
    $\implies$ no marks for standard "getter, setter, constructor, toString etc."

⚠️ don't use `null`!

⚠️ classes written should be *immutable*! -> to follow sound principles

⚠️ try to stay away from using the `record`, **except** when creating meaningful composite data types like `Pair<T, U>`
    -> try to avoid behaviour (through using `record`s) that violates "Tell-Don't-Ask" principle.

⚠️ don't replace a variable with another value -> hence use `private final ...`

✅ use assignment operators (i.e. ` ... = ...`) to make the code look nicer and more readable, but use them sparingly.
    -> D.R.Y. principle / copy pasting similar codes

## Class conventions
- constructor of the class is the *same as the class name* itself
- `--enable-preview` option when compiling is only needed for a nameless class

### Constructor
- should take in all the `private` properties defined in the class
- anytime we are referring to certain properties / methods within the class -> use the `this` keyword.
- we invoke the constructor through the `new` keyword.

### ToString method
- allows the output to be "customized" if someone wants to print the point that is meaningful to the client/user
- usually at the end because it is the most unimportant
```java
// signature is always like that
public String toString(String name){
    return "Hello, " + name;
}
```
## Dependency
- establish a `has-a` relationship btwn Circle and Point

### Class Examples
- Point class is standalone, does not depend on other classes
- Circle class depends on Point class
    - if Point provides the service of calculating distance, we should just use it in the `Circle` class instead of reinventing the wheel (providing the services of the class).

*Cyclic dependencies*
- cannot have two class files that depend on each other 
- best design is to have a "base" class and build on top of that
    -> a class should only have a *single responsibility* (containment is a circle thing, not a point thing)


### Access modifiers
- the most common access modifiers are `public`, `private` and `protected` (if no access modifier is specified then the thing defined thereafter is of `package` access)

- making the properties `private` $\implies$ so that other classes cannot access (don't provide direct access)
    - MUST make properties **private** in CS2030 (restrict client access)

	- `public` modifier allows unrestricted access to properties and methods for modification $\implies$ not a good practice!

- child classes can only use properties or methods scoped as `public` or `protected`

**Guiding principle: Tell-Don't-Ask**
- `private final`: can only be assigned a value at the constructor level.
- don't use getters or accessors!
- accessors can be used as a helper (i.e. `private double getX()`)

Objects should be immutable!
- methods should not be able to change properties of objects.
- methods should return us something -> a new object (not `void`)!
- don't want objects to suddenly change to something else (or have a side effect)

### Record
- can print values (purpose is to store values)

## Optionals
- Don't use `null`, use optionals instead (sometimes have something, sometimes don't have it)
    - wrap optional around the type that we might not have

- Optional.empty() is a value in the domain of a function
    - have a correct type to include the missing type (or in this case `Optional<Point>`)

- use `.map()` (typically) or `filter()` to do something to the value contained inside the optional (some operation)
- use `.orElse` to return the value inside the optional, otherwise return something else specified (provided as part of `orElse`inside the brackets)

```java
import java.util.Optional; // needed
class Circle {
    private final Optional<Point> centre;
    ...
}
```

### Testing `distanceTo()` within `Point.java`
```java
jshell> List<Point> points = List.of(new Point(0.0, 0.0), new Point(2.0, 2.0))
points ==> [(0.0, 0.0), (2.0, 2.0)]

jshell> points.stream().map(x -> x.distanceTo(new Point(1.0, 1.0))).toList()
$17 ==> [1.4142135623730951, 1.4142135623730951]
```

### Abstraction Barrier
- no longer client's concern to know about formula for calculate distanceTo (i.e. $\sqrt{(x_1 - x_2)^2 + (y_1 - y_2)^2}$ )
- avoid providing backdoor access to properties

---

Try to implement `isOverlap` -> of a circle class that takes in another circle.
- solved already, refer to recitation 2 question 3.