package vehicles;

public interface Vehicle {
    <E extends VehicleImpl>String drive(double kilometers, Class<E> clazz);

    void refuel(double liters);
}
