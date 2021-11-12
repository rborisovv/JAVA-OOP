package telephony;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Smartphone implements Callable, Browsable {
    private final List<String> numbers;
    private final List<String> urls;

    public Smartphone(List<String> numbers,
                      List<String> urls) {
        this.numbers = numbers;
        this.urls = urls;
    }

    @Override
    public String browse() {
        String errorMessage = "Invalid URL!";
        String message = "Browsing: ";
        Pattern pattern = Pattern.compile("^[^0-9]+$");
        return accumulatePrintData(urls, pattern, errorMessage, message, true);
    }

    @Override
    public String call() {
        Pattern pattern = Pattern.compile("^[^A-Za-z]+$");
        String errorMessage = "Invalid number!";
        String message = "Calling... ";
        return accumulatePrintData(numbers, pattern, errorMessage, message, false);
    }

    private String accumulatePrintData(List<String> data, Pattern pattern, String errorMessage, String message, boolean placePostfix) {
        StringBuilder result = new StringBuilder();
        Iterator<String> iterator = data.iterator();
        while (iterator.hasNext()) {
            String element = iterator.next();
            Matcher matcher = pattern.matcher(element);
            if (matcher.find()) {
                result.append(message).append(element);
                if (placePostfix) {
                    result.append("!").append(System.lineSeparator());
                } else {
                    result.append(System.lineSeparator());
                }
            } else {
                result.append(errorMessage).append(System.lineSeparator());
            }
        }
        return result.toString().trim();
    }
}