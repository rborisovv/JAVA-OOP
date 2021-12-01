package restaurant.core;

import restaurant.common.OutputMessages;
import restaurant.core.interfaces.Controller;
import restaurant.entities.drinks.Fresh;
import restaurant.entities.drinks.Smoothie;
import restaurant.entities.healthyFoods.Food;
import restaurant.entities.healthyFoods.Salad;
import restaurant.entities.healthyFoods.VeganBiscuits;
import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.entities.drinks.interfaces.Beverages;
import restaurant.entities.tables.InGarden;
import restaurant.entities.tables.Indoors;
import restaurant.entities.tables.interfaces.Table;
import restaurant.repositories.interfaces.*;

import static restaurant.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private HealthFoodRepository<HealthyFood> healthFoodRepository;
    private BeverageRepository<Beverages> beverageRepository;
    private TableRepository<Table> tableRepository;
    private double totalMoney;

    public ControllerImpl(HealthFoodRepository<HealthyFood> healthFoodRepository, BeverageRepository<Beverages> beverageRepository, TableRepository<Table> tableRepository) {
        this.healthFoodRepository = healthFoodRepository;
        this.beverageRepository = beverageRepository;
        this.tableRepository = tableRepository;
        this.totalMoney = 0;
    }

    @Override
    public String addHealthyFood(String type, double price, String name) {
        //TODO:
        Food food;
        switch (type) {
            case "Salad":
                food = new Salad(name, price);
                existsInFoodRepository(name);
                this.healthFoodRepository.add(food);
                break;
            case "VeganBiscuits":
                food = new VeganBiscuits(name, price);
                existsInFoodRepository(name);
                this.healthFoodRepository.add(food);
                break;
        }
        return String.format(OutputMessages.FOOD_ADDED, name);
    }

    private void existsInFoodRepository(String name) {
        if (healthFoodRepository.foodByName(name) != null) {
            throw new IllegalArgumentException(String.format(FOOD_EXIST, name));
        }
    }

    @Override
    public String addBeverage(String type, int counter, String brand, String name) {
        //TODO:
        Beverages beverages;
        switch (type) {
            case "Smoothie":
                beverages = new Smoothie(name, counter, brand);
                existsInBeverageRepository(brand, name);
                this.beverageRepository.add(beverages);
                break;
            case "Fresh":
                beverages = new Fresh(name, counter, brand);
                existsInBeverageRepository(brand, name);
                this.beverageRepository.add(beverages);
                break;
        }
        return String.format(OutputMessages.BEVERAGE_ADDED, type, brand);
    }

    private void existsInBeverageRepository(String brand, String name) {
        if (this.beverageRepository.beverageByName(name, brand) != null) {
            throw new IllegalArgumentException(String.format(BEVERAGE_EXIST, name));
        }
    }

    @Override
    public String addTable(String type, int tableNumber, int capacity) {
        //TODO:
        Table table;
        switch (type) {
            case "Indoors":
                table = new Indoors(tableNumber, capacity);
                existsInTableRepository(tableNumber);
                this.tableRepository.add(table);
                break;
            case "InGarden":
                table = new InGarden(tableNumber, capacity);
                existsInTableRepository(tableNumber);
                this.tableRepository.add(table);
                break;
        }
        return String.format(OutputMessages.TABLE_ADDED, tableNumber);
    }

    private void existsInTableRepository(int tableNumber) {
        if (tableRepository.byNumber(tableNumber) != null) {
            throw new IllegalArgumentException(String.format(TABLE_IS_ALREADY_ADDED, tableNumber));
        }
    }

    @Override
    public String reserve(int numberOfPeople) {
        //TODO:
        Table foundTable = this.tableRepository.getAllEntities()
                .stream()
                .filter(table -> !table.isReservedTable() && table.getSize() >= numberOfPeople)
                .findFirst().orElse(null);
        if (foundTable == null) {
            return String.format(OutputMessages.RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }
        foundTable.reserve(numberOfPeople);
        return String.format(OutputMessages.TABLE_RESERVED, foundTable.getTableNumber(), numberOfPeople);
    }

    @Override
    public String orderHealthyFood(int tableNumber, String healthyFoodName) {
        //TODO:
        Table foundTable = this.tableRepository.byNumber(tableNumber);
        if (foundTable == null) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        }
        HealthyFood foundFood = this.healthFoodRepository.foodByName(healthyFoodName);
        if (foundFood == null) {
            return String.format(OutputMessages.NONE_EXISTENT_FOOD, healthyFoodName);
        }
        foundTable.orderHealthy(foundFood);
        return String.format(OutputMessages.FOOD_ORDER_SUCCESSFUL, healthyFoodName, tableNumber);
    }

    @Override
    public String orderBeverage(int tableNumber, String name, String brand) {
        //TODO:
        Table foundTable = this.tableRepository.byNumber(tableNumber);
        Beverages foundBeverages = this.beverageRepository.beverageByName(name, brand);
        if (foundTable == null) {
            return String.format(OutputMessages.WRONG_TABLE_NUMBER, tableNumber);
        } else if (foundBeverages == null) {
            return String.format(OutputMessages.NON_EXISTENT_DRINK, name, brand);
        }
        foundTable.orderBeverages(foundBeverages);
        return String.format(OutputMessages.BEVERAGE_ORDER_SUCCESSFUL, name, tableNumber);
    }

    @Override
    public String closedBill(int tableNumber) {
        //TODO:
        Table foundTable = this.tableRepository.byNumber(tableNumber);
        double bill = foundTable.bill();
        foundTable.clear();
        this.totalMoney += bill;
        return String.format(OutputMessages.BILL, tableNumber, bill);
    }


    @Override
    public String totalMoney() {
        //TODO:
        return String.format(OutputMessages.TOTAL_MONEY, totalMoney);
    }
}