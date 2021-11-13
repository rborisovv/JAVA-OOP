package vehicles;

import java.text.DecimalFormat;

public abstract class VehicleImpl implements Vehicle {
    private double fuelQuantity;
    private final double fuelConsumption;

    public VehicleImpl(double fuelQuantity, double fuelConsumption) {
        this.fuelQuantity = fuelQuantity;
        this.fuelConsumption = fuelConsumption;
    }

    protected double getFuelQuantity() {
        return fuelQuantity;
    }

    protected double getFuelConsumption() {
        return fuelConsumption;
    }

    protected void setFuelQuantity(double fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    @Override
    public <E extends VehicleImpl> String drive(double kilometers, Class<E> clazz) {
        String className = clazz.getSimpleName();
        String result;
        double usedFuel = (kilometers * (this.getFuelConsumption() + (isCar(className) ?
                Car.FUEL_CONSUMPTION_SUMMER_INCREASE :
                Truck.FUEL_CONSUMPTION_SUMMER_INCREASE)));
        double remainingFuel = this.getFuelQuantity() - usedFuel;
        if (remainingFuel >= 0) {
            this.setFuelQuantity(remainingFuel);
            DecimalFormat formatter = new DecimalFormat("##.##");
            result = String.format("%s travelled %s km", isCar(className) ? "Car" : "Truck", formatter.format(kilometers));
        } else {
            result = String.format("%s needs refueling", isCar(className) ? "Car" : "Truck");
        }
        return result;
    }

    private boolean isCar(String className) {
        return className.equals("Car");
    }
}