package onlineShop.models.products.computers;

public class DesktopComputer extends BaseComputer {
    private static final double DESKTOP_COMPUTER_OVP = 15;

    public DesktopComputer(int id, String manufacturer, String model, double price) {
        super(id, manufacturer, model, price, DESKTOP_COMPUTER_OVP);
    }
}