package onlineShop.models.products.computers;

import onlineShop.models.products.BaseProduct;
import onlineShop.models.products.Product;
import onlineShop.models.products.components.Component;
import onlineShop.models.products.peripherals.Peripheral;

import java.util.ArrayList;
import java.util.List;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.*;

public abstract class BaseComputer extends BaseProduct implements Computer {
    private List<Component> components;
    private List<Peripheral> peripherals;

    public BaseComputer(int id, String manufacturer, String model,
                        double price, double overallPerformance) {
        super(id, manufacturer, model, price, overallPerformance);
        this.components = new ArrayList<>();
        this.peripherals = new ArrayList<>();
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public String getManufacturer() {
        return super.getManufacturer();
    }

    @Override
    public String getModel() {
        return super.getModel();
    }

    @Override
    public double getPrice() {
        double componentsPrice = components.stream()
                .mapToDouble(Component::getPrice)
                .sum();
        double peripheralsPrice =
                peripherals.stream()
                        .mapToDouble(Peripheral::getPrice)
                        .sum();
        return super.getPrice() + componentsPrice + peripheralsPrice;

    }

    @Override
    public double getOverallPerformance() {
        if (components.isEmpty()) {
            return super.getOverallPerformance();
        }
        double avgComponentSum = components.stream()
                .mapToDouble(Component::getOverallPerformance)
                .average().orElse(0);
        return super.getOverallPerformance() + avgComponentSum;
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public List<Peripheral> getPeripherals() {
        return peripherals;
    }

    @Override
    public void addComponent(Component component) {
        if (components.contains(component)) {
            throw new IllegalArgumentException(String.format(EXISTING_COMPONENT,
                    component, this.getClass().getSimpleName(),
                    this.getId()));
        }
        components.add(component);
    }

    @Override
    public Component removeComponent(String componentType) {
        Component foundComponent = components.stream()
                .filter(component -> component.getClass().getSimpleName().equals(componentType))
                .findFirst()
                .orElse(null);
        if (components.isEmpty() || foundComponent == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_COMPONENT, componentType,
                    this.getClass().getSimpleName(), this.getId()));
        }
        components.remove(foundComponent);
        return foundComponent;
    }

    @Override
    public void addPeripheral(Peripheral peripheral) {
        Peripheral foundPeripheral = findPeripheralById(peripheral.getClass().getSimpleName());
        assertPeripheralIsNotNull(peripheral, foundPeripheral);
        peripherals.add(peripheral);
    }

    private void assertPeripheralIsNotNull(Peripheral peripheral, Peripheral foundPeripheral) {
        if (foundPeripheral != null) {
            throw new IllegalArgumentException(String.format(EXISTING_PERIPHERAL,
                    peripheral,
                    this.getClass().getSimpleName(),
                    this.getId()));
        }
    }

    @Override
    public Peripheral removePeripheral(String peripheralType) {
        Peripheral foundPeripheral = findPeripheralById(peripheralType);
        if (peripherals.isEmpty() || foundPeripheral == null) {
            throw new IllegalArgumentException(String.format(NOT_EXISTING_PERIPHERAL,
                    peripheralType, this.getClass().getSimpleName(),
                    this.getId()));
        }
        peripherals.remove(foundPeripheral);
        return foundPeripheral;
    }

    private Peripheral findPeripheralById(String peripheralType) {
        return peripherals.stream()
                .filter(peripheral -> peripheral.getClass().getSimpleName().equals(peripheralType))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format(PRODUCT_TO_STRING,
                        this.getOverallPerformance(), this.getPrice(),
                        this.getClass().getSimpleName(), super.getManufacturer(),
                        super.getModel(), super.getId()))
                .append(System.lineSeparator())
                .append(String.format(" " + COMPUTER_COMPONENTS_TO_STRING, components.size()))
                .append(System.lineSeparator());
        components.stream().map(Component::toString).forEach(component -> result.append("  ")
                .append(component).append(System.lineSeparator()));
        double overallPeripheralsPerformance = peripherals.stream()
                .mapToDouble(Product::getOverallPerformance)
                .average().orElse(0);
        int peripheralsSize = peripherals.size();
        result.append(String.format(" " + COMPUTER_PERIPHERALS_TO_STRING, peripheralsSize, overallPeripheralsPerformance))
                .append(System.lineSeparator());
        peripherals.stream()
                .map(Peripheral::toString)
                .forEach(peripheral -> result.append("  ")
                        .append(peripheral).append(System.lineSeparator()));
        return result.toString().trim();
    }
}