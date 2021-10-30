package cardSuit;

import java.util.Arrays;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Card Suits:");
        Consumer<Suits> printSuits = suit ->
                System.out.printf("Ordinal value: %d; Name value: %s%n",
                        suit.ordinal(), suit.name());
        Arrays.stream(Suits.values()).forEach(printSuits);
    }
}