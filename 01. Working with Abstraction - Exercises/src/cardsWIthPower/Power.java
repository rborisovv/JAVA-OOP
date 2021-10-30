package cardsWIthPower;

public enum Power {
    CLUBS(0),
    DIAMONDS(13),
    HEARTS(26),
    SPADES(39);

    private final int power;

    Power(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}