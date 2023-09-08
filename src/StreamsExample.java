import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StreamsExample {

    public static void main(final String[] args) {

        List<Author> authors = Library.getAuthors();
        
        banner("Authors information");
        // TODO With functional interfaces declared
        Consumer<Author> printer = System.out::println;
        authors
            .forEach(printer);
        // TODO With functional interfaces used directly
        authors
            .forEach(System.out::println);

        banner("Active authors");
        // TODO With functional interfaces declared
        Predicate<Author> filterAuthor = author -> author.active;
        authors
                .stream()
                .filter(filterAuthor)
                .forEach(printer);
        // TODO With functional interfaces used directly
        authors
                .stream()
                .filter(author -> author.active)
                .forEach(System.out::println);

        banner("Active books for all authors");
        // TODO With functional interfaces declared
        Predicate<Book> filterActiveBook = book -> book.published;
        Consumer<Book> printBooks = System.out::println;
        authors
                .stream()
                .flatMap(author -> author.books.stream())
                .filter(filterActiveBook)
                .forEach(printBooks);
        // TODO With functional interfaces used directly
        authors
                .stream()
                .flatMap(author -> author.books.stream())
                .filter(book -> book.published)
                .forEach(System.out::println);

        banner("Average price for all books in the library");
        // TODO With functional interfaces declared
        DoubleConsumer printAverage = System.out::println;
        authors
                .stream()
                .flatMap(author -> author.books.stream())
                .mapToDouble(book -> book.price)
                .average()
                .ifPresent(printAverage);
        // TODO With functional interfaces used directly
        authors
                .stream()
                .flatMap(author -> author.books.stream())
                .mapToDouble(book -> book.price)
                .average()
                .ifPresent(System.out::println);

        banner("Active authors that have at least one published book");
        // TODO With functional interfaces declared
        Predicate<Author> filterActiveAuthor = author -> author.active;
        Predicate<Author> filterPublishedBook = author -> author.books.stream().anyMatch(book -> book.published);
        authors
                .stream()
                .filter(filterActiveAuthor)
                .filter(filterPublishedBook)
                .forEach(printer);
        // TODO With functional interfaces used directly
        authors
                .stream()
                .filter(author -> author.active)
                .filter(author -> author.books.stream().anyMatch(book -> book.published))
                .forEach(System.out::println);
    }

    private static void banner(final String m) {
        System.out.println("#### " + m + " ####");
    }
}


class Library {
    public static List<Author> getAuthors() {
        return Arrays.asList(
            new Author("Author A", true, Arrays.asList(
                new Book("A1", 100, true),
                new Book("A2", 200, true),
                new Book("A3", 220, true))),
            new Author("Author B", true, Arrays.asList(
                new Book("B1", 80, true),
                new Book("B2", 80, false),
                new Book("B3", 190, true),
                new Book("B4", 210, true))),
            new Author("Author C", true, Arrays.asList(
                new Book("C1", 110, true),
                new Book("C2", 120, false),
                new Book("C3", 130, true))),
            new Author("Author D", false, Arrays.asList(
                new Book("D1", 200, true),
                new Book("D2", 300, false))),
            new Author("Author X", true, Collections.emptyList()));
    }
}

class Author {
    String name;
    boolean active;
    List<Book> books;

    Author(String name, boolean active, List<Book> books) {
        this.name = name;
        this.active = active;
        this.books = books;
    }

    @Override
    public String toString() {
        return name + "\t| " + (active ? "Active" : "Inactive");
    }
}

class Book {
    String name;
    int price;
    boolean published;

    Book(String name, int price, boolean published) {
        this.name = name;
        this.price = price;
        this.published = published;
    }

    @Override
    public String toString() {
        return name + "\t| " + "\t| $" + price + "\t| " + (published ? "Published" : "Unpublished");
    }
}
