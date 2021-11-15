package vehicles;

import java.text.DecimalFormat;

public class Bus extends VehicleImpl {
    static final double BUS_INCREASED_CONSUMPTION = 1.4;

    public Bus(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        super(fuelQuantity, fuelConsumption, tankCapacity);
    }

    public String driveEmpty(double kilometers) {
        double usedFuel = kilometers * this.getFuelConsumption();
        double remainingFuel = this.getFuelQuantity() - usedFuel;
        String className = this.getClass().getSimpleName();
        if (remainingFuel >= 0) {
            this.setFuelQuantity(this.getFuelQuantity() - usedFuel);
        } else {
            return VehicleImpl.VEHICLE_NEEDS_REFUELING_MSG.apply(className);
        }
        DecimalFormat formatter = new DecimalFormat("##.##");
        return VehicleImpl.VEHICLE_DRIVEN_MSG.apply(className, formatter.format(kilometers));
    }

    @Override
    public void refuel(double liters) {
        if (liters <= 0) {
            System.out.println("Fuel must be a positive number");
            return;
        }
        this.setFuelQuantity(getFuelQuantity() + liters);
    }
}