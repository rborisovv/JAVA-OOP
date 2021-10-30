package trafficLights;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class TrafficLight {

    public static List<String> changeSignals(List<String> signals) {
        List<String> changedSignals = new ArrayList<>();
        Function<String, String> changeLight = signal -> {
            switch (signal) {
                case "RED" -> signal = "GREEN";
                case "GREEN" -> signal = "YELLOW";
                case "YELLOW" -> signal = "RED";
            }
            return signal;
        };
        signals.stream().map(changeLight).forEach(changedSignals::add);
        return changedSignals;
    }

    public static void printSignals(List<String> signals) {
        StringBuilder result = new StringBuilder();
        Consumer<String> printSignals = str -> result.append(str).append("\s");
        signals.forEach(printSignals);
        System.out.println(result.toString().trim());
    }
}