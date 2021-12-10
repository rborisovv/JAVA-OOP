package CounterStriker.models.guns;

public class Rifle extends GunImpl {
    private static final int RIFLE_BULLETS_FIRE = 10;

    public Rifle(String name, int bulletsCount) {
        super(name, bulletsCount);
    }

    @Override
    public int fire() {
        if (super.getBulletsCount() < RIFLE_BULLETS_FIRE) {
            return 0;
        }
        super.setBulletsCount(super.getBulletsCount() - 10);
        return 10;
    }
}