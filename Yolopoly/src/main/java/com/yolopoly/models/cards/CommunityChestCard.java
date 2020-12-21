package com.yolopoly.models.cards;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityChestCard extends DrawableCard {
    @JsonCreator
    public CommunityChestCard(@JsonProperty("message")String message, @JsonProperty("isComposed")boolean isComposed, @JsonProperty("isMoving")boolean isMoving, @JsonProperty("moveToIndex")int moveToIndex,
                              @JsonProperty("moveInCounts")int moveInCounts, @JsonProperty("isGettingMoney")boolean isGettingMoney, @JsonProperty("moneyGet")int moneyGet, @JsonProperty("moneyOwe")int moneyOwe,
                              @JsonProperty("isRelatedToBuildings")boolean isRelatedToBuildings, @JsonProperty("moneyForHouses")int moneyForHouses, @JsonProperty("moneyForHotels")int moneyForHotels,
                              @JsonProperty("isGOOJC")boolean isGOOJC, @JsonProperty("isGTJC")boolean isGTJC, @JsonProperty("isEachPlayerIncluded")boolean isEachPlayerIncluded, @JsonProperty("isDrawingChanceCard")boolean isDrawingChanceCard) {
        super(message, isComposed, isMoving, moveToIndex, moveInCounts, isGettingMoney, moneyGet, moneyOwe, isRelatedToBuildings, moneyForHouses, moneyForHotels, isGOOJC, isGTJC, isEachPlayerIncluded, isDrawingChanceCard);
    }

}
