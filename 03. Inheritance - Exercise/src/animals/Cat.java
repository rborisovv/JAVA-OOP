package animals;

public class Cat extends Animal {
    public Cat(String name, int age, String gender) {
        super(name, age, gender);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setAge(int age) {
        super.setAge(age);
    }

    @Override
    public void setGender(String gender) {
        super.setGender(gender);
    }

    @Override
    public String produceSound() {
        return "Meow meow";
    }
}