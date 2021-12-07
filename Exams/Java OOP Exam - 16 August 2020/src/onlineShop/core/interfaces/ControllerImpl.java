package onlineShop.core.interfaces;

import onlineShop.models.products.components.*;
import onlineShop.models.products.computers.Computer;
import onlineShop.models.products.computers.DesktopComputer;
import onlineShop.models.products.computers.Laptop;
import onlineShop.models.products.peripherals.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import static onlineShop.common.constants.ExceptionMessages.*;
import static onlineShop.common.constants.OutputMessages.*;

public class ControllerImpl implements Controller {
    private Collection<Computer> computers;

    public ControllerImpl() {
        this.computers = new ArrayList<>();
    }

    @Override
    public String addComputer(String computerType, int id, String manufacturer, String model, double price) {
        Computer computer;
        switch (computerType) {
            case "DesktopComputer":
                computer = new DesktopComputer(id, manufacturer, model, price);
                break;
            case "Laptop":
                computer = new Laptop(id, manufacturer, model, price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMPUTER_TYPE);
        }
        if (computers.stream().anyMatch(comp -> comp.getId() == id)) {
            throw new IllegalArgumentException(EXISTING_COMPUTER_ID);
        }
        computers.add(computer);
        return String.format(ADDED_COMPUTER, id);
    }

    @Override
    public String addPeripheral(int computerId, int id, String peripheralType, String manufacturer, String model, double price, double overallPerformance, String connectionType) {
        Computer foundComputer = findComputerById(computerId);
        assertComputerIsNotNull(foundComputer);
        boolean peripheralExists = foundComputer.getPeripherals().stream().anyMatch(peripheral -> peripheral.getId() == id);
        if (peripheralExists) {
            throw new IllegalArgumentException(EXISTING_PERIPHERAL_ID);
        }

        Peripheral peripheral;
        switch (peripheralType) {
            case "Headset":
                peripheral = new Headset(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Keyboard":
                peripheral = new Keyboard(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Monitor":
                peripheral = new Monitor(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            case "Mouse":
                peripheral = new Mouse(id, manufacturer, model, price, overallPerformance, connectionType);
                break;
            default:
                throw new IllegalArgumentException(INVALID_PERIPHERAL_TYPE);

        }
        foundComputer.addPeripheral(peripheral);
        return String.format(ADDED_PERIPHERAL,
                peripheral.getClass().getSimpleName(),
                peripheral.getId(),
                foundComputer.getId());
    }

    @Override
    public String removePeripheral(String peripheralType, int computerId) {
        Computer foundComputer = findComputerById(computerId);
        assertComputerIsNotNull(foundComputer);
        Peripheral peripheral = foundComputer.removePeripheral(peripheralType);
        foundComputer.getPeripherals().remove(peripheral);
        return String.format(REMOVED_PERIPHERAL, peripheralType, peripheral.getId());
    }

    @Override
    public String addComponent(int computerId, int id, String componentType, String manufacturer, String model, double price, double overallPerformance, int generation) {
        Computer foundComputer = findComputerById(computerId);
        assertComputerIsNotNull(foundComputer);
        Component foundComponent = findComponentById(id, foundComputer);
        if (foundComponent != null) {
            throw new IllegalArgumentException(EXISTING_COMPONENT_ID);
        }

        Component component;
        switch (componentType) {
            case "CentralProcessingUnit":
                component = new CentralProcessingUnit(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "Motherboard":
                component = new Motherboard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "PowerSupply":
                component = new PowerSupply(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "RandomAccessMemory":
                component = new RandomAccessMemory(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "SolidStateDrive":
                component = new SolidStateDrive(id, manufacturer, model, price, overallPerformance, generation);
                break;
            case "VideoCard":
                component = new VideoCard(id, manufacturer, model, price, overallPerformance, generation);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COMPONENT_TYPE);
        }
        foundComputer.addComponent(component);
        return String.format(ADDED_COMPONENT,
                componentType, id, computerId);
    }

    @Override
    public String removeComponent(String componentType, int computerId) {
        Computer foundComputer = findComputerById(computerId);
        assertComputerIsNotNull(foundComputer);
        Component component = foundComputer.removeComponent(componentType);
        foundComputer.getComponents().remove(component);
        return String.format(REMOVED_COMPONENT, componentType, component.getId());
    }

    @Override
    public String buyComputer(int id) {
        Computer foundComputer = findComputerById(id);
        assertComputerIsNotNull(foundComputer);
        computers.remove(foundComputer);
        return foundComputer.toString();
    }

    @Override
    public String BuyBestComputer(double budget) {
        Computer foundComputer = computers.stream()
                .filter(computer -> computer.getPrice() <= budget)
                .max(Comparator.comparing(Computer::getOverallPerformance))
                .orElse(null);
        if (foundComputer == null || computers.isEmpty()) {
            throw new IllegalArgumentException(String.format(CAN_NOT_BUY_COMPUTER, budget));
        }
        computers.remove(foundComputer);
        return foundComputer.toString();
    }

    @Override
    public String getComputerData(int id) {
        Computer foundComputer = findComputerById(id);
        assertComputerIsNotNull(foundComputer);
        return foundComputer.toString();
    }

    private Computer findComputerById(int computerId) {
        return computers.stream().filter(computer -> computer.getId() == computerId)
                .findFirst()
                .orElse(null);
    }

    private void assertComputerIsNotNull(Computer foundComputer) {
        if (foundComputer == null) {
            throw new IllegalArgumentException(NOT_EXISTING_COMPUTER_ID);
        }
    }

    private Component findComponentById(int id, Computer foundComputer) {
        return foundComputer.getComponents().stream().filter(component -> component.getId() == id)
                .findFirst().orElse(null);
    }
}