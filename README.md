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

## What Is a Stream ?

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

- **Intermediate operations** - Intermediate operations return a new stream. They are always lazy; executing an intermediate operation such as `filter()` does not actually perform any filtering, but instead creates a new stream that, when traversed, contains the elements of the initial stream that match the given predicate. Intermediate operations are never the final result of a stream pipeline.
- **Terminal operations** - Terminal operations return a result of a certain type instead of again a Stream. Terminal operations are eager and execute the stream pipeline to produce a result. A terminal operation is the last operation in a stream pipeline.

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

With streams,
there are also equivalents that work with the int, double, and long primitives. Let’s take a
look at why this is needed. Suppose that we want to calculate the sum of numbers in a finite
stream:

```java
Stream<Integer> stream = Stream.of(1, 2, 3);
System.out.println(stream.reduce(0, (s, n) -> s + n)); // 6
```

Not bad. It wasn’t hard to write a reduction. We started the accumulator with zero. We
then added each number to that running total as it came up in the stream. There is another
way of doing that:

```java
Stream<Integer> stream = Stream.of(1, 2, 3);
System.out.println(stream.mapToInt(x -> x).sum());
```

This time, we converted our Stream<Integer> to an IntStream and asked the IntStream
to calculate the sum for us. The primitive streams know how to perform certain common
operations automatically.

```java
IntStream intStream = IntStream.of(1, 2, 3);
OptionalDouble avg = intStream.average();
System.out.println(avg.getAsDouble()); // 2.0
```

Clearly primitive streams are important. We will look at creating and using such streams, including
optionals and functional interfaces.

## Creating Primitive Streams

There are three primitive stream types: IntStream, DoubleStream, and LongStream.

Some of the methods for creating a primitive stream are equivalent to how we created
the source for a regular Stream . You can create an empty stream with this:

```java
IntStream empty = IntStream.empty();
```

You can create a stream with a single element with this:

```java
IntStream oneValue = IntStream.of(1);
```

You can create a stream from an array with this:

```java
IntStream fromArray = IntStream.of(1, 2, 3);
int[] intArray = {1, 2, 3};
IntStream fromArray2 = Arrays.stream(intArray);
```

You can create a stream from a range of numbers with this:

```java
IntStream fromRange = IntStream.range(1, 6); // 1, 2, 3, 4, 5
IntStream fromRangeClosed = IntStream.rangeClosed(1, 5); // 1, 2, 3, 4, 5
```

The final way to create a primitive stream is by mapping from another stream type.
Table 4.6 shows that there is a method for mapping between any stream types.;

![10.png](Photos%2F10.png)

## Using Optional with Primitive Streams

Now that you know about primitive streams, you can calculate the
average in one line:

```java
IntStream stream = IntStream.of(1, 2, 3);
OptionalDouble avg = stream.average();
System.out.println(avg.getAsDouble()); // 2.0
```

The OptionalDouble is a container that might or might not have a double in it. In this
case, it does. We can get it out with getAsDouble() . What happens if we try to get the
average of an empty stream?

```java
IntStream stream = IntStream.empty();
OptionalDouble avg = stream.average();
System.out.println(avg); // OptionalDouble.empty
System.out.println(avg.getAsDouble()); // NoSuchElementException
```

The OptionalDouble is empty. When we try to get the double out, we get a
NoSuchElementException . This is a runtime exception, so we don’t have to catch it. It is
also unchecked, so we don’t have to declare it. It is a good idea to check whether the
OptionalDouble is present before trying to get the double out. We can do that with
isPresent() :

```java
IntStream stream = IntStream.empty();
OptionalDouble avg = stream.average();
if (avg.isPresent()) System.out.println(avg.getAsDouble()); // 0.0
else System.out.println(avg.orElseGet(() -> Double.NaN)); // NaN
```

![](C:\Users\Mega%20Store\Desktop\Fawry\FunctionalProgramming\Photos\11.png)

u’ve learned enough to be able to get the maximum value from a stream of int primitives. If the stream is empty, we want to throw an exception:

```java
private static int max(IntStream ints) {
   OptionalInt optional = ints.max();
    return optional.orElseThrow(RuntimeException::new);
}
```

## Learning the Functional Interfaces for Primitives

Just as there are special streams and optional classes for primitives,
there are also special functional interfaces.
Luckily, most of them are for the double, int, and long types that you saw for streams
and optionals. There is one exception, which is BooleanSupplier. We will cover that before
introducing the ones for double, int, and long.

### Functional Interfaces for Boolean

BooleanSupplier is a separate type. It has one method to implement:
boolean getAsBoolean()
It works just as you’ve come to expect from functional interfaces, for example:

```java
BooleanSupplier b1 = () -> true;
BooleanSupplier b2 = () -> Math.random() > .5;
System.out.println(b1.getAsBoolean()); // true
System.out.println(b2.getAsBoolean()); // true or false
```

### Functional Interfaces for Double, Int, and Long

The functional interfaces for double, int, and long are similar to the ones for Boolean.
They have a single method to implement that returns the primitive type. The names are
DoubleSupplier, IntSupplier, and LongSupplier. Here are the methods:

```java
double getAsDouble()
int getAsInt()
long getAsLong()
```

Here are some examples:

```java
DoubleSupplier ds1 = () -> Math.random();
DoubleSupplier ds2 = Math::random;
System.out.println(ds1.getAsDouble());
System.out.println(ds2.getAsDouble());
```

```java
IntSupplier i1 = () -> 10;
IntSupplier i2 = () -> (int) (Math.random() * 10);
System.out.println(i1.getAsInt());
System.out.println(i2.getAsInt());
```

# Working with Advanced Stream Pipeline Concepts

ou’ve almost reached the end of learning about streams. We have only a few more topics left. You’ll see the relationship between streams and the underlying data, chaining
Optional and grouping collectors.

## Linking Streams to the Underlying Data

What do you think this outputs?

```java
List<String> cats = new ArrayList<>();
cats.add("Annie");
cats.add("Ripley");
Stream<String> stream = cats.stream();
cats.add("KC");
System.out.println(stream.count());
```

The stream is linked to the data source. When we add KC to the list, it is also added to
the stream. When we call count() , we get the number of elements in the stream. This is
the same as the number of elements in the list. The stream is linked to the list. This is
important to know when you are working with streams. You can’t modify the source while
operating on it with a stream.

## Chaining Optionals

By now, you are familiar with the benefits of chaining operations in a stream pipeline
Suppose that you are given an Optional<Integer> and asked to print the value, but
only if it is a three-digit number. Without functional programming, you could write the
following:

```java
private static void threeDigit(Optional<Integer> optional) {
    if (optional.isPresent()) { // outer if
    Integer num = optional.get();
    String string = "" + num;
    if (string.length() == 3) // inner if
    System.out.println(string);
 }
}
```

This is a lot of code to do something simple. We can do better with functional programming. Let’s start by chaining the calls:

```java
private static void threeDigit(Optional<Integer> optional) {
    optional.map(n -> "" + n)
    .filter(s -> s.length() == 3)
    .ifPresent(System.out::println);
}
```

## Collecting Results

You’ve seen how to use the collect() method to get results from a stream. You’ve also
seen how to use the Collectors class to get a collector. Now it’s time to put them together.
Let’s start with a simple example. Suppose that we want to get a list of the names of the
cats. We can do that with a stream:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
String word = stream.collect(StringBuilder::new,StringBuilder::append, StringBuilder::append).toString();
System.out.println(word); // wolf
```

## Collecting Using Basic Collectors

The Collectors class has a number of basic collectors. We’ll start with the ones that
you’ve already seen. The first one is toList() . It collects the data into a List . Here’s an
example:

```java
Stream<String> stream = Stream.of("w", "o", "l", "f");
List<String> list = stream.collect(Collectors.toList());
System.out.println(list); // [w, o, l, f]
```

The second one is joining() . It collects the data into a Set . Here’s an example:

```java
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
String result = ohMy.collect(Collectors.joining(", "));
System.out.println(result); // lions, tigers, bears
```

## Collecting into Maps

The Collectors class has a number of methods for collecting data into maps. The first
one is toMap() . It takes two functions and returns a Collector that collects elements into
a Map . The first function tells how to map the key. The second function tells how to map
the value. Here’s an example:

```java
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<String, Integer> map = ohMy.collect(Collectors.toMap(s -> s, String::length));
System.out.println(map); // {lions=5, bears=5, tigers=6}
```

## Collecting Using Grouping, Partitioning, and Mapping

Now suppose that we want to get groups of names by their length. We can do that by saying that we want to group by length:

```java
Stream<String> ohMy = Stream.of("lions", "tigers", "bears");
Map<Integer, List<String>> map = ohMy.collect(Collectors.groupingBy(String::length));
System.out.println(map); // {5=[lions, bears], 6=[tigers]}
```

# Summary

## Lambdas

Lambdas can reference static variables, instance variables, effectively final parameters, and
effectively final local variables.

## Functional Interface

A functional interface has a single abstract method. You
must know the functional interfaces:
■ Supplier<T>: Method get() returns T
■ Consumer<T>: Method accept(T t) returns void
■ BiConsumer<T>: Method accept(T t, U u) returns void
■ Predicate<T>: Method test(T t) returns boolean
■ BiPredicate<T>: Method test(T t, U u) returns boolean
■ Function<T, R>: Method apply(T t) returns R
■ BiFunction<T, U, R>: Method apply(T t, U u) returns R
■ UnaryOperator<T>: Method apply(T t) returns T
■ BinaryOperator<T>: Method apply(T t1, T t2) returns T

## Optional

An Optional can be empty or store a value. You can check if it contains a value with
ifPresent() and get() the value inside. There are also three methods that take functional interfaces as parameters: ifPresent(Consumer c), orElseGet(Supplier s), and
orElseThrow(Supplier s). There are three optional types for primitives: DoubleSupplier,
IntSupplier, and LongSupplier. These have the methods getDouble(), getInt(), and
getLong(), respectively.

## Streams

A stream pipeline has three parts. The source is required, and it creates the data in
the stream. There can be zero or more intermediate operations, which aren’t executed
until the terminal operation runs. Examples of intermediate operations include filter(),
flatMap(), and sorted(). Examples of terminal operations include allMatch(), count(),
and forEach().

## Primitive Streams

There are three primitive streams: DoubleStream, IntStream, and LongStream. In
addition to the usual Stream methods, they have range() and rangeClosed(). The call
range(1, 10) on IntStream and LongStream creates a stream of the primitives from 1 to 9
By contrast, rangeClosed(1, 10) creates a stream of the primitives from 1 to 10. The
primitive streams have math operations including average(), max(), and sum(). They also
have summaryStatistics() to get many statistics in one call. There are also functional
interfaces specific to streams. Except for BooleanSupplier, they are all for double, int, and
long primitives as well.

## Collectors

You can use a Collector to transform a stream into a traditional collection. You can
even group fields to create a complex map in one line. Partitioning works the same way as
grouping, except that the keys are always true and false. A partitioned map always has
two keys even if the value is empty for the key.

# Review Questions

1.  What is the output of the following?

```java
Stream<String> stream = Stream.iterate("", (s) -> s + "1");
System.out.println(stream.limit(2).map(x -> x + "2"));;
```

A. 12112
B. 212
C. 212112
D. java.util.stream.ReferencePipeline$3@4517d9a3
E. The code does not compile.
F. An exception is thrown.
G. The code hangs.

solution: D

2. What is the output of the following?

```java
Predicate<? super String> predicate = s -> s.startsWith("g");
Stream<String> stream1 = Stream.generate(() -> "growl! ");
Stream<String> stream2 = Stream.generate(() -> "growl! ");
boolean b1 = stream1.anyMatch(predicate);
boolean b2 = stream2.allMatch(predicate);
System.out.println(b1 + " " + b2);
```

A. true false
B. true true
C. java.util.stream.ReferencePipeline$3@4517d9a3
D. The code does not compile.
E. An exception is thrown.
F. The code hangs.

Solution: F

3. Which are true statements about terminal operations in a stream? (Choose all that apply.)

A. At most one terminal operation can exist in a stream pipeline.
B. Terminal operations are a required part of the stream pipeline in order to get a result.
C. Terminal operations have Stream as the return type.
D. The referenced Stream may be used after the calling a terminal operation.
E. The peek() method is an example of a terminal operation.

Solution: A,B

4. Select from the following statements and indicate the order in which they would appear to output 10 lines:

```java
Stream.generate(() -> "1")
L: .filter(x -> x.length() > 1)
M: .forEach(System.out::println)
N: .limit(10)
O: .peek(System.out::println)
```

A. L, N
B. L, N, O
C. L, N, M
D. L, N, M, O
E. L, O, M
F. N, M
G. N, O

Solution: F

5. Which of the following is true?

```java
List<Integer> l1 = Arrays.asList(1, 2, 3);
List<Integer> l2 = Arrays.asList(4, 5, 6);
List<Integer> l3 = Arrays.asList();
Stream.of(l1, l2, l3).map(x -> x + 1)
   .flatMap(x -> x.stream()).forEach(System.out::print);
```

A. The code compiles and prints 123456.
B. The code compiles and prints 234567.
C. The code compiles but does not print anything.
D. The code compiles but prints stream references.
E. The code runs infinitely.
F. The code does not compile.
G. The code throws an exception.

Solution: F

6. What is the output of the following?

```java
Stream<String> s = Stream.empty();
Stream<String> s2 = Stream.empty();
Map<Boolean, List<String>> p = s.collect(
 Collectors.partitioningBy(b -> b.startsWith("c")));
Map<Boolean, List<String>> g = s2.collect(
 Collectors.groupingBy(b -> b.startsWith("c")));
System.out.println(p + " " + g);
```

A. {} {}
B. {} {false=[], true=[]}
C. {false=[], true=[]} {}
D. {false=[], true=[]} {false=[], true=[]}
E. The code does not compile.
F. An exception is thrown.

Solution: C