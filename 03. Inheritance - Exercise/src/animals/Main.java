package animals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        String animalType = reader.readLine();

        while (!animalType.equals("Beast!")) {
            String[] animalInfo = reader.readLine().split(" ");
            String name = animalInfo[0];
            int age = Integer.parseInt(animalInfo[1]);
            String gender = animalInfo[2];
            try {
                switch (animalType) {
                    case "Cat" -> {
                        Cat cat = new Cat(name, age, gender);
                        System.out.println(cat);
                    }
                    case "Frog" -> {
                        Frog frog = new Frog(name, age, gender);
                        System.out.println(frog);
                    }
                    case "Dog" -> {
                        Dog dog = new Dog(name, age, gender);
                        System.out.println(dog);
                    }
                    case "Kitten" -> {
                        Kitten kitten = new Kitten(name, age);
                        System.out.println(kitten);
                    }
                    case "Tomcat" -> {
                        Tomcat tomcat = new Tomcat(name, age);
                        System.out.println(tomcat);
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            animalType = reader.readLine();
        }
    }
}