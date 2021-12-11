package catHouse.entities.houses;

import catHouse.entities.cat.Cat;
import catHouse.entities.toys.Toy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static catHouse.common.ExceptionMessages.HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY;

public abstract class BaseHouse implements House {
    private String name;
    private final int capacity;
    private final Collection<Toy> toys;
    private final Collection<Cat> cats;

    public BaseHouse(String name, int capacity) {
        this.setName(name);
        this.capacity = capacity;
        this.toys = new ArrayList<>();
        this.cats = new ArrayList<>();
    }

    @Override
    public int sumSoftness() {
        return this.toys.stream()
                .mapToInt(Toy::getSoftness)
                .sum();
    }

    @Override
    public void addCat(Cat cat) {
        if (cats.size() >= capacity) {
            throw new IllegalStateException("Not enough capacity for this cat.");
        }
        cats.add(cat);
    }

    @Override
    public void removeCat(Cat cat) {
        cats.remove(cat);
    }

    @Override
    public void buyToy(Toy toy) {
        toys.add(toy);
    }

    @Override
    public void feeding() {
        cats.forEach(Cat::eating);
    }

    @Override
    public String getStatistics() {
        String result = String.format("%s %s:%n",
                name, this.getClass().getSimpleName()) +
                String.format("Cats: %s%n",
                        cats.size() == 0 ? "none"
                                : cats.stream().map(Cat::getName)
                                .collect(Collectors.joining(" "))) +
                String.format("Toys: %d Softness: %d",
                        toys.size(), this.sumSoftness());
        return result.trim();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new NullPointerException(HOUSE_NAME_CANNOT_BE_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    @Override
    public Collection<Cat> getCats() {
        return cats;
    }

    @Override
    public Collection<Toy> getToys() {
        return toys;
    }
}