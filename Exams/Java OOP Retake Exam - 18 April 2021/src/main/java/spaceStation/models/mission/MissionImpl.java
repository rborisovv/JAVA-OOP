package spaceStation.models.mission;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.planets.Planet;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission {
    @Override
    public void explore(Planet planet, Collection<Astronaut> astronauts) {
        List<Astronaut> clonedAstronauts = (List<Astronaut>) astronauts;
        ArrayDeque<String> planetItems = new ArrayDeque<>();
        planet.getItems().forEach(planetItems::offer);
        for (int astronautIndex = 0; astronautIndex < astronauts.size(); astronautIndex++) {
            Astronaut astronaut = clonedAstronauts.get(astronautIndex);
            while (!planetItems.isEmpty()) {
                if (astronaut.canBreath()) {
                    String currentItem = planetItems.poll();
                    astronaut.getBag().getItems().add(currentItem);
                    planet.getItems().remove(currentItem);
                    astronaut.breath();
                } else {
                    break;
                }
            }
        }
    }
}