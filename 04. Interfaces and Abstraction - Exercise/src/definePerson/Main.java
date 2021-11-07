package definePerson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int numberOfLines = Integer.parseInt(reader.readLine());
        List<Person> buyers = new ArrayList<>();
        for (int i = 0; i < numberOfLines; i++) {
            String[] tokens = reader.readLine().split("\\s+");
            String name = tokens[0];
            int age = Integer.parseInt(tokens[1]);
            if (tokens.length == 4) {
                String id = tokens[2];
                String birthDate = tokens[3];
                buyers.add(new Citizen(name, age, id, birthDate));
            } else if (tokens.length == 3) {
                String group = tokens[2];
                buyers.add(new Rebel(name, age, group));
            }
        }
        String buyerName = reader.readLine();
        while (!buyerName.equals("End")) {
            Optional<Person> person = validateBuyerIsPresent(buyers, buyerName);
            person.ifPresent(Person::buyFood);
            buyerName = reader.readLine();
        }
        System.out.println(buyers.stream().map(Buyer::getFood).reduce(0, (acc, el) -> acc += el));
    }

    private static Optional<Person> validateBuyerIsPresent(List<Person> buyers, String buyerName) {
        return buyers.stream().filter(p -> p.getName().equals(buyerName)).findFirst();
    }
}