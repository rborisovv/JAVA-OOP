package vehicles;

public class Truck extends VehicleImpl {
    static final double FUEL_CONSUMPTION_SUMMER_INCREASE = 1.6;

    public Truck(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption, tankCapacity);
    }

    @Override
    public void refuel(double liters) {
        if (liters <= 0) {
            System.out.println("Fuel must be a positive number");
            return;
        }
        setFuelQuantity(getFuelQuantity() + (liters * 0.95));
    }
}