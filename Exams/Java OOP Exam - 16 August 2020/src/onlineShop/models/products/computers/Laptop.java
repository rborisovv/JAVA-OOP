package onlineShop.models.products.computers;

public class Laptop extends BaseComputer {
    private static final double LAPTOP_OVP = 10;

    public Laptop(int id, String manufacturer, String model, double price) {
        super(id, manufacturer, model, price, LAPTOP_OVP);
    }
}