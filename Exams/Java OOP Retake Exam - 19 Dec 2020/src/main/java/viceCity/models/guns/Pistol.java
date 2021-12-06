package viceCity.models.guns;

public class Pistol extends BaseGun {
    private static final int PISTOL_BULLETS_PER_BARREL = 10;
    private static final int PISTOL_TOTAL_BULLETS = 100;
    private static final int PISTOL_FIRE_SHOTS_COUNT = 1;

    private int barrelShotsLeft;

    public Pistol(String name) {
        super(name, PISTOL_BULLETS_PER_BARREL, PISTOL_TOTAL_BULLETS);
        this.barrelShotsLeft = PISTOL_BULLETS_PER_BARREL;
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
        if (barrelShotsLeft >= PISTOL_FIRE_SHOTS_COUNT) {
            barrelShotsLeft -= PISTOL_FIRE_SHOTS_COUNT;
            return PISTOL_FIRE_SHOTS_COUNT;
        } else {
            barrelShotsLeft = PISTOL_BULLETS_PER_BARREL;
            this.setTotalBullets(this.getTotalBullets() - this.getBulletsPerBarrel());
            return 0;
        }
    }
}