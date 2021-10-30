package cardRank;

import java.util.Arrays;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Card Ranks:");
        Consumer<DeckRanks> printDeckRanks = rank ->
                System.out.printf("Ordinal value: %d; Name value: %s%n",
                        rank.ordinal(), rank.name());
        Arrays.stream(DeckRanks.values()).forEach(printDeckRanks);
    }
}
