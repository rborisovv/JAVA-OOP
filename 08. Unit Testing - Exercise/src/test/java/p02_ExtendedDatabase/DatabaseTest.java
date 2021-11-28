package p02_ExtendedDatabase;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DatabaseTest {
    private int initialPeopleCount;
    private Person[] people;
    private Database database;

    @Before
    public void setUp() throws OperationNotSupportedException {
        this.initialPeopleCount = 10;
        this.people = initializePeople();
        this.database = new Database(this.people);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testThrowsErrorWhenInitializingWithMorePeopleThanAllowed() throws OperationNotSupportedException {
        Person[] people = new Person[17];
        new Database(people);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testThrowsErrorWhenInitializingWithLessPeopleThanAllowed() throws OperationNotSupportedException {
        new Database();
    }

    @Test
    public void testShouldCorrectlyInitializeDatabase() {
        assertEquals(this.people.length, this.database.getElements().length);
        assertArrayEquals(this.people, this.database.getElements());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowWhenAddingNullElementToDB() throws OperationNotSupportedException {
        this.database.add(null);
    }

    @Test
    public void testShouldSuccessfullyAddPerson() throws OperationNotSupportedException {
        int personId = 11;
        String personName = "George";
        this.database.add(new Person(personId, personName));
        assertEquals(this.initialPeopleCount + 1, this.database.getElements().length);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowWhenRemovingWithEmptyDB() throws OperationNotSupportedException {
        new Database().remove();
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowWhenRemovingAndThereAreNoMorePeople() throws OperationNotSupportedException {
        Person person = new Person(1, "Peter");
        Database database = new Database(person);
        database.remove();
        database.remove();
    }

    @Test
    public void testShouldSuccessfullyRemovePerson() throws OperationNotSupportedException {
        assertEquals(this.people.length, this.database.getElements().length);
        this.database.remove();
        assertEquals(this.people.length - 1, this.database.getElements().length);
    }

    @Test
    public void testShouldReturnTheDatabase() {
        assertArrayEquals(this.people, this.database.getElements());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowIfPassedPersonToFindIsNull() throws OperationNotSupportedException {
        this.database.findByUsername(null);
    }

    @Test
    public void testShouldReturnPersonWithGivenUsername() throws OperationNotSupportedException {
        Database database = initializeCustomTestingDB();
        Person foundPerson = database.findByUsername("Jessica");
        assertEquals("Jessica", foundPerson.getUsername());
        assertEquals(3, foundPerson.getId());
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowIfMoreThanOnePersonWithGivenNameExists() throws OperationNotSupportedException {
        Person firstPerson = new Person(1, "Peter");
        Person secondPerson = new Person(2, "Jessica");
        Person thirdPerson = new Person(3, "Jessica");
        Database db = new Database(firstPerson, secondPerson, thirdPerson);
        db.findByUsername("Jessica");
    }

    @Test
    public void testShouldReturnPersonWithGivenId() throws OperationNotSupportedException {
        Database db = initializeCustomTestingDB();
        long id = 3;
        Person personById = db.findById(id);
        assertEquals("Jessica", personById.getUsername());
        assertEquals(id, personById.getId());
    }

    private Database initializeCustomTestingDB() throws OperationNotSupportedException {
        Person firstPerson = new Person(1, "Peter");
        Person secondPerson = new Person(2, "George");
        Person thirdPerson = new Person(3, "Jessica");
        return new Database(firstPerson, secondPerson, thirdPerson);
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testShouldThrowWhenPeopleWithGivenIdArePresent() throws OperationNotSupportedException {
        Person firstPerson = new Person(1, "Peter");
        Person secondPerson = new Person(2, "George");
        Person thirdPerson = new Person(2, "Jessica");
        Database db = new Database(firstPerson, secondPerson, thirdPerson);
        db.findById(2);
    }

    private Person[] initializePeople() {
        Person[] people = new Person[this.initialPeopleCount];
        for (int i = 0; i < people.length; i++) {
            byte[] array = new byte[7];
            new Random().nextBytes(array);
            String generatedString = new String(array, StandardCharsets.UTF_8);
            Person person = new Person(i, generatedString);
            people[i] = person;
        }
        return people;
    }
}