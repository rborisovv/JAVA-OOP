package blueOrigin;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpaceshipTests {
    @Test(expected = NullPointerException.class)
    public void testThrowsWhenPassingNullNameToCtor() {
        new Spaceship(null, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsWhenPassingCapacityBelowZeroToCtor() {
        new Spaceship("spaceship", -1);
    }

    @Test
    public void testShouldSuccessfullyCreateSpaceship() {
        Spaceship spaceship = new Spaceship("spaceship", 5);
        assertEquals("spaceship", spaceship.getName());
        assertEquals(5, spaceship.getCapacity());
        assertEquals(0, spaceship.getCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testShouldThrowWhenNotEnoughCapacityToAdd() {
        Spaceship spaceship = new Spaceship("ISA", 1);
        spaceship.add(new Astronaut("George", 75));
        spaceship.add(new Astronaut("Peter", 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsWhenAddingTheSameAstronaut() {
        Spaceship spaceship = new Spaceship("ISA", 10);
        spaceship.add(new Astronaut("George", 22));
        spaceship.add(new Astronaut("George", 58));
    }

    @Test
    public void testAddsAstronautToSpaceship() {
        Spaceship spaceship = new Spaceship("ISA", 8);
        assertEquals(0, spaceship.getCount());
        spaceship.add(new Astronaut("Iva", 100));
        assertEquals(1, spaceship.getCount());
    }

    @Test
    public void testRemovesAstronautFromStation() {
        Spaceship spaceship = new Spaceship("ISA", 90);
        spaceship.add(new Astronaut("George", 20));
        spaceship.add(new Astronaut("Eve", 20));
        spaceship.add(new Astronaut("Jessica", 20));
        assertEquals(3, spaceship.getCount());
        assertTrue(spaceship.remove("Eve"));
        assertEquals(2, spaceship.getCount());
    }
}