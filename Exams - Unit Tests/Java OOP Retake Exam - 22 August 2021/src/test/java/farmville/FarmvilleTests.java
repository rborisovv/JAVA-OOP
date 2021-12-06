package farmville;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FarmvilleTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Farm

    @Test(expected = NullPointerException.class)
    public void testShouldThrowWhenInstantiatingWithNullName() {
        new Farm(null, 0);
    }

    @Test(expected = NullPointerException.class)
    public void testShouldThrowWhenPassingBlankNameToCtor() {
        new Farm("", 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenPassingNegativeCapacityToCtor() {
        new Farm("farm", -1);
    }

    @Test
    public void testShouldSuccessfullyInitializeFarm() {
        Farm farm = new Farm("farm", 5);
        assertNotNull(farm);
        assertEquals("farm", farm.getName());
        assertEquals(5, farm.getCapacity());
        assertEquals(0, farm.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenAddingWithNoFreeSpace() {
        Farm farm = new Farm("farm", 1);
        farm.add(new Animal("Chicken", 5));
        farm.add(new Animal("Pig", 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenAddingPresentAnimal() {
        Farm farm = new Farm("farm", 3);
        farm.add(new Animal("Chicken", 5));
        farm.add(new Animal("Chicken", 5));
    }

    @Test
    public void testShouldAddAnimalToFarm() {
        Farm farm = new Farm("farm", 2);
        assertEquals(0, farm.getCount());
        farm.add(new Animal("Chicken", 5));
        assertEquals(1, farm.getCount());
    }

    @Test
    public void testShouldRemoveAnimal() {
        Farm farm = new Farm("farm", 2);
        farm.add(new Animal("Chicken", 5));
        farm.add(new Animal("Pig", 5));
        assertEquals(2, farm.getCount());
        assertTrue(farm.remove("Pig"));
        assertEquals(1, farm.getCount());
    }
}
