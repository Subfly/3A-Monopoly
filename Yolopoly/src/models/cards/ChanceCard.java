package models.cards;

public class ChanceCard extends DrawableCard {
    public ChanceCard(String message, boolean isComposed, boolean isMoving, int moveToIndex, int moveInCounts, boolean isGettingMoney, int moneyGet, int moneyOwe, boolean isRelatedToBuildings, int moneyForHouses, int moneyForHotels, boolean isGOOJC, boolean isGTJC, boolean isEachPlayerIncluded, boolean isDrawingChanceCard) {
        super(message, isComposed, isMoving, moveToIndex, moveInCounts, isGettingMoney, moneyGet, moneyOwe, isRelatedToBuildings, moneyForHouses, moneyForHotels, isGOOJC, isGTJC, isEachPlayerIncluded, isDrawingChanceCard);
    }
}
