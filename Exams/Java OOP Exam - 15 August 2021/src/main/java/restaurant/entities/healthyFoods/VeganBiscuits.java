package restaurant.entities.healthyFoods;

public class VeganBiscuits extends Food {
    private static final double INITIAL_VEGAN_BUISCUIT_PORTION = 205;

    public VeganBiscuits(String name, double price) {
        super(name, INITIAL_VEGAN_BUISCUIT_PORTION, price);
    }
}