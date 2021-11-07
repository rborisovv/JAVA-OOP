package telephony;

import java.util.List;
import java.util.function.Predicate;

public record Smartphone(List<String> numbers,
                         List<String> urls) implements Callable, Browsable {

    @Override
    public String browse() {
        String errorMessage = "Invalid URL!";
        String message = "Browsing: ";
        Predicate<Character> predicate = Character::isDigit;
        return accumulatePrintData(urls, predicate, errorMessage, message);
    }

    @Override
    public String call() {
        Predicate<Character> predicate = Character::isAlphabetic;
        String errorMessage = "Invalid number!";
        String message = "Calling... ";

        return accumulatePrintData(numbers, predicate, errorMessage, message);
    }

    private String accumulatePrintData(List<String> data, Predicate<Character> predicate, String errorMessage, String message) {
        StringBuilder result = new StringBuilder();
        for (String element : data) {
            boolean containsCriteria = false;
            char[] symbols = element.toCharArray();
            for (char symbol : symbols) {
                if (predicate.test(symbol)) {
                    containsCriteria = true;
                    break;
                }
            }
            if (containsCriteria) {
                result.append(errorMessage).append(System.lineSeparator());
            } else {
                result.append(message).append(element).append(System.lineSeparator());
            }
        }
        return result.toString().trim();
    }
}