package halfLife;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTests {
    //TODO: TEST ALL THE FUNCTIONALITY OF THE PROVIDED CLASS Player

    @Test(expected = NullPointerException.class)
    public void testThrowsWhenPassingNullNameToCtor() {
        new Player(null, 100);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsWhenPassingEmptyNameToCtor() {
        new Player("   ", 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsWhenPassedHealthBelowZero() {
        new Player("George", -1);
    }

    @Test
    public void testShouldCreateInstancePlayer() {
        Player player = new Player("George", 50);
        assertEquals("George", player.getUsername());
        assertEquals(50, player.getHealth());
        assertEquals(0, player.getGuns().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testThrowsWhenAttackingDeadPlayer() {
        new Player("George", 0).takeDamage(100);
    }

    @Test
    public void testSetsHealthToZeroWhenAttackPointsAreGreaterThanHealth() {
        Player player = new Player("George", 10);
        player.takeDamage(20);
        assertEquals(0, player.getHealth());
    }

    @Test
    public void testReducesHealthWhenAttackingAccordingly() {
        Player player = new Player("George", 40);
        player.takeDamage(30);
        assertEquals(10, player.getHealth());
    }

    @Test(expected = NullPointerException.class)
    public void testThrowsWhenAddingGunWithNullValue() {
        new Player("George", 100).addGun(null);
    }

    @Test
    public void testSuccessfullyAddsGunToPlayer() {
        Player player = new Player("George", 100);
        assertEquals(0, player.getGuns().size());
        player.addGun(new Gun("Eagle", 10));
        assertEquals(1, player.getGuns().size());
    }

    @Test
    public void testRemovesGunFromPlayersGuns() {
        Player player = new Player("George", 100);
        Gun gun = new Gun("Eagle", 10);
        player.addGun(gun);
        assertTrue(player.removeGun(gun));
    }

    @Test
    public void testDoesntRemoveGunFromPlayersGuns() {
        Player player = new Player("George", 100);
        player.addGun(new Gun("Makarov", 30));
        Gun gun = new Gun("Eagle", 10);
        assertFalse(player.removeGun(gun));
    }

    @Test
    public void testReturnsNullWhenGunNotFound() {
        Player player = new Player("George", 100);
        player.addGun(new Gun("Eagle", 10));
        assertNull(player.getGun("Makarov"));
    }

    @Test
    public void testReturnsGunByName() {
        Player player = new Player("George", 100);
        player.addGun(new Gun("Eagle", 10));
        Gun gun = player.getGun("Eagle");
        assertEquals("Eagle", gun.getName());
        assertEquals(10, gun.getBullets());
    }
}