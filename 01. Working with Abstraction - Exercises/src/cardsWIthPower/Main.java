package cardsWIthPower;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String rank = reader.readLine();
        String power = reader.readLine();

        Rank cardRank = Enum.valueOf(Rank.class, rank);
        Power cardPower = Enum.valueOf(Power.class, power);
        Card card = new Card(cardPower, cardRank);
        System.out.println(card);
    }
}