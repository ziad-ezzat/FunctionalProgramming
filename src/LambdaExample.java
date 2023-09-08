import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class LambdaExample {

    public static void main(final String[] args) {

        final UsersRepository repository = new UsersRepository();

        banner("Listing all users");
        repository.select(null, null);

        banner("Listing users with age > 5 sorted by name");
        Predicate<User> filterAge5 = user -> user.age > 5;
        Comparator<User> orderName = (user1, user2) -> user1.name.compareTo(user2.name);
        repository.select(filterAge5, orderName);
        // -------------
        repository.select(user -> user.age > 5,(user1,user2) -> user1.name.compareTo(user2.name));

        banner("Listing users with age < 10 sorted by age");
        // TODO once using anonymous objects and once using lambda expressions
        Predicate<User> filterAge10 = user -> user.age < 10;
        Comparator<User> orderAge = (user1, user2) -> user2.age - user1.age ;
        repository.select(filterAge10,orderAge);
        // -------------
        repository.select(user -> user.age < 10,(user1, user2) -> user2.age - user1.age);

        banner("Listing active users sorted by name");
        Predicate<User> filterActive = user -> user.active;
        repository.select(filterActive,orderName);
        // -------------
        repository.select(user -> user.active, (user1,user2) -> user1.name.compareTo(user2.name));

        banner("Listing active users with age > 8 sorted by name");
        Predicate<User> filterActiveAge = user -> user.active && user.age > 8;
        repository.select(filterActiveAge,orderName);
        // -------------
        repository.select(user -> user.active && user.age > 8, (user1,user2) -> user1.name.compareTo(user2.name));
    }

    private static void banner(final String m) {
        System.out.println("#### " + m + " ####");
    }
}

class UsersRepository {
    List<User> users;

    UsersRepository() {
        users = Arrays.asList(
            new User("Seven", 7, true),
            new User("Four", 4, false),
            new User("Eleven", 11, true),
            new User("Three", 3, true),
            new User("Nine", 9, false),
            new User("One", 1, true),
            new User("Twelve", 12, true));
    }

    void select(final Predicate<User> filter, final Comparator<User> order) {
        Stream<User> usersStream = users.stream();

        if (filter != null) {
            usersStream = usersStream.filter(filter);
        }
        if (order != null) {
            usersStream = usersStream.sorted(order);
        }
        usersStream.forEach(System.out::println);
    }
}

class User {
    String name;
    int age;
    boolean active;

    User(final String name, final int age, boolean active) {
        this.name = name;
        this.age = age;
        this.active = active;
    }

    @Override
    public String toString() {
        return name + "\t| " + age;
    }
}