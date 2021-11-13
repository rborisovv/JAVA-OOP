package vehicles;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    private static VehicleImpl car;
    private static VehicleImpl truck;
    private static VehicleImpl bus;

    public static void main(String[] args) throws IOException {
        String[] carInfo = splitVehicleInput();
        String[] truckInfo = splitVehicleInput();
        String[] busInfo = splitVehicleInput();
        car = initializeVehicle(Car.class, carInfo);
        truck = initializeVehicle(Truck.class, truckInfo);
        bus = initializeVehicle(Bus.class, busInfo);

        int numberOfCommands = Integer.parseInt(reader.readLine());
        for (int i = 0; i < numberOfCommands; i++) {
            String[] tokens = reader.readLine().split("\\s+");
            String command = tokens[0];
            String vehicle = tokens[1];
            double commandValue = Double.parseDouble(tokens[2]);
            switch (command) {
                case "Drive":
                    driveVehicle(vehicle, commandValue);
                    break;
                case "Refuel":
                    refuelVehicle(vehicle, commandValue);
                    break;
                case "DriveEmpty":
                    System.out.println(((Bus) bus).driveEmpty(commandValue));
                    break;
            }
        }
        System.out.printf("Car: %.2f%n", car.getFuelQuantity());
        System.out.printf("Truck: %.2f%n", truck.getFuelQuantity());
        System.out.printf("Bus: %.2f", bus.getFuelQuantity());
    }

    private static void refuelVehicle(String vehicle, double liters) {
        switch (vehicle) {
            case "Car":
                car.refuel(liters);
                break;
            case "Truck":
                truck.refuel(liters);
                break;
            case "Bus":
                bus.refuel(liters);
                break;
        }
    }

    private static void driveVehicle(String vehicle, double kilometers) {
        switch (vehicle) {
            case "Car":
                System.out.println(car.drive(kilometers, Car.class, Car.FUEL_CONSUMPTION_SUMMER_INCREASE));
                break;
            case "Truck":
                System.out.println(truck.drive(kilometers, Truck.class, Truck.FUEL_CONSUMPTION_SUMMER_INCREASE));
                break;
            case "Bus":
                System.out.println(bus.drive(kilometers, Bus.class, Bus.BUSS_INCREASED_CONSUMPTION));
                break;
        }
    }

    private static <E extends VehicleImpl> VehicleImpl initializeVehicle(Class<E> clazz, String[] vehicleInfo) {
        double fuelQuantity = Double.parseDouble(vehicleInfo[1]);
        double fuelConsumption = Double.parseDouble(vehicleInfo[2]);
        double tankCapacity = Double.parseDouble(vehicleInfo[3]);
        String className = clazz.getSimpleName();
        VehicleImpl vehicleImpl;
        switch (className) {
            case "Car":
                vehicleImpl = new Car(fuelQuantity, fuelConsumption, tankCapacity);
                break;
            case "Truck":
                vehicleImpl = new Truck(fuelQuantity, fuelConsumption, tankCapacity);
                break;
            case "Bus":
                vehicleImpl = new Bus(fuelQuantity, fuelConsumption, tankCapacity);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + className);
        }
        return vehicleImpl;
    }

    private static String[] splitVehicleInput() throws IOException {
        return reader.readLine().split("\\s+");
    }
}