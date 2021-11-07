package telephony;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        List<String> numbers = initializeCollection();
        List<String> urls = initializeCollection();

        Smartphone smartphone = new Smartphone(numbers, urls);
        System.out.println(smartphone.call());
        System.out.println(smartphone.browse());
    }

    private static List<String> initializeCollection() throws IOException {
        return Arrays.stream(reader.readLine().split("\\s+")).collect(Collectors.toList());
    }
}