package definePerson;

public class Robot implements Identifiable {
    private String id;
    private String model;

    public Robot(String id, String mode) {
        this.id = id;
        this.model = mode;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }
}