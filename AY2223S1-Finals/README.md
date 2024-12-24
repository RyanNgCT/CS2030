Partially Completed.
### Question 1

```java
class User implements Notifiable {
    private final String userName;

    User(String userName) {
        this.userName = userName;
    }

    public Bot join(Bot b) {
        return b.resp(this);
    }

    public void printStuff(String toPrint) {
        System.out.println(this.userName + ": " + toPrint);
    }

    @Override
    public String toString() {
        return this.userName.toString();
    }
}
```

```java
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

class Bot {
    private final int botId;
    private final List<Notifiable> uList;

    Bot() {
        this.botId = new Random().nextInt(1000);
        this.uList = List.of();
    }

    Bot(List<Notifiable> uList) {
        this.botId = new Random().nextInt(1000);
        this.uList = uList;
    }

    private Bot(int botId, List<Notifiable> uList) {
        this.botId = botId;
        this.uList = uList;
    }

    public Bot resp(Notifiable usr) {
        String toAdd = this.toString() + " says hi to " + usr.toString();

        // update the Bot
        Bot updated = new Bot(
            this.botId,
            Stream.concat(this.uList.stream(), List.of(usr).stream()).toList());

        updated.uList.stream().forEach(u -> u.printStuff(toAdd));
        return updated;
    }

    @Override
    public String toString() {
        return "bot@" + this.botId;
    }
}
```

```java
interface Notifiable {
	public void printStuff(String s);
}
```

Eliminate Cyclic dependency by **not having bot** depend on the user.
### Question 2

Part A) Write a method `reverse()` that takes in a `List` of elements and returns the list with the element order reversed. Sample:

```java
jshell> reverse(List.<Integer>of(1, 2, 3));
$.. ==> [3, 2, 1]
```

Answer:
```java
import java.util.stream.Stream;
import java.util.List;

List<Integer> reverse(List<Integer> target) {
    return IntStream
        .range(1, target.size())
        .reduce((curr, next) -> new Pair<Integer, Integer>(next, curr))
        .toList();
}
```

Part B) Write a method pairing that takes in a `List` comprising an even number of elements and outputs a `List` of `Pairs` of elements by pairing adjacent elements (together).

```java
jshell> pairing(List.<Integer>of(1, 2, 3, 4));
$.. ==> [(1, 2), (3, 4)]
```


Part C) Write a method merging that takes in a `List` comprising of `Pair`s of
elements of the **same type** and outputs a `List` of elements by merging the pairs together.

```java
jshell> merging(pairing(List.<Integer>of(1, 2, 3, 4))) $.. ==> [1, 2, 3, 4]
```


### Question 3
*Part A)* Write the `getMarks()` method in the `PA` class to return the marks of all the levels of the assessment individually as a `Stream`.

```java
public Stream<Double> getMarks() {
	return this.levels.stream().map(x -> x.getMark());
}
```

Is the correct answer.


*Part B)* Write the `getMarks()` method within the `Student` class to return the marks of all practical assessments (by adding up the level marks of each assessment) individually as a Stream.
```java
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class Student {
    private final List<Optional<PA>> listPA;

    Student(List<Optional<PA>> listPA) {
        this.listPA = listPA;
    }

    public Stream<Optional<Integer>> getMarks() {
        return this.listPA.stream().map(
            x -> x.map(y -> y.getMarks().reduce(0, (a, b) -> a + b)));
    }
}
```

A student may be absent for a particular practical assessment. In this case, leave the marks as `Optional.empty()`, so as to differentiate between a student missing an assessment, and getting the assessment entirely wrong.

```java
class Main {
    public static int getTotalMarks(List<Student> stuList) {
        return stuList.stream()
            .flatMap(stu -> stu.getMarks().map(y -> y.orElse(0)))
            .reduce(0, (x, y) -> x + y);
    }
}
```



---
**Misc classes**

Level
```java
class Level {
    private final int marks;

    Level(int marks) {
        this.marks = marks;
    }

    public int getMark() {
        return this.marks;
    }

    @Override
    public String toString() {
        return "score: " + this.marks;
    }
}
```

PA
```java
import java.util.List;
import java.util.stream.Stream;

class PA {
    private final List<Level> levels;

    PA(List<Level> levels) {
        this.levels = levels;
    }

    public Stream<Integer> getMarks() {
        return this.levels.stream().map(x -> x.getMark());
    }

    @Override
    public String toString() {
        return this.levels.stream()
            .map(x -> x.toString())
            .reduce((a, b) -> a + ", " + b)
            .orElse("");
    }
}
```

Main
```java
import java.util.List;

class Main {
    public static int getTotalMarks(List<Student> stuList) {
        return stuList.stream()
            .flatMap(stu -> stu.getMarks().map(y -> y.orElse(0)))
            .reduce(0, (x, y) -> x + y);
    }
}
```

---
### Question 5
*Part A)* Using JShell, write a program fragment to total up the number of words of all pages pointed to by the urls. Demonstrate how the fetching of pages can be done lazily, i.e. only on demand. Note that web pages are not static, i.e. the same url could result in different content to be fetched at different times. You may use assume that the following list of urls is given.

Provided:
```java
int processUrl(String url) {
	try {
		Thread.sleep(1000);
	}
	catch (InterruptedException ex) {}
}

List<String> urls = List.of("abc.xyz", "cde.qpr", "xyz.abc")
```

The general idea:
```java
Supplier<List<Integer>> sup = () -> urls.stream().map(x -> processUrl(x)).toList();
```


*Part B)* Fetching webpages takes up a lot of time. Moreover, webpages are unrelated and thus can be fetched at the same time. Write a `JShell` program fragment to total up the number of words of all pages pointed to by the urls in a given list within a minimum amount of time.
```java
CompletableFuture<Integer> cf =
    urls.stream()
        .map(x -> CompletableFuture.supplyAsync(() -> processUrl(x)))
        .reduce(CompletableFuture.completedFuture(0),
                (x, y) -> x.thenCombine(y, (a, b) -> a + b)).join();
```