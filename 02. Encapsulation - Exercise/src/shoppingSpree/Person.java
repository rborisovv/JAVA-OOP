package shoppingSpree;

import java.util.ArrayList;
import java.util.List;

public class Person implements Naming {
    private String name;
    private double money;
    private final List<Product> products;

    public Person(String name, double money) {
        this.setName(name);
        this.setMoney(money);
        this.products = new ArrayList<>();
    }

    private void setName(String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    private void setMoney(double money) {
        if (money < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
        this.money = money;
    }

    public void buyProduct(Product product) {
        if (this.money >= product.getCost()) {
            this.products.add(product);
            this.money -= product.getCost();
            System.out.printf("%s bought %s\n", this.name, product.getName());
        } else {
            throw new IllegalStateException(String.format("%s can't afford %s", this.name, product.getName()));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
}