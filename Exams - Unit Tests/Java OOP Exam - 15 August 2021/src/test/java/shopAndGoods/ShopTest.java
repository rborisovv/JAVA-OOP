package shopAndGoods;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Map;

import static org.junit.Assert.*;

public class ShopTest {
    // TODO
    private Shop shop;

    @Before
    public void setUp() {
        this.shop = new Shop();
    }

    @Test
    public void testShouldSuccessfullyCreateShop() {
        assertNotNull(shop);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testShouldReturnUnmodifiableMap() {
        Map<String, Goods> shelves = this.shop.getShelves();
        assertTrue(shelves.containsKey("Shelves1"));
        shelves.put("newShelf", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenNoShelfToAddToFound() throws OperationNotSupportedException {
        this.shop.addGoods("MissingShelf", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenShelfIsTaken() throws OperationNotSupportedException {
        this.shop.addGoods("Shelves1", new Goods("Item", null));
        this.shop.addGoods("Shelves1", new Goods("new Item", null));
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowWhenAddingGoodsAlreadyExistingInShelves() throws OperationNotSupportedException {
        Goods goods = new Goods("Item", null);
        this.shop.addGoods("Shelves1", goods);
        this.shop.addGoods("Shelves2", goods);
    }

    @Test
    public void testShouldAddGoodsToShelf() throws OperationNotSupportedException {
        String result = this.shop.addGoods("Shelves1", new Goods("New Items", "7777"));
        Goods obtainedGoods = this.shop.getShelves().get("Shelves1");
        assertNotNull(obtainedGoods);
        assertEquals("New Items", obtainedGoods.getName());
        assertEquals("7777", obtainedGoods.getGoodsCode());
        assertEquals(String.format("Goods: %s is placed successfully!", obtainedGoods.getGoodsCode()), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenShelfDoesNotExist() {
        this.shop.removeGoods("shelves15", new Goods(null, null));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenShelfIsNotContainingGoods() throws OperationNotSupportedException {
        Goods goods = new Goods("Goods", "9999");
        this.shop.addGoods("Shelves1", goods);
        this.shop.removeGoods("Shelves1", new Goods("new Goods", "0000"));
    }

    @Test
    public void testShouldRemoveGoods() throws OperationNotSupportedException {
        Goods goods = new Goods("Goods", "9999");
        this.shop.addGoods("Shelves1", goods);
        String message = this.shop.removeGoods("Shelves1", goods);
        assertNull(this.shop.getShelves().get("Shelves1"));

        assertEquals(String.format("Goods: %s is removed successfully!", goods.getGoodsCode()), message);
    }
}