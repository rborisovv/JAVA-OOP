package vehicles;

public class Truck extends VehicleImpl {
    static final double FUEL_CONSUMPTION_SUMMER_INCREASE = 1.6;

    public Truck(double fuelQuantity, double fuelConsumption) {
        super(fuelQuantity, fuelConsumption);
    }

    @Override
    public void refuel(double liters) {
        setFuelQuantity(getFuelQuantity() + (liters * 0.95));
    }
}