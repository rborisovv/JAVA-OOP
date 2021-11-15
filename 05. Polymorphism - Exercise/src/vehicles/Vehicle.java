package vehicles;

public interface Vehicle {
    <E extends VehicleImpl> String drive(double kilometers, Class<E> clazz, double fuelConsumptionIncrease);

    void refuel(double liters);
}