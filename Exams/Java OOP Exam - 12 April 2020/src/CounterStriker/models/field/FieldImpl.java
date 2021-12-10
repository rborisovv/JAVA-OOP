package CounterStriker.models.field;

import CounterStriker.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static CounterStriker.common.OutputMessages.*;

public class FieldImpl implements Field {
    @Override
    public String start(Collection<Player> players) {
        List<Player> terrorists = findPlayerByType(players, "Terrorist");
        List<Player> counterTerrorists = findPlayerByType(players, "CounterTerrorist");

        while (findAlivePlayers(terrorists) > 0 && findAlivePlayers(counterTerrorists) > 0) {
            for (Player terrorist : terrorists) {
                for (Player counterTerrorist : counterTerrorists) {
                    if (counterTerrorist.isAlive()) {
                        counterTerrorist.takeDamage(terrorist.getGun().fire());
                    }
                }
            }
            counterTerrorists = removeDeadPlayers(counterTerrorists);
            for (Player counterTerrorist : counterTerrorists) {
                for (Player terrorist : terrorists) {
                    if (terrorist.isAlive()) {
                        terrorist.takeDamage(counterTerrorist.getGun().fire());
                    }
                }
            }
            terrorists = removeDeadPlayers(terrorists);
        }
        if (findAlivePlayers(terrorists) > 0) {
            return TERRORIST_WINS;
        }
        return COUNTER_TERRORIST_WINS;
    }

    private List<Player> removeDeadPlayers(List<Player> players) {
        return players.stream().filter(Player::isAlive).collect(Collectors.toList());
    }

    private int findAlivePlayers(List<Player> players) {
        return (int) players.stream()
                .filter(player -> player.getHealth() > 0)
                .count();
    }

    private List<Player> findPlayerByType(Collection<Player> players, String type) {
        return players.stream()
                .filter(player -> player.getClass().getSimpleName().equals(type))
                .collect(Collectors.toList());
    }
}