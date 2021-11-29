package glacialExpedition.core;

import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.Repository;
import glacialExpedition.repositories.StateRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;
import static glacialExpedition.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {
    private Repository<Explorer> explorerRepository;
    private Repository<State> stateRepository;
    private Mission mission;
    private int exploredStatesCount;

    public ControllerImpl() {
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();
        this.mission = new MissionImpl();
        this.exploredStatesCount = 0;
    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer;
        switch (type) {
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplorer":
                explorer = new NaturalExplorer(explorerName);
                break;
            default:
                throw new IllegalArgumentException(EXPLORER_INVALID_TYPE);
        }
        this.explorerRepository.add(explorer);
        return String.format(EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        state.getExhibits().addAll(Arrays.asList(exhibits));
        this.stateRepository.add(state);
        return String.format(STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer foundExplorer = this.explorerRepository
                .getCollection()
                .stream()
                .filter(explorer -> explorer.getName().equals(explorerName))
                .findFirst().orElse(null);

        if (foundExplorer == null) {
            throw new IllegalArgumentException(String.format(EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        this.explorerRepository.remove(foundExplorer);
        return String.format(EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        State state = this.stateRepository.byName(stateName);
        List<Explorer> explorers = this.explorerRepository
                .getCollection()
                .stream()
                .filter(explorer -> explorer.getEnergy() > 50)
                .collect(Collectors.toList());

        if (explorers.size() == 0) {
            throw new IllegalArgumentException(STATE_EXPLORERS_DOES_NOT_EXISTS);
        }
        int explorersCount = this.explorerRepository.getCollection().size();
        this.mission.explore(state, explorers);
        int explorersAfterExplore = this.explorerRepository.getCollection().size();
        this.exploredStatesCount++;
        return String.format(STATE_EXPLORER, stateName,
                explorersCount - explorersAfterExplore);
    }

    @Override
    public String finalResult() {
        StringBuilder result = new StringBuilder();
        result.append(String.format(FINAL_STATE_EXPLORED, this.exploredStatesCount))
                .append(System.lineSeparator())
                .append(FINAL_EXPLORER_INFO)
                .append(System.lineSeparator());

        this.explorerRepository
                .getCollection()
                .forEach(explorer -> {
                    result.append(String.format(FINAL_EXPLORER_NAME, explorer.getName())).append(System.lineSeparator());
                    result.append(String.format(FINAL_EXPLORER_ENERGY, explorer.getEnergy())).append(System.lineSeparator());
                    String exhibits = explorer.getSuitcase().getExhibits().size() == 0 ? "None" : String.join(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, explorer.getSuitcase().getExhibits());
                    result.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS, exhibits))
                            .append(System.lineSeparator());
                });
        return result.toString().trim();
    }
}