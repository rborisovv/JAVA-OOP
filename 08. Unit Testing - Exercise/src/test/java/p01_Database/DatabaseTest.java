package p01_Database;

import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {

    @Test
    public void testShouldProperlyInitializeDatabase() throws OperationNotSupportedException {
        int length = 7;
        Integer[] nums = initializeElements(length);
        Database db = new Database(nums);
        assertEquals(nums.length, db.getElements().length);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testThrowsExceptionWhenInstantiatingMoreElementsThanAllowed() throws OperationNotSupportedException {
        Integer[] nums = new Integer[17];
        new Database(nums);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testThrowsExceptionWhenInstantiatingLessElementsThanAllowed() throws OperationNotSupportedException {
        Integer[] nums = new Integer[0];
        new Database(nums);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testThrowsExceptionWhenPassingNullElementToAdd() throws OperationNotSupportedException {
        Integer[] elements = initializeElements(10);
        Database db = new Database(elements);
        db.add(null);
    }

    @Test
    public void testShouldAddElementToTheArray() throws OperationNotSupportedException {
        int initialLength = 15;
        Integer[] elements = initializeElements(initialLength);
        Database db = new Database(elements);
        int element = 50;

        assertEquals(initialLength, db.getElements().length);
        db.add(element);
        assertEquals(initialLength + 1, db.getElements().length);
    }

    @Test
    public void testShouldRemoveElementFromTheArray() throws OperationNotSupportedException {
        int initialLength = 10;
        Integer[] elements = new Integer[initialLength];

        Database db = new Database(elements);
        assertEquals(initialLength, db.getElements().length);
        db.remove();
        assertEquals(initialLength - 1, db.getElements().length);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testThrowsExceptionWhenRemovingOnEmptyDB() throws OperationNotSupportedException {
        Database db = new Database();
        db.remove();
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowExceptionWhenRemovingNoElements() throws OperationNotSupportedException {
        Integer[] elements = initializeElements(1);
        Database db = new Database(elements);
        db.remove();
        db.remove();
    }

    @Test
    public void testShouldReturnDatabaseElements() throws OperationNotSupportedException {
        int length = 16;
        Integer[] elements = initializeElements(length);
        Database db = new Database(elements);
        assertEquals(elements.length, db.getElements().length);
        assertArrayEquals(elements, db.getElements());
    }

    private static Integer[] initializeElements(int length) {
        Integer[] elements = new Integer[length];
        int origin = 1;
        int bound = 50;
        for (int i = 0; i < length; i++) {
            int element = ThreadLocalRandom.current().nextInt(origin, bound);
            elements[i] = element;
        }
        return elements;
    }
}