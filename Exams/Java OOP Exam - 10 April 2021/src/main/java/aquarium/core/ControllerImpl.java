package aquarium.core;

import aquarium.common.ConstantMessages;
import aquarium.common.ExceptionMessages;
import aquarium.entities.aquariums.Aquarium;
import aquarium.entities.aquariums.BaseAquarium;
import aquarium.entities.aquariums.FreshwaterAquarium;
import aquarium.entities.aquariums.SaltwaterAquarium;
import aquarium.entities.decorations.Decoration;
import aquarium.entities.decorations.Ornament;
import aquarium.entities.decorations.Plant;
import aquarium.entities.fish.Fish;
import aquarium.entities.fish.FreshwaterFish;
import aquarium.entities.fish.SaltwaterFish;
import aquarium.repositories.DecorationRepository;
import aquarium.repositories.Repository;

import java.util.ArrayList;
import java.util.Collection;

public class ControllerImpl implements Controller {
    private Repository decorations;
    private Collection<Aquarium> aquariums;

    public ControllerImpl() {
        this.decorations = new DecorationRepository();
        this.aquariums = new ArrayList<>();
    }

    @Override
    public String addAquarium(String aquariumType, String aquariumName) {
        Aquarium aquarium;
        switch (aquariumType) {
            case "FreshwaterAquarium":
                aquarium = new FreshwaterAquarium(aquariumName);
                break;
            case "SaltwaterAquarium":
                aquarium = new SaltwaterAquarium(aquariumName);
                break;
            default:
                throw new NullPointerException(ExceptionMessages.INVALID_AQUARIUM_TYPE);
        }
        this.aquariums.add(aquarium);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_AQUARIUM_TYPE, aquariumType);
    }

    @Override
    public String addDecoration(String type) {
        Decoration decoration;
        switch (type) {
            case "Ornament":
                decoration = new Ornament();
                break;
            case "Plant":
                decoration = new Plant();
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_DECORATION_TYPE);
        }
        this.decorations.add(decoration);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_TYPE, type);
    }

    @Override
    public String insertDecoration(String aquariumName, String decorationType) {
        Aquarium foundAquarium = this.aquariums.stream()
                .filter(aquarium -> aquarium.getName().equals(aquariumName))
                .findFirst()
                .orElse(null);
        Decoration foundDecoration = this.decorations.findByType(decorationType);
        if (foundDecoration == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.NO_DECORATION_FOUND, decorationType));
        }
        assert foundAquarium != null;
        foundAquarium.addDecoration(foundDecoration);
        this.decorations.remove(foundDecoration);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_DECORATION_IN_AQUARIUM, decorationType, aquariumName);
    }

    @Override
    public String addFish(String aquariumName, String fishType, String fishName, String fishSpecies, double price) {
        BaseAquarium foundAquarium = (BaseAquarium) this.aquariums.stream()
                .filter(aquarium -> aquarium.getName().equals(aquariumName))
                .findFirst()
                .orElse(null);
        Fish fish;
        switch (fishType) {
            case "FreshwaterFish":
                fish = new FreshwaterFish(fishName, fishSpecies, price);
                break;
            case "SaltwaterFish":
                fish = new SaltwaterFish(fishName, fishSpecies, price);
                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.INVALID_FISH_TYPE);
        }
        assert foundAquarium != null;
        if (foundAquarium.getFish().size() >= foundAquarium.getCapacity()) {
            return ConstantMessages.NOT_ENOUGH_CAPACITY;
        }
        switch (fishType) {
            case "FreshwaterFish":
                if (foundAquarium.getClass().getSimpleName().equals("SaltwaterAquarium")) {
                    return ConstantMessages.WATER_NOT_SUITABLE;
                }
                break;
            case "SaltwaterFish":
                if (foundAquarium.getClass().getSimpleName().equals("FreshwaterAquarium")) {
                    return ConstantMessages.WATER_NOT_SUITABLE;
                }
                break;
        }
        foundAquarium.addFish(fish);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_FISH_IN_AQUARIUM, fishType, aquariumName);
    }

    @Override
    public String feedFish(String aquariumName) {
        Aquarium foundAquarium = this.aquariums.stream()
                .filter(aquarium -> aquarium.getName().equals(aquariumName))
                .findFirst()
                .orElse(null);
        assert foundAquarium != null;
        foundAquarium.feed();
        int fedFish = foundAquarium.getFish().size();
        return String.format(ConstantMessages.FISH_FED, fedFish);
    }

    @Override
    public String calculateValue(String aquariumName) {
        Aquarium foundAquarium = this.aquariums.stream()
                .filter(aquarium -> aquarium.getName().equals(aquariumName))
                .findFirst()
                .orElse(null);
        assert foundAquarium != null;
        double fishPriceSum = foundAquarium.getFish()
                .stream().mapToDouble(Fish::getPrice).sum();
        double decorationPriceSum = foundAquarium.getDecorations().stream()
                .mapToDouble(Decoration::getPrice).sum();
        return String.format(ConstantMessages.VALUE_AQUARIUM, aquariumName, fishPriceSum + decorationPriceSum);
    }

    @Override
    public String report() {
        StringBuilder report = new StringBuilder();
        this.aquariums.forEach(aquarium -> report.append(aquarium.getInfo()).append(System.lineSeparator()));
        return report.toString().trim();
    }
}