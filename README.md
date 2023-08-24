# What Is a Functional Programming?

![1.png](Photos%2F1.png)

Functional programming is a declarative programming paradigm, meaning that the program logic is expressed without explicitly describing the control flow. It emphasizes the application of functions, in contrast to the imperative programming style, which emphasizes changes in state.
It's based on the concept of "pure functions," which produce output based solely on their inputs and have no side effects

## Why Functional Programming

### Predictable and Debuggable:
Pure functions with no side effects make code easier to debug and understand, as they don't rely on hidden external state.

### Concurrency and Parallelism: 
Immutability and lack of shared state simplify handling concurrent and parallel processes, reducing the likelihood of bugs.

### Modular and Reusable: 
Emphasis on smaller, composable functions promotes modularity and code reusability, leading to maintainable systems.

### Testing:
Pure functions facilitate unit testing since they produce consistent outputs for given inputs.

### Scalability: 
Functional programming's structure and immutability support managing complexity as systems grow.

### Mathematical Foundation: 
Functional programming's basis in mathematical concepts is advantageous for complex computations and transformations.

# Using Variables in Lambdas

The code snippet provided demonstrates the usage of lambda expressions in the context of a `GorillaFamily` class.
Let's analyze how effectively final variables, instance variables, and method parameters are used within this example:

```java
interface Gorilla {
    String move();
}

class GorillaFamily {
    String walk = "walk";

    void everyonePlay(boolean baby) {
        String approach = "amble";
        //approach = "run";  // Uncommenting this line would make the variable non-effectively final

        play(() -> walk);  // Uses an instance variable (walk)
        play(() -> baby ? "hitch a ride" : "run");  // Uses an effectively final method parameter (baby)
        play(() -> approach);  // Uses an effectively final local variable (approach)
    }

    void play(Gorilla g) {
        System.out.println(g.move());
    }
}
```

1. **Instance Variable**: The instance variable `walk` from line 3 is accessed in the lambda expression on line 8. This is allowed, as lambda expressions can access instance variables.

2. **Effectively Final Method Parameter**: The method parameter `baby` from line 4 is used in the lambda expression on line 9. It's considered effectively final because there are no reassignments to this variable within the method. Lambda expressions can capture effectively final method parameters.

3. **Effectively Final Local Variable**: The local variable `approach` from line 5 is used in the lambda expression on line 10. This variable is effectively final because it's not reassigned after its initialization. Lambda expressions can capture effectively final local variables.

If you uncomment the line `approach = "run";` (line 6), the `approach` variable would no longer be effectively final, and this would result in a compiler error on line 10 when trying to access a non-effectively final variable.

It's important to note that the access rules for lambda expressions are consistent with those for inner classes. Lambda expressions can access instance variables, effectively final method parameters, and effectively final local variables from the enclosing scope. Private variables in another class remain inaccessible, just like for inner classes. This behavior helps maintain encapsulation and avoid unintended access to variables.

# Working with Built-In Functional Interfaces

As you remember, a functional interface has exactly one abstract method. Java 8 introduced a number of built-in functional interfaces to represent common functions used in Java code. These interfaces are located in the `java.util.function` package.

Let's take a look at some of the most commonly used built-in functional interfaces:

1. **`Supplier<T>`**: Represents a supplier of results. It has a single method `get()` that takes no arguments and returns a result of type `T`.

    ```java
    Supplier<Integer> randomNumberSupplier = () -> (int) (Math.random() * 100);
    int randomValue = randomNumberSupplier.get();
    System.out.println(randomValue);
    ```

2. **`Consumer<T>`**: Represents an operation that accepts a single input argument and returns no result. It has a single method `accept(T t)`.

    ```java
    Consumer<String> printUpperCase = s -> System.out.println(s.toUpperCase());
    printUpperCase.accept("hello");
    ```

3. **`BiConsumer<T, U>`**: Represents an operation that accepts two input arguments and returns no result. It has a single method `accept(T t, U u)`.

    ```java
    BiConsumer<String, Integer> printPair = (s, i) -> System.out.println(s + ": " + i);
    printPair.accept("Value", 42);
    ```

4. **`Predicate<T>`**: Represents a predicate (boolean-valued function) of one argument. It has a single method `test(T t)`.

    ```java
    Predicate<Integer> isEven = num -> num % 2 == 0;
    System.out.println(isEven.test(4));  // true
    ```

5. **`BiPredicate<T, U>`**: Represents a predicate of two arguments. It has a single method `test(T t, U u)`.

    ```java
    BiPredicate<String, Integer> isLengthEqualTo = (s, length) -> s.length() == length;
    System.out.println(isLengthEqualTo.test("hello", 5));  // true
    ```

6. **`Function<T, R>`**: Represents a function that accepts one argument and produces a result. It has a single method `apply(T t)`.

    ```java
    Function<Integer, String> intToString = i -> "Number: " + i;
    String result = intToString.apply(42);
    System.out.println(result);
    ```

7. **`BiFunction<T, U, R>`**: Represents a function that accepts two arguments and produces a result. It has a single method `apply(T t, U u)`.

    ```java
    BiFunction<Integer, Integer, Double> calculateHypotenuse = (a, b) -> Math.sqrt(a * a + b * b);
    double hypotenuse = calculateHypotenuse.apply(3, 4);
    System.out.println(hypotenuse);
    ```

8. **`UnaryOperator<T>`**: Represents an operation on a single operand that produces a result of the same type. It extends `Function<T, T>`.

    ```java
    UnaryOperator<Integer> square = n -> n * n;
    int squaredValue = square.apply(5);
    System.out.println(squaredValue);
    ```

9. **`BinaryOperator<T>`**: Represents an operation upon two operands of the same type, producing a result of the same type. It extends `BiFunction<T, T, T>`.

    ```java
    BinaryOperator<Integer> add = (a, b) -> a + b;
    int sum = add.apply(3, 4);
    System.out.println(sum);
    ```
   
## Understanding Functional Interfaces

It's crucial to have a clear understanding of the characteristics of various functional interfaces to effectively work with them. Let's practice by considering three scenarios and identifying the appropriate functional interface for each:

1. **Returns a String without taking any parameters**:
   - The `Supplier` functional interface fits this scenario perfectly. It has no parameters and returns a value.

Example:
```java
Supplier<String> messageSupplier = () -> "Hello, world!";
String message = messageSupplier.get();
```

2. **Returns a Boolean and takes a String**:
   - In this case, the appropriate functional interface is `Function`. Although it's a bit counterintuitive, the `Function` interface is used because it takes one parameter and returns a different type. A `Predicate` wouldn't work since it returns a boolean primitive, not a `Boolean` object.

Example:
```java
Function<String, Boolean> stringToBoolean = str -> str.equals("valid");
Boolean isValid = stringToBoolean.apply("valid");
```

3. **Returns an Integer and takes two Integers**:
   - For this scenario, you have two valid options: `BinaryOperator` and `BiFunction`. However, the more specific and appropriate choice is `BinaryOperator`, as it is tailored for cases where the input and output types are the same.

Example:
```java
BinaryOperator<Integer> addIntegers = (a, b) -> a + b;
int sum = addIntegers.apply(5, 7);
```

![2.png](Photos%2F2.png)
![3.png](Photos%2F3.png)

# Returning an Optional

Suppose that you are taking an introductory Java class and receive scores of 90 and 100
on the first two exams. Now, we ask you what your average is. An average is calculated by
adding the scores and dividing by the number of scores, so you have (90+100)/2. This gives
190/2, so you answer with 95. Great!
Now suppose that you are taking your second class on Java, and it is the first day of
class. We ask you what your average is in this class that just started. You haven’t taken any
exams yet, so you don’t have anything to average. It wouldn’t be accurate to say that your
average is zero. That sounds bad, and it isn’t true. There simply isn’t any data, so you don’t
have an average yet.
How do we express this “we don’t know” or “not applicable” answer in Java? Starting
with Java 8, we use the Optional type. An Optional is created using a factory. You can
either request an empty Optional or pass a value for the Optional to wrap. Think of an
Optional as a box that might have something in it or might instead be empty. Figure 4.1
shows both options.

![4.png](Photos%2F4.png)

### Here’s how to code our average method:

```java
public Optional<Double> average(int... scores) {
    if (scores.length == 0) return Optional.empty();
    int sum = 0;
    for (int score: scores) sum += score;
    return Optional.of((double) sum / scores.length);
}
        System.out.println(average(90, 100)); // Optional[95.0]
        System.out.println(average()); // Optional.empty
```

### want to check if a value is there and/or get it out of the box. Here’s one way to do that:

```java
Optional<Double> opt = average(90, 100);
if (opt.isPresent())
    System.out.println(opt.get()); // 95.0
```

The `Optional` class was introduced in Java 8 to represent an optional value. It's a container object that may or may not contain a non-null value. It's used to avoid `NullPointerException` in situations where a value may or may not be present.

## Optional instance methods

![5.png](Photos%2F5.png)

```java
Optional<Double> opt = average(90, 100);
opt.ifPresent(System.out::println); // 95.0
```

```java
Optional<Double> opt = average();
System.out.println(opt.orElse(Double.NaN)); // NaN
System.out.println(opt.orElseGet(() -> Math.random())); // 0.6163096402758491
System.out.println(opt.orElseThrow(() -> new IllegalStateException())); // Exception in thread "main" java.lang.IllegalStateException
```

## Conclusion

Using the `Optional` type introduced in Java 8 allows you to better handle cases where data might be missing, ensuring more accurate and readable code. It's a way to explicitly indicate "we don't know" or "not applicable" scenarios in your Java programs.

# Using Streams

## What Is a Stream?

A stream is a sequence of elements supporting sequential and parallel aggregate operations.

## Creating a Stream

There are several ways to create a stream. Let's look at some of the most common ways.

### Creating a Stream from a Collection

You can create a stream from a collection using the `stream()` method:

```java
List<String> names = Arrays.asList("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> namesStream = names.stream();
```

### Creating a Stream from an Array

You can create a stream from an array using the `Arrays.stream()` method:

```java
String[] names = {"John", "Jane", "Mary", "Harry", "Joe"};
Stream<String> namesStream = Arrays.stream(names);
```

### Creating a Stream from a Static Factory Method

You can create a stream from a static factory method using the `Stream.of()` method:

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
```

### Creating a Stream from a File

You can create a stream from a file using the `Files.lines()` method:

```java
Stream<String> fileLinesStream = Files.lines(Paths.get("file.txt"));
```

### Creating a Stream from a String

You can create a stream from a string using the `chars()` method:

```java
IntStream charsStream = "Stream API".chars();
```

### Creating a Stream from a Builder

You can create a stream from a builder using the `Stream.builder()` method:

```java
Stream<String> namesStream = Stream.<String>builder()
    .add("John")
    .add("Jane")
    .add("Mary")
    .add("Harry")
    .add("Joe")
    .build();
```

### Creating a Stream from an Iterator

You can create a stream from an iterator using the `StreamSupport.stream()` method:

```java
Iterator<String> namesIterator = Arrays.asList("John", "Jane", "Mary", "Harry", "Joe").iterator();
Stream<String> namesStream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(namesIterator, 0), false);
```

## Using Stream Operations

![6.png](Photos%2F6.png)

Once you have a stream, you can perform operations on it. There are two types of operations:

* **Intermediate operations** - Intermediate operations return a new stream. They are always lazy; executing an intermediate operation such as `filter()` does not actually perform any filtering, but instead creates a new stream that, when traversed, contains the elements of the initial stream that match the given predicate. Intermediate operations are never the final result of a stream pipeline.
* **Terminal operations** - Terminal operations return a result of a certain type instead of again a Stream. Terminal operations are eager and execute the stream pipeline to produce a result. A terminal operation is the last operation in a stream pipeline.

![7.png](Photos%2F7.png)

### Intermediate Operations

Intermediate operations return a new stream. They are always lazy; executing an intermediate operation such as `filter()` does not actually perform any filtering, but instead creates a new stream that, when traversed, contains the elements of the initial stream that match the given predicate. Intermediate operations are never the final result of a stream pipeline.

#### filter()

The `filter()` method returns a stream consisting of the elements of the current stream that match the given predicate.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> namesStartingWithJStream = namesStream.filter(name -> name.startsWith("J"));
```

#### distinct()

The `distinct()` method returns a stream consisting of the distinct elements of the current stream.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe", "Jane");
Stream<String> distinctNamesStream = namesStream.distinct();
```

#### limit()

The `limit()` method returns a stream consisting of the elements of the current stream, truncated to be no longer than the specified maximum size.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> limitedNamesStream = namesStream.limit(3);
```

#### skip()

The `skip()` method returns a stream consisting of the remaining elements of the current stream after discarding the first specified number of elements.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> skippedNamesStream = namesStream.skip(2);
```

#### map()

The `map()` method returns a stream consisting of the results of applying the given function to the elements of the current stream.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> upperCaseNamesStream = namesStream.map(String::toUpperCase);
```

#### flatMap()

The `flatMap()` method returns a stream consisting of the results of replacing each element of the current stream with the contents of a mapped stream produced by applying the provided mapping function to each element.

```java
Stream<List<String>> namesStream = Stream.of(
    Arrays.asList("John", "Jane"),
    Arrays.asList("Mary", "Harry", "Joe")
);
Stream<String> flatNamesStream = namesStream.flatMap(Collection::stream);
```

#### sorted()

The `sorted()` method returns a stream consisting of the elements of the current stream, sorted according to natural order.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> sortedNamesStream = namesStream.sorted();
```

#### peek()

The `peek()` method returns a stream consisting of the elements of the current stream, additionally performing the provided action on each element as elements are consumed from the resulting stream.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Stream<String> peekedNamesStream = namesStream.peek(System.out::println);
```


### Terminal Operations

Terminal operations return a result of a certain type instead of again a Stream. Terminal operations are eager and execute the stream pipeline to produce a result. A terminal operation is the last operation in a stream pipeline.

![8.png](Photos%2F8.png)

#### forEach()

The `forEach()` method performs an action for each element of the current stream.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
namesStream.forEach(System.out::println);
```

#### allMatch()

The `allMatch()` method returns whether all elements of the current stream match the provided predicate.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
boolean allMatch = namesStream.allMatch(name -> name.length() > 3);
```

#### anyMatch()

The `anyMatch()` method returns whether any elements of the current stream match the provided predicate.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
boolean anyMatch = namesStream.anyMatch(name -> name.length() > 3);
```

#### noneMatch()

The `noneMatch()` method returns whether no elements of the current stream match the provided predicate.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
boolean noneMatch = namesStream.noneMatch(name -> name.length() > 3);
```

#### findFirst()

The `findFirst()` method returns an `Optional` describing the first element of the current stream, or an empty `Optional` if the stream is empty.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Optional<String> firstName = namesStream.findFirst();
```

#### findAny()

The `findAny()` method returns an `Optional` describing some element of the current stream, or an empty `Optional` if the stream is empty.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Optional<String> anyName = namesStream.findAny();
```

#### count()

The `count()` method returns the count of elements in the current stream.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
long count = namesStream.count();
```

#### Collect()

The `collect()` method performs a mutable reduction operation on the elements of the current stream using a Collector.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
List<String> names = namesStream.collect(Collectors.toList());
```

#### reduce()

The `reduce()` method performs a reduction on the elements of the current stream, using the provided identity value and an associative accumulation function, and returns the reduced value.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Optional<String> reducedNames = namesStream.reduce((name1, name2) -> name1 + " | " + name2);
```

#### min()

The `min()` method returns the minimum element of the current stream according to the provided `Comparator`.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Optional<String> minName = namesStream.min(Comparator.naturalOrder());
```

#### max()

The `max()` method returns the maximum element of the current stream according to the provided `Comparator`.

```java
Stream<String> namesStream = Stream.of("John", "Jane", "Mary", "Harry", "Joe");
Optional<String> maxName = namesStream.max(Comparator.naturalOrder());
```






## Putting Together the Pipeline of Stream Operations

Streams allow you to use chaining and express what you want to accomplish rather than
how to do so. Let’s say that we wanted to get the first two names alphabetically that are
four characters long. In Java 7, we’d have to write something like the following:

```java
List<String> names = Arrays.asList("John", "Jane", "Mary", "Harry", "Joe");
List<String> filteredNames = new ArrayList<>();
for (String name: names) {
    if (name.length() == 4) filteredNames.add(name);
}
Collections.sort(filteredNames);
List<String> firstTwoNames = new ArrayList<>();
int count = 0;
for (String name: filteredNames) {
    firstTwoNames.add(name);
    count++;
    if (count == 2) break;
}
System.out.println(firstTwoNames); // [Jane, John]
```

In Java 8, we can use streams to express what we want to accomplish rather than how to

```java
List<String> names = Arrays.asList("John", "Jane", "Mary", "Harry", "Joe");
List<String> firstTwoNames = names.stream()
    .filter(name -> name.length() == 4)
    .sorted()
    .limit(2)
    .forEach(System.out::println);// [Jane, John]
```

The difference is that we express what is going on. We care about String objects of
length 4. Then we then want them sorted. Then we want to first two. Then we want to
print them out. It maps better to the problem that we are trying to solve, and it is simpler
because we don’t have to deal with counters and such.
Once you start using streams in your code, you may find yourself using them in many
places. Having shorter, briefer, and clearer code is definitely a good thing!
In this example, you see all three parts of the pipeline. Figure 4.5 shows how each intermediate operation in the pipeline feeds into the next.

![9.png](Photos%2F9.png)


# Working with Primitives


