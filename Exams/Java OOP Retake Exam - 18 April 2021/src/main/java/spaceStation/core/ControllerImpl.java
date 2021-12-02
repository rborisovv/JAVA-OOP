package spaceStation.core;

import spaceStation.models.astronauts.Astronaut;
import spaceStation.models.astronauts.Biologist;
import spaceStation.models.astronauts.Geodesist;
import spaceStation.models.astronauts.Meteorologist;
import spaceStation.models.mission.Mission;
import spaceStation.models.mission.MissionImpl;
import spaceStation.models.planets.Planet;
import spaceStation.models.planets.PlanetImpl;
import spaceStation.repositories.AstronautRepository;
import spaceStation.repositories.PlanetRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static spaceStation.common.ConstantMessages.*;
import static spaceStation.common.ExceptionMessages.*;
import static spaceStation.common.ExceptionMessages.ASTRONAUT_DOES_NOT_EXIST;
import static spaceStation.common.ExceptionMessages.ASTRONAUT_INVALID_TYPE;

public class ControllerImpl implements Controller {
    private AstronautRepository astronautRepository;
    private PlanetRepository planetRepository;
    private int exploredPlanetsCount;

    public ControllerImpl() {
        this.astronautRepository = new AstronautRepository();
        this.planetRepository = new PlanetRepository();
        this.exploredPlanetsCount = 0;
    }

    @Override
    public String addAstronaut(String type, String astronautName) {
        Astronaut astronaut;
        switch (type) {
            case "Biologist":
                astronaut = new Biologist(astronautName);
                break;
            case "Geodesist":
                astronaut = new Geodesist(astronautName);
                break;
            case "Meteorologist":
                astronaut = new Meteorologist(astronautName);
                break;
            default:
                throw new IllegalArgumentException(ASTRONAUT_INVALID_TYPE);
        }
        this.astronautRepository.add(astronaut);
        return String.format(ASTRONAUT_ADDED, type, astronautName);
    }

    @Override
    public String addPlanet(String planetName, String... items) {
        Planet planet = new PlanetImpl(planetName);
        planet.getItems().addAll(Arrays.asList(items));
        this.planetRepository.add(planet);
        return String.format(PLANET_ADDED, planetName);
    }

    @Override
    public String retireAstronaut(String astronautName) {
        Astronaut foundAstronaut = this.astronautRepository.findByName(astronautName);
        if (foundAstronaut == null) {
            throw new IllegalArgumentException(String.format(ASTRONAUT_DOES_NOT_EXIST, astronautName));
        }
        this.astronautRepository.remove(foundAstronaut);
        return String.format(ASTRONAUT_RETIRED, astronautName);
    }

    @Override
    public String explorePlanet(String planetName) {
        List<Astronaut> bestAstronauts = this.astronautRepository.getModels()
                .stream()
                .filter(astronaut -> astronaut.getOxygen() > 60)
                .collect(Collectors.toList());
        Planet planet = this.planetRepository.findByName(planetName);
        if (bestAstronauts.size() == 0) {
            throw new IllegalArgumentException(PLANET_ASTRONAUTS_DOES_NOT_EXISTS);
        }
        Mission mission = new MissionImpl();
        mission.explore(planet, bestAstronauts);
        this.exploredPlanetsCount++;
        long deadAstronauts = bestAstronauts.stream().filter(astronaut -> astronaut.getOxygen() == 0).count();
        return String.format(PLANET_EXPLORED, planetName, deadAstronauts);
    }

    @Override
    public String report() {
        StringBuilder reportInfo = new StringBuilder();
        reportInfo.append(String.format(REPORT_PLANET_EXPLORED, this.exploredPlanetsCount))
                .append(System.lineSeparator())
                .append(REPORT_ASTRONAUT_INFO)
                .append(System.lineSeparator());

        this.astronautRepository.getModels()
                .forEach(astronaut -> {
                    Collection<String> items = astronaut.getBag().getItems();
                    reportInfo.append(String.format(REPORT_ASTRONAUT_NAME, astronaut.getName()))
                            .append(System.lineSeparator())
                            .append(String.format(REPORT_ASTRONAUT_OXYGEN, astronaut.getOxygen()))
                            .append(System.lineSeparator())
                            .append(items.size() > 0 ? String.format(REPORT_ASTRONAUT_BAG_ITEMS,
                                    String.join(REPORT_ASTRONAUT_BAG_ITEMS_DELIMITER, items)) :
                                    String.format(REPORT_ASTRONAUT_BAG_ITEMS, "none"))
                            .append(System.lineSeparator());
                });
        return reportInfo.toString().trim();
    }
}