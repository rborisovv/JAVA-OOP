package catHouse.core;

import catHouse.entities.cat.Cat;
import catHouse.entities.cat.LonghairCat;
import catHouse.entities.cat.ShorthairCat;
import catHouse.entities.houses.House;
import catHouse.entities.houses.LongHouse;
import catHouse.entities.houses.ShortHouse;
import catHouse.entities.toys.Ball;
import catHouse.entities.toys.Mouse;
import catHouse.entities.toys.Toy;
import catHouse.repositories.ToyRepository;

import java.util.ArrayList;
import java.util.Collection;

import static catHouse.common.ConstantMessages.*;
import static catHouse.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private final ToyRepository toys;
    private final Collection<House> houses;

    public ControllerImpl() {
        this.toys = new ToyRepository();
        this.houses = new ArrayList<>();
    }

    @Override
    public String addHouse(String type, String name) {
        House house;
        switch (type) {
            case "ShortHouse":
                house = new ShortHouse(name);
                break;
            case "LongHouse":
                house = new LongHouse(name);
                break;
            default:
                throw new NullPointerException(INVALID_HOUSE_TYPE);
        }
        houses.add(house);
        return String.format(SUCCESSFULLY_ADDED_HOUSE_TYPE, type);
    }

    @Override
    public String buyToy(String type) {
        Toy toy;
        switch (type) {
            case "Ball":
                toy = new Ball();
                break;
            case "Mouse":
                toy = new Mouse();
                break;
            default:
                throw new IllegalArgumentException(INVALID_TOY_TYPE);
        }
        toys.buyToy(toy);
        return String.format(SUCCESSFULLY_ADDED_TOY_TYPE, type);
    }

    @Override
    public String toyForHouse(String houseName, String toyType) {
        Toy foundToy = findToyByType(toyType);
        if (foundToy == null) {
            throw new IllegalArgumentException(String.format(NO_TOY_FOUND, toyType));
        }
        House foundHouse = findHouseByName(houseName);
        assert foundHouse != null;
        foundHouse.buyToy(foundToy);
        toys.removeToy(foundToy);
        return String.format(SUCCESSFULLY_ADDED_TOY_IN_HOUSE, toyType, houseName);
    }

    private Toy findToyByType(String toyType) {
        return this.toys.getToys()
                .stream()
                .filter(toy -> toy.getClass().getSimpleName().equals(toyType))
                .findFirst()
                .orElse(null);
    }

    private House findHouseByName(String houseName) {
        return houses.stream()
                .filter(house -> house.getName().equals(houseName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String addCat(String houseName, String catType, String catName, String catBreed, double price) {
        Cat cat;
        switch (catType) {
            case "ShorthairCat":
                cat = new ShorthairCat(catName, catBreed, price);
                break;
            case "LonghairCat":
                cat = new LonghairCat(catName, catBreed, price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_CAT_TYPE);
        }
        House foundHouse = findHouseByName(houseName);

        assert foundHouse != null;
        if (cat.getClass().getSimpleName().equals("ShorthairCat")) {
            if (foundHouse.getClass().getSimpleName().equals("LongHouse")) {
                return UNSUITABLE_HOUSE;
            }
            foundHouse.addCat(cat);
        }
        if (cat.getClass().getSimpleName().equals("LonghairCat")) {
            if (foundHouse.getClass().getSimpleName().equals("ShortHouse")) {
                return UNSUITABLE_HOUSE;
            }
            foundHouse.addCat(cat);
        }
        return String.format(SUCCESSFULLY_ADDED_CAT_IN_HOUSE, catType, houseName);
    }

    @Override
    public String feedingCat(String houseName) {
        House foundHouse = findHouseByName(houseName);
        assert foundHouse != null;
        foundHouse.getCats().forEach(Cat::eating);
        return String.format(FEEDING_CAT, foundHouse.getCats().size());
    }

    @Override
    public String sumOfAll(String houseName) {
        House foundHouse = findHouseByName(houseName);

        assert foundHouse != null;
        double toysSum = foundHouse.getToys().stream().mapToDouble(Toy::getPrice).sum();
        double catsSum = foundHouse.getCats().stream().mapToDouble(Cat::getPrice).sum();

        return String.format(VALUE_HOUSE, houseName, toysSum + catsSum);
    }

    @Override
    public String getStatistics() {
        StringBuilder housesInfo = new StringBuilder();
        houses.forEach(house -> housesInfo.append(house.getStatistics()).append(System.lineSeparator()));
        return housesInfo.toString().trim();
    }
}