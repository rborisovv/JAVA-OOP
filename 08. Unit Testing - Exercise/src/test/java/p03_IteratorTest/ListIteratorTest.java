package p03_IteratorTest;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;

import static org.junit.Assert.assertEquals;

public class ListIteratorTest {
    private String[] elements;
    private ListIterator listIterator;

    @Before
    public void setUp() throws OperationNotSupportedException {
        this.elements = initializeElements();
        this.listIterator = new ListIterator(elements);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowIfInstantiatedWithNull() throws OperationNotSupportedException {
        new ListIterator((String[]) null);
    }

    @Test
    public void testShouldInitializeAndCompareElementsFromListIterator() {
        for (String element : elements) {
            assertEquals(element, this.listIterator.print());
            this.listIterator.move();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testThrowsErrorWhenPrintingWithNoElementsInList() throws OperationNotSupportedException {
        new ListIterator().print();
    }

    private String[] initializeElements() {
        return new String[]{"Java", "C#", "Python", "C++", "Javascript"};
    }
}