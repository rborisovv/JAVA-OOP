package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer {
    private static final double INITIAL_ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, INITIAL_ENERGY);
    }

    @Override
    public void search() {
        double remainingEnergy = this.getEnergy() - 7;
        if (remainingEnergy < 0) {
            this.setEnergy(0);
            return;
        }
        this.setEnergy(remainingEnergy);
    }
}