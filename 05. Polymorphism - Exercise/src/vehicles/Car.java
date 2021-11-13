package vehicles;

public class Car extends VehicleImpl {
    static final double FUEL_CONSUMPTION_SUMMER_INCREASE = 0.9;

    public Car(double fuelQuantity, double fuelConsumption) {
        super(fuelQuantity, fuelConsumption);
    }

    @Override
    public void refuel(double liters) {
        setFuelQuantity(getFuelQuantity() + liters);
    }
}