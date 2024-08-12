# Lecture 1 - Course Synopsis

## Misc Notes
1. `jshell` is used for people who are new to Java.
    - use `Ctrl-L` to for unit testing, prototyping etc.

2. Java is a statically-typed language with struct type checking
    - need to develop sense of type awareness when coding
    - everything revolves around typing -> return type, input param types

3. Function definitions
    - notion of types must be present when defining functions
    - usually use type `double` and not single-precision `float`

```bash
jshell> double myFunc(double inputParam){
...>     return d;
...> }
```

## Primitive VS Reference Types
- primitive types -> predefined by Java language specification (i.e. `int`, `boolean`)
- reference types -> non-primitives
    - class types, intreface types, type vars
    - predefined in the JDK (i.e. `String`) or user-defined
    - Wrapper Types: primitive values can "wrapped ard"/ boxed up on Wrapper Types (i.e. `Integer`, `Double`, `Boolean`)

## Abstraction and OOP Concepts
- *meaning*: reduce complexity by filtering out details
- is the overarching theme to CS2030 (want to deal with issues pertaining to abstraction)

- devising a composite type to represent things in the real world

1. Constructor(s)
```java
record Location(String desc, double x, double y){

}

record Location(String desc, double lng, double lat, int postal){

}
```

2. Instantiating Object
```java
Location home = new Location("home", 1.0, 2.0);
```

3. Java to-String Method
```java
home.toString();
home.x();
home.y();
home.desc();
```

4. `.equals()` method
- written in the "proper" way
```java
home.equals(new Location("home", 1.0, 2.0));
```

## JShell
1. `/save <filename>` - save jshell history as specified filename
2. `/list`
3. `/var` - see the type of the list

## Java Generics
- initialise list using the `List.of()` method
- need the wrapper type for inidvidual elements (i.e. can specify `List<Integer>` but **NOT** `List<int>`)

## Functional Programming Principle
- when creating a new `List`, we don't modify the elements but rather, we create a new `List`

## Loops
#### 1. Type 1
```java
for (Location loc: locations){
    // .. code
}
```
--> `for loc in locations:`

#### 2. Type 2
```java
for (int i = 0; i < locations.size(), i++){

}
```
--> `for i in range(start, stop, step)`


## Higher-Order (Declarative) Programming
- can receive a function as an argument
- can return a function
    - composition function that takes in two functions and produces some output

```java
Function<Integer, Integer> f = x -> x * x
f.apply(2)

f.compose(f) // call the other function i.e. f⋅g(x)
```

1. `forEach()`