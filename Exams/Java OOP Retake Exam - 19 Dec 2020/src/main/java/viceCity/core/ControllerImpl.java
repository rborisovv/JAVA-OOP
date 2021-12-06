package viceCity.core;

import viceCity.core.interfaces.Controller;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.ArrayList;
import java.util.Collection;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {
    private MainPlayer mainPlayer;
    private Collection<Player> players;
    private Collection<Gun> guns;

    public ControllerImpl() {
        mainPlayer = new MainPlayer();
        this.players = new ArrayList<>();
        this.guns = new ArrayList<>();
    }

    @Override
    public String addPlayer(String name) {
        players.add(new CivilPlayer(name));
        return String.format(PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        Gun gun;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name);
                break;
            case "Rifle":
                gun = new Rifle(name);
                break;
            default:
                return GUN_TYPE_INVALID;
        }
        guns.add(gun);
        return String.format(GUN_ADDED, name, type);
    }

    @Override
    public String addGunToPlayer(String name) {
        Gun gun = this.guns.stream().findFirst().orElse(null);
        if (gun == null) {
            return GUN_QUEUE_IS_EMPTY;
        }
        if ("Vercetti".equals(name)) {
            mainPlayer.getGunRepository().add(gun);
            guns.remove(gun);
            return String.format(GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), "Tommy Vercetti");
        }
        Player civilPlayer = players.stream().filter(player -> player.getName().equals(name))
                .findFirst().orElse(null);
        if (civilPlayer == null) {
            return CIVIL_PLAYER_DOES_NOT_EXIST;
        }
        civilPlayer.getGunRepository().add(gun);
        guns.remove(gun);
        return String.format(GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), civilPlayer.getName());
    }

    @Override
    public String fight() {
        GangNeighbourhood gangNeighbourhood = new GangNeighbourhood();
        gangNeighbourhood.action(mainPlayer, players);
        boolean mainPlayerIsAlive = mainPlayer.isAlive();
        long numberOfPlayersDied = players.stream().filter(player -> player.getLifePoints() == 0).count();
        if (mainPlayerIsAlive && numberOfPlayersDied == 0) {
            return FIGHT_HOT_HAPPENED;
        }
        String result = FIGHT_HAPPENED + System.lineSeparator() +
                String.format(MAIN_PLAYER_LIVE_POINTS_MESSAGE, mainPlayer.getLifePoints()) + System.lineSeparator() +
                String.format(MAIN_PLAYER_KILLED_CIVIL_PLAYERS_MESSAGE, numberOfPlayersDied) + System.lineSeparator() +
                String.format(CIVIL_PLAYERS_LEFT_MESSAGE, players.size());
        return result.trim();
    }
}