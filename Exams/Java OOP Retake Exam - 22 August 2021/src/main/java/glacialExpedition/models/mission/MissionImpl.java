package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.*;

public class MissionImpl implements Mission {
    @Override
    public void explore(State state, Collection<Explorer> explorers) {
        //TODO:
        Iterator<String> iterator = state.getExhibits().iterator();
        while (state.getExhibits().size() > 0) {
            Optional<Explorer> optionalExplorer = explorers.stream().findFirst();
            if (optionalExplorer.isPresent()) {
                Explorer explorer = optionalExplorer.get();
                while (iterator.hasNext()) {
                    if (explorer.canSearch()) {
                        String exhibit = iterator.next();
                        explorer.search();
                        explorer.getSuitcase().getExhibits().add(exhibit);
                        iterator.remove();
                    } else {
                        explorers.remove(explorer);
                    }
                }
            }
        }
    }
}