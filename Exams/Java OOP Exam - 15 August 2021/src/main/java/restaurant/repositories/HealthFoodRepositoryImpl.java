package restaurant.repositories;

import restaurant.entities.healthyFoods.interfaces.HealthyFood;
import restaurant.repositories.interfaces.HealthFoodRepository;

import java.util.ArrayList;
import java.util.Collection;

public class HealthFoodRepositoryImpl implements HealthFoodRepository<HealthyFood> {
    private Collection<HealthyFood> foods;

    public HealthFoodRepositoryImpl() {
        this.foods = new ArrayList<>();
    }

    @Override
    public HealthyFood foodByName(String name) {
        return foods.stream()
                .filter(food -> food.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<HealthyFood> getAllEntities() {
        return this.foods;
    }

    @Override
    public void add(HealthyFood entity) {
        this.foods.add(entity);
    }
}