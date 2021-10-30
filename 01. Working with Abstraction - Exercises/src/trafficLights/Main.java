package trafficLights;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static trafficLights.TrafficLight.printSignals;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        List<String> signals = Arrays.stream(reader.readLine()
                        .split("\\s+"))
                .collect(Collectors.toList());

        int numberOfTrafficLights = Integer.parseInt(reader.readLine());

        for (int i = 0; i < numberOfTrafficLights; i++) {
            signals = TrafficLight.changeSignals(signals);
            printSignals(signals);
        }
    }
}