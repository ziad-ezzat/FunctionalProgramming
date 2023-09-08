import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface Gorilla {
    String move();
}

class GorillaFamily {
    String walk = "walk";

    void everyonePlay(boolean baby) {
        String approach = "amble";
//        approach = "run";
//        walk = "walk fast";
        play(() -> walk);  // Uses an instance variable (walk)
        play(() -> baby ? "hitch a ride" : "run");  // Uses an effectively final method parameter (baby)
        play(() -> approach);  // Uses an effectively final local variable (approach)
    }

    void play(Gorilla g) {
        System.out.println(g.move());
    }
}

public class Main {
    public static void main(String[] args) {
//        GorillaFamily gorillaFamily = new GorillaFamily();
//        gorillaFamily.everyonePlay(true);
//        System.out.println("---");
//        gorillaFamily.everyonePlay(false);
// --------------------------------------------------
//        Supplier<Integer> randomNumberSupplier = () -> (int) (Math.random() * 100);
//        int randomValue = randomNumberSupplier.get();
//        System.out.println(randomValue);
//
//        Consumer<Integer> printUpperCase = s -> System.out.println(s+5);
//        printUpperCase.accept(5);
//
//        BiConsumer<String, Integer> printPair = (s, i) -> System.out.println(s + ": " + i);
//        printPair.accept("Value", 42);
//
//        Predicate<Integer> isEven = num -> num % 2 == 0;
//        System.out.println(isEven.test(4));  // true
//
//        BiPredicate<String, Integer> isLengthEqualTo = (s, length) -> s.length() == length;
//        System.out.println(isLengthEqualTo.test("hello", 5));  // true
//
//        Function<Integer, String> intToString = i -> "Number: " + i;
//        String result = intToString.apply(42);
//        System.out.println(result);
//
//        BiFunction<Integer, Integer, Double> calculateHypotenuse = (a, b) -> Math.sqrt(a * a + b * b);
//        double hypotenuse = calculateHypotenuse.apply(3, 4);
//        System.out.println(hypotenuse);
//
//        UnaryOperator<Integer> square = n -> n * n;
//        int squaredValue = square.apply(5);
//        System.out.println(squaredValue);
//
//        BinaryOperator<Integer> add = (a, b) -> a + b;
//        int sum = add.apply(3, 4);
//        System.out.println(sum);
// --------------------------------------------------

        // example to show how reduce works in streams

        // 1. create a stream of integers.
        // 2. reduce the stream to a single value by adding all the elements.
        // 3. print the result.
//        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
//        Integer sum = stream.reduce(0, (a, b) -> a + b);
//        System.out.println(sum);  // 15

//        Stream<String> stream = Stream.of("w", "o", "l", "f");
//        String word = stream.collect(StringBuilder::new,StringBuilder::append,StringBuilder::append).toString();
//        System.out.println(word); // wolf

        Stream<String> s = Stream.empty();
        Stream<String> s2 = Stream.empty();
        Map<Boolean, List<String>> p = s.collect(
                Collectors.partitioningBy(b -> b.startsWith("c")));
        Map<Boolean, List<String>> g = s2.collect(
                Collectors.groupingBy(b -> b.startsWith("c")));
        System.out.println(p + " " + g);
    }
}