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

```java
jshell> double myFunc(double inputParam){
...>     return d;
...> }
```

## Primitive VS Reference Types
- primitive types (or literals) -> predefined by Java language specification (i.e. `int`, `boolean`)
	- are statically typed (variable types must be declared before use)
	- default values for primitives are **never null** -> Java language will assign them a given value

- reference types -> non-primitives
    - class types, interface types, type vars
    - predefined in the JDK (i.e. `String`) or user-defined
    - **Wrapper Types / Wrapper Objects**: primitive values can "wrapped ard"/ boxed up their Wrapper Types (i.e. `Integer`, `Double`, `Boolean`)
	    - default values for these are null
	    - are immutable (need to create a new instance when performing operations in memory) and are final (can't inherit from them)
### Autoboxing and Unboxing
- *def* Autoboxing: the auto conversion of primitive types to corresponding object wrapper classes
```java
// autoboxing
Integer j = 1;
Double dbl = 3.94;
```

- *def*: Unboxing: auto conversion of object wrapper types back into their corresponding primitive types
```java
// unboxing
int newInt = new Integer(1);

double primitiveDbl = dbl; // refer to above
```

**Consequences**
- drop in performance through creation of intermediary objects -> create more work for the Java garbage collector

**When to use Wrapper classes**
- when dealing with collections or generics $\implies$ required.
- when we want to calculate `MIN_SIZE` or `MAX_SIZE` of a type.
- when we want a null-able variable and we want the default value to be null
## Abstraction and OOP Concepts
- *meaning*: reduce complexity by filtering out the unnecessary (technical) details
- is the **overarching theme** to CS2030 (want to deal with issues pertaining to abstraction)

- devising a composite / aggregated type to represent things in the real world
	- i.e. Location may consist of the following building blocks:
		```
		descrip - String
		lng value - double
		lat value - double
		```
	- i.e. Person which has a lot of data points that we can quantify

1. Constructor(s)
```java
record Location(String desc, double x, double y){

}

record Location(String desc, double lng, double lat){}
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

5. Object's own functionalities
	- i.e. for a Location Object to be able to compute it's own distance away from another point.

*Traditional Approach*
- The Location Object has properties (x and y values or longitude and latitudes)
```java
jshell> record Location(String desc, double lng, double lat){}
|  replaced record Location

jshell> Location myHome = new Location("Home base", 1.0, 1.0)
myHome ==> Location[desc=Home base, lng=1.0, lat=1.0]

jshell> double distanceBtwn(Location loc_1, Location loc_2){
   ...>     double dY = loc_2.lat() - loc_1.lat();
   ...>     double dX = loc_2.lng() - loc_1.lng();
   ...>     return (dX * dX + dY * dY);
   ...> }
|  created method distanceBtwn(Location,Location)
jshell> double newdbt = distanceBtwn(myHome, new Location("The Park", 10.5, 30.345));
newdbt ==> 951.379025
```

*Using self-referencing*
- have functional abstraction within this method itself (need to already have an instance of this location).
```java
record Location(String desc, double lat, double lng){
	double distanceTo(Location other)
	{
		double dY = this.lat() - other.lat();
		double dX = this.lng() - other.lng();
		return (dX * dX + dY * dY);
	}
}
```


## JShell
1. `/save <filename>` - save jshell history as specified filename
2. `/list`
3. `/var` - see the type of the list

## Java Generics
- Python, we use the built-in `list()` so in Java, there is also an equivalent `List`.
- initialise list using the `List.of()` method, passing in one or more arguments.
	- type contained within the `List` wrapper class does not seem to matter -> infer type of list.
- need the wrapper type for individual elements (i.e. can specify `List<Integer>` but **NOT** `List<int>`)

## Functional Programming Principles
- Fundamental principle: when creating a new `List`, we don't modify the elements but rather, we create a new `List` 
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

- `forEach()`, `reduce()` etc.


### Streams
- functionality of Java, somewhat like loops
```Java
List<Location> locs = List.of(home, new Location("Shop", 2.5, 3.5));
locs.stream(); // turning the list of locations into a stream
```
