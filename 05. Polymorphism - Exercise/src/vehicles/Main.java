package vehicles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    private static VehicleImpl car;
    private static VehicleImpl truck;

    public static void main(String[] args) throws IOException {
        String[] carInfo = splitVehicleInput();
        String[] truckInfo = splitVehicleInput();
        car = initializeVehicle(Car.class, carInfo);
        truck = initializeVehicle(Truck.class, truckInfo);

        int numberOfCommands = Integer.parseInt(reader.readLine());
        for (int i = 0; i < numberOfCommands; i++) {
            String[] tokens = reader.readLine().split("\\s+");
            String command = tokens[0];
            String vehicle = tokens[1];
            switch (command) {
                case "Drive":
                    double kilometers = Double.parseDouble(tokens[2]);
                    driveVehicle(vehicle, kilometers);
                    break;
                case "Refuel":
                    double liters = Double.parseDouble(tokens[2]);
                    refuelVehicle(vehicle, liters);
                    break;
            }
        }
        System.out.printf("Car: %.2f%n", car.getFuelQuantity());
        System.out.printf("Truck: %.2f%n", truck.getFuelQuantity());
    }

    private static void refuelVehicle(String vehicle, double liters) {
        if (isCar(vehicle)) {
            car.refuel(liters);
        } else {
            truck.refuel(liters);
        }
    }

    private static boolean isCar(String vehicle) {
        return vehicle.equals("Car");
    }

    private static void driveVehicle(String vehicle, double kilometers) {
        if (isCar(vehicle)) {
            System.out.println(car.drive(kilometers, Car.class));
        } else {
            System.out.println(truck.drive(kilometers, Truck.class));
        }
    }

    private static <E extends VehicleImpl> VehicleImpl initializeVehicle(Class<E> clazz, String[] vehicleInfo) {
        double fuelQuantity = Double.parseDouble(vehicleInfo[1]);
        double fuelConsumption = Double.parseDouble(vehicleInfo[2]);
        VehicleImpl vehicleImpl;
        if (isCar(clazz.getSimpleName())) {
            vehicleImpl = new Car(fuelQuantity, fuelConsumption);
        } else {
            vehicleImpl = new Truck(fuelQuantity, fuelConsumption);
        }
        return vehicleImpl;
    }

    private static String[] splitVehicleInput() throws IOException {
        return reader.readLine().split("\\s+");
    }
}