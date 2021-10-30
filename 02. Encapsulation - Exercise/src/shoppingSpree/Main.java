package shoppingSpree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        List<Person> people = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        String[] peopleTokens = reader.readLine().split(";");
        String[] productsTokens = reader.readLine().split(";");
        fillData(peopleTokens, Person.class, people);
        fillData(productsTokens, Product.class, products);

        String buyCommand = reader.readLine();
        while (!buyCommand.equals("END")) {
            String[] tokens = buyCommand.split("\\s+");
            String person = tokens[0];
            String product = tokens[1];
            try {
                Person foundPerson = findByGivenName(people, person).orElseThrow(NoSuchElementException::new);
                Product foundProduct = findByGivenName(products, product).orElseThrow(NoSuchElementException::new);
                foundPerson.buyProduct(foundProduct);
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                System.out.close();
            }
            buyCommand = reader.readLine();
        }
        Consumer<Person> printPeople = person -> {
            if (person.getProducts().isEmpty()) {
                System.out.printf("%s - Nothing bought\n", person.getName());
            } else {
                System.out.printf("%s - %s\n", person.getName(),
                        person.getProducts().stream()
                                .map(Product::getName)
                                .collect(Collectors.joining(", ")));
            }
        };
        people.forEach(printPeople);
    }

    private static <E extends Naming> Optional<E> findByGivenName(List<E> list, String name) {
        return list.stream().filter(e -> e.getName().equals(name)).findFirst();
    }

    @SuppressWarnings("unchecked")
    private static <E> void fillData(String[] tokens, Class<E> eClass, List<E> list) {
        for (String token : tokens) {
            String name = token.split("=")[0];
            double money = Double.parseDouble(token.split("=")[1]);
            try {
                if (eClass.getSimpleName().equals("Person")) {
                    list.add((E) new Person(name, money));
                } else if (eClass.getSimpleName().equals("Product")) {
                    list.add((E) new Product(name, money));
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                System.out.close();
            }
        }
    }
}