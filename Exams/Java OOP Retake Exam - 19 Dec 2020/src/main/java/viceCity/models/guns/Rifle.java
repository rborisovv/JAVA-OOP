package viceCity.models.guns;

public class Rifle extends BaseGun {
    private static final int RIFLE_BULLETS_PER_BARREL = 50;
    private static final int RIFLE_TOTAL_BULLETS = 500;
    private static final int RIFLE_FIRE_SHOTS_COUNT = 5;

    private int barrelShotsLeft;

    public Rifle(String name) {
        super(name, RIFLE_BULLETS_PER_BARREL, RIFLE_TOTAL_BULLETS);
        this.barrelShotsLeft = RIFLE_FIRE_SHOTS_COUNT;
    }

    public int getBarrelShotsLeft() {
        return barrelShotsLeft;
    }

    @Override
    public boolean canFire() {
        return barrelShotsLeft > 0;
    }

    @Override
    public int fire() {
        if (barrelShotsLeft >= RIFLE_FIRE_SHOTS_COUNT) {
            barrelShotsLeft -= RIFLE_FIRE_SHOTS_COUNT;
            return RIFLE_FIRE_SHOTS_COUNT;
        } else {
            barrelShotsLeft = RIFLE_BULLETS_PER_BARREL;
            this.setTotalBullets(this.getTotalBullets() - this.getBulletsPerBarrel());
            return 0;
        }
    }
}