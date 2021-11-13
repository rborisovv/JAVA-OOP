package vehicles;

public class Car extends VehicleImpl {
    static final double FUEL_CONSUMPTION_SUMMER_INCREASE = 0.9;

    public Car(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption, tankCapacity);
    }

    @Override
    public void refuel(double liters) {
        if (liters <= 0) {
            System.out.println("Fuel must be a positive number");
            return;
        }
        setFuelQuantity(getFuelQuantity() + liters);
    }
}