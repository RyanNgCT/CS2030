# Lecture 3 - Abstraction and Encapsulation

- write methods only when they are needed (take a more conservative approach).

## Class conventions
- constructor of the class is the same as the class name itself
- `--enable-preview` option when compiling is only needed for a nameless class


## ToString method
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


### Record
- can print values (purpose is to store values)


### Notes
- Don't use `null`, use optionals instead
```java
import java.util.Optional;

class Circle {
    private final Optional<Point> centre;
    ...
}
```

---

Try to implement isOverlap -> of a circle class that takes in another circle.