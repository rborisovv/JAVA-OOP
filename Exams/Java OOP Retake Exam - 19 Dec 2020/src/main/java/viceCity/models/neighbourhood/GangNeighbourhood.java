package viceCity.models.neighbourhood;

import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;

public class GangNeighbourhood implements Neighbourhood {
    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        if (!mainPlayer.getGunRepository().getModels().isEmpty()) {
            mainPlayerShoots(mainPlayer, civilPlayers);
        }
        civilPlayersShoot(mainPlayer, civilPlayers);
    }

    private void civilPlayersShoot(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Player civilPlayer : civilPlayers) {
            Gun gun = civilPlayer.getGunRepository().getModels().stream().findFirst().orElse(null);
            while (mainPlayer.isAlive() && !civilPlayer.getGunRepository().getModels().isEmpty()) {
                if (gun != null) {
                    mainPlayer.takeLifePoints(gun.fire());
                    if (gun.canFire()) {
                        civilPlayer.getGunRepository().getModels().remove(gun);
                        gun = civilPlayer.getGunRepository().getModels().stream().findFirst().orElse(null);
                    }
                }
            }
        }
    }

    private void mainPlayerShoots(Player mainPlayer, Collection<Player> civilPlayers) {
        for (Player civilPlayer : civilPlayers) {
            Gun gun = mainPlayer.getGunRepository().getModels().stream().findFirst().orElse(null);
            while (civilPlayer.isAlive() && !mainPlayer.getGunRepository().getModels().isEmpty()) {
                if (gun != null) {
                    civilPlayer.takeLifePoints(gun.fire());
                    if (!gun.canFire()) {
                        mainPlayer.getGunRepository().getModels().remove(gun);
                        gun = mainPlayer.getGunRepository().getModels().stream().findFirst().orElse(null);
                    }
                }
            }
        }
    }
}