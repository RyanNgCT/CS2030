# Lecture 3 - Abstraction and Encapsulation

## Data Process Model vs OOP
- i.e. `distanceBtwn(Point a, Point b)` method
    - Points -> are the data
    - seperate from the process (which is the distanceBtwn function itself)

- i.e. `distanceTo(Point othr)` method
    - requires just one other point to find the difference between itself and the other point

## Big ideas from this lecture
1. Abstraction: data & functional abstraction
    - data abstraction: point comprises of two floating-point values (for the implementation details, client doesn't need to know or care about).
    - functional abstraction: e.g. a point can determine the diatnce from itself to another point on the same plane.

2. Encapsulation: packaging and hiding (of info that client doesn't need to know)
    - packaging: use of classes to package lower level functionality and data

3. Object
    - abstraction of closely-related data (properties) and behaviour or functionality (methods)
    - a blueprint of the object is called a **class**.


## Tips
⚠️ write methods **only when they are needed** (take a more conservative approach)!
⚠️ don't use `null`!
⚠️ classes written should be immutable!
⚠️ try to stay away from using the `record`, except when creating meaningful composite data  types like `Pair<T, U>`
    -> try to avoid behaviour that violates "Tell-Don't-Ask" principle.

✅ use assignment operators (i.e. `=`) to make the code look nicer and more readable, but use sparingly.

## Class conventions
- constructor of the class is the same as the class name itself
- `--enable-preview` option when compiling is only needed for a nameless class

### ToString method
```java
public String toString(String name){
    return "Hello, " + name;
}
```

### Class Examples
- Point class is standalone, does not depend on other classes
- Circle class depends on Point class


*Cyclic dependencies*
- cannot have two class files that depend on each other 


### Access modifiers
- making the properties `private` -> so that other classes cannot access (don't provide direct access)
    - MUST make properties private in CS2030 (restrict client access)

Guiding principle: Tell-Don't-Ask
- `private final`: can only be accessed at the constructor level
- don't use getters or accessors!

### Record
- can print values (purpose is to store values)

### Examples
- Don't use `null`, use optionals instead
```java
import java.util.Optional;

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
- no longer client's concern to know about formula for calculate distanceTo (i.e. $\sqrt{(x_1 - x_2)^2 + (y_1 - y_2)^2}$ )

---

Try to implement `isOverlap` -> of a circle class that takes in another circle.
- solved already, refer to recitation 2 question 3.