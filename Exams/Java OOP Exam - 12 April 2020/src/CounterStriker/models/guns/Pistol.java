package CounterStriker.models.guns;

public class Pistol extends GunImpl {
    private static final int PISTOL_BULLETS_FIRE = 1;

    public Pistol(String name, int bulletsCount) {
        super(name, bulletsCount);
    }

    @Override
    public int fire() {
        if (this.getBulletsCount() < PISTOL_BULLETS_FIRE) {
            //TODO: find out if i need to reduce bullets count
            return 0;
        }
        super.setBulletsCount(super.getBulletsCount() - 1);
        return 1;
    }
}