package cardsWIthPower;

public record Card(Power cardPower, Rank cardRank) {

    @Override
    public String toString() {
        return String.format("Card name: %s of %s; Card power: %d",
                this.cardRank.name(), this.cardPower.name(),
                this.cardRank.getRank() + cardPower.getPower());
    }
}