package CounterStriker.core;

import CounterStriker.common.OutputMessages;
import CounterStriker.models.field.Field;
import CounterStriker.models.field.FieldImpl;
import CounterStriker.models.guns.Gun;
import CounterStriker.models.guns.Pistol;
import CounterStriker.models.guns.Rifle;
import CounterStriker.models.players.CounterTerrorist;
import CounterStriker.models.players.Player;
import CounterStriker.models.players.PlayerImpl;
import CounterStriker.models.players.Terrorist;
import CounterStriker.repositories.GunRepository;
import CounterStriker.repositories.PlayerRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static CounterStriker.common.ExceptionMessages.*;
import static CounterStriker.common.OutputMessages.*;

public class ControllerImpl implements Controller {
    private GunRepository guns;
    private PlayerRepository players;
    private Field field;

    public ControllerImpl() {
        this.guns = new GunRepository();
        this.players = new PlayerRepository();
        this.field = new FieldImpl();
    }

    @Override
    public String addGun(String type, String name, int bulletsCount) {
        Gun gun;
        switch (type) {
            case "Pistol":
                gun = new Pistol(name, bulletsCount);
                break;
            case "Rifle":
                gun = new Rifle(name, bulletsCount);
                break;
            default:
                throw new IllegalArgumentException(INVALID_GUN_TYPE);
        }
        guns.add(gun);
        return String.format(SUCCESSFULLY_ADDED_GUN, name);
    }

    @Override
    public String addPlayer(String type, String username, int health, int armor, String gunName) {
        Player player;
        Gun gun = findGunByName(gunName);
        switch (type) {
            case "Terrorist":
                player = new Terrorist(username, health, armor, gun);
                break;
            case "CounterTerrorist":
                player = new CounterTerrorist(username, health, armor, gun);
                break;
            default:
                throw new IllegalArgumentException(INVALID_PLAYER_TYPE);
        }
        players.add(player);
        return String.format(SUCCESSFULLY_ADDED_PLAYER, username);
    }

    private Gun findGunByName(String gunName) {
        return this.guns.getModels()
                .stream()
                .filter(gun -> gun.getName().equals(gunName))
                .findFirst()
                .orElseThrow(() -> new NullPointerException(GUN_CANNOT_BE_FOUND));
    }

    @Override
    public String startGame() {
        List<Player> alivePlayers = findAllAlivePlayers();
        return field.start(alivePlayers);
    }

    private List<Player> findAllAlivePlayers() {
        return players.getModels()
                .stream().filter(Player::isAlive)
                .collect(Collectors.toList());
    }

    @Override
    public String report() {
        StringBuilder reportInfo = new StringBuilder();
        Comparator<Player> playerComparator = (p1, p2) -> {
            int result = p1.getClass().getSimpleName().compareTo(p2.getClass().getSimpleName());
            if (result == 0) {
                result = Integer.compare(p2.getHealth(), p1.getHealth());
            }
            if (result == 0) {
                result = p1.getUsername().compareTo(p2.getUsername());
            }
            return result;
        };
        players.getModels()
                .stream()
                .sorted(playerComparator)
                .forEach(player -> reportInfo.append(player).append(System.lineSeparator()));
        return reportInfo.toString().trim();
    }
}