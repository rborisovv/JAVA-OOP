package vehicles;

import java.text.DecimalFormat;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class VehicleImpl implements Vehicle {
    protected static final BiFunction<String, String, String> VEHICLE_DRIVEN_MSG =
            (vehicle, kilometers) -> String.format("%s travelled %s km", vehicle, kilometers);
    protected static final Function<String, String> VEHICLE_NEEDS_REFUELING_MSG =
            vehicle -> String.format("%s needs refueling", vehicle);

    private static final String FUEL_TANK_LESS_THAN_ZERO_MSG = "Fuel must be a positive number";
    private static final String FUEL_MORE_THAN_TANK_CAPACITY_MSG = "Cannot fit fuel in tank";

    private double fuelQuantity;
    private final double fuelConsumption;
    private double tankCapacity;

    public VehicleImpl(double fuelQuantity, double fuelConsumption, double tankCapacity) {
        this.fuelQuantity = fuelQuantity;
        this.fuelConsumption = fuelConsumption;
        this.setTankCapacity(tankCapacity);
    }

    protected double getFuelQuantity() {
        return fuelQuantity;
    }

    protected double getFuelConsumption() {
        return fuelConsumption;
    }

    protected void setFuelQuantity(double fuelQuantity) {
        if (fuelQuantity <= this.tankCapacity) {
            this.fuelQuantity = fuelQuantity;
        } else {
            System.out.println(FUEL_MORE_THAN_TANK_CAPACITY_MSG);
        }
    }

    public void setTankCapacity(double tankCapacity) {
        if (tankCapacity > 0) {
            this.tankCapacity = tankCapacity;
        } else {
            System.out.println(FUEL_TANK_LESS_THAN_ZERO_MSG);
        }
    }

    @Override
    public <E extends VehicleImpl> String drive(double kilometers, Class<E> clazz, double fuelConsumptionIncrease) {
        String className = clazz.getSimpleName();
        String result;
        double usedFuel;
        usedFuel = (kilometers * (this.getFuelConsumption() + fuelConsumptionIncrease));
        double remainingFuel = this.getFuelQuantity() - usedFuel;
        if (remainingFuel >= 0) {
            this.setFuelQuantity(remainingFuel);
            DecimalFormat formatter = new DecimalFormat("##.##");
            result = VEHICLE_DRIVEN_MSG.apply(className, formatter.format(kilometers));
        } else {
            result = VEHICLE_NEEDS_REFUELING_MSG.apply(className);
        }
        return result;
    }
}