package models.engines;

import enumerations.*;
import models.*;
import models.cards.ChanceCard;
import models.cards.PlaceCard;
import models.cards.PropertyCard;
import storage.Constants;
import storage.StorageUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InnerEngine {

    // Constants
    private final static int JAIL_TURN_COUNT = 3;

    //************
    // Variables
    //************
    private ArrayList<String> chat;
    private ArrayList<String> log;
    private ArrayList<Player> players;
    private Dice dice;
    private Board board;
    private Bank bank;
    private int currentPlayerId;
    private GameState state;
    private GameMode gameMode;

    //Auction Related
    private int currentBid;
    private int auctionPropertyIndex;
    private int currentPlayerAuctioning;
    private ArrayList<Player> participants;

    //Broken player related
    /**
     * IMPORTANT!!!!!
     * THIS HASH IS THE CORE OF BANKRUPTCY
     * HENCE BE AWARE USING IT
     * <====VALUES====>
     *      FROM 0 TO PLAYERS.SIZE-1 ===> MONEY PAYING FROM A PARTICULAR PLAYER TO OTHER PLAYERS
     *      PLAYER.SIZE ===> MONEY PAYING TO BANK
     *      PLAYER.SIZE + 1 ===> MONEY PAYING AS TAX
     * <====STRUCTURE====>
     *     PAYING PLAYER ID / WHERE TO PAY / PAYMENT AMOUNT
     */
    private HashMap<Integer, HashMap<Integer, Integer>> brokenPlayersMoneyHash;

    //************
    // Constructor
    //************
    public InnerEngine(){
    }

    public void initializeGame(boolean isSavedGamePlaying, GameMode mode, GameTheme theme, ArrayList<Player> players) {
        if(isSavedGamePlaying){
            //TODO: IMPLEMENT SAVED GAME
        }else{
            chat = new ArrayList<>();
            log = new ArrayList<>();
            this.players = players;
            bank = new Bank(theme, mode);
            dice = new Dice();
            board = new Board(mode, theme);
            currentPlayerId = 0;
            state = GameState.Linear;
            currentBid = 0;
            auctionPropertyIndex = -1;
            currentPlayerAuctioning = -1;
            brokenPlayersMoneyHash = new HashMap<>();
            gameMode = mode;

            for (Player p : players){
                p.setCurrentPosition(0);
                p.setStartMoney(Constants.START_MONEY);
            }
        }
    }

    //************
    // Functions
    //************
    public PropertyCard getSpecificProperty( int squareIndex ){
        for(PropertyCard p: bank.getPropertyCards()){
            if(p.getId() == squareIndex){
                return p;
            }
        }
        return null;
    }

    public Player getOwner(int squareIndex){
        var prop = getSpecificProperty(squareIndex);
        if(prop.getOwnedBy() != -1){
            return players.get(prop.getOwnedBy());
        }
        return null;
    }

    public int saveAndExit(){
        StorageUtil util = new StorageUtil();
        try{
            return util.saveGame(this) ? 1 : 0;
        }catch (IOException e){
            System.out.println("ERROR (3001) SAVE FAILED");
        }
        return 0;
    }

    public ArrayList<Integer> getSettings(){
        StorageUtil util = new StorageUtil();
        try{
            return util.getSettings();
        } catch (IOException e){
            System.out.println("ERROR (1001): INVALID FILE");
        }
        return null;
    }

    public void setSettings(int audio, int music){
        StorageUtil util = new StorageUtil();
        try{
            util.setSettings(audio, music);
        }catch (IOException e){
            System.out.println("ERROR (1002): INVALID WRITE TO FILE");
        }
    }

    public boolean changePlayerToBot(int index){
        var player = players.get(index);
        player.setHuman(false);
        players.set(index, player);
        return true;
    }

    public void addToChat(String data, String userName){
        chat.add(userName + ":\n" + data);
    }

    public void addToLog(String logAction, String userName){
        log.add("Player " + userName + " has " + logAction);
    }

    //************
    // Bot Handlers
    //************
    public boolean isCurrentPlayerHuman(){
        return this.players.get(currentPlayerId).isHuman();
    }

    public int makeDecision(int diceResult, boolean isDouble){
        double multiplier = 1;
        if (this.gameMode ==  GameMode.bankman) {
            int decision = (int)(Math.random() * 2 + 1);
            if(decision == 1){
                multiplier = this.generateChanceMultiplier(diceResult);
            }
        }
        int result = startTurn(diceResult, isDouble, multiplier);
        Player bot = players.get(currentPlayerId);
        if(result == -99){
            return payDebtBot(bot);
        }else if(result == 1){
            /*
             * RETURN VALUES EXPLAINED
             * -99 => PLAYER BROKE
             * 1 => DRAWN GOOJC
             * 2 => DRAWN GTJC
             * 3 => CHANGED POSITION FORWARD
             * 4 => CHANGED POSITION TO INDEX
             * 5 => PAY MONEY FOR BUILDINGS
             * 6 => PAY TO BANK
             * 7 => BIRTHDAY GIFT BABY!
             * UNKNOWN VALUE > 100000 => EITHER PAY MONEY OR DRAW CHANCE CARD
             */
            int cardRes = drawCard(DrawableCardType.Chance);
            if (cardRes == -99){
                return payDebtBot(bot);
            }else if (cardRes == 3){
                //TODO: SAİT BURAYI HANDLE ET POSITION FARKI İLE
                /*
                 * HANDLE ALGORITHM===>
                 * SAVE OLD POSITION
                 * MOVE PAWN ACCORDING TO TOTAL COUNT
                 * HERE I ENCODE MOVE ***COUNT*** AS SUPPOSED TO BE
                 * SO MOVE AGAIN WITH RETURN VALUE
                 * CHECK WITH if(result < 0 && result > -90)
                 */
                return cardRes;
            }else if(cardRes == 4){
                //TODO: SAİT BURAYI HANDLE ET POSITION FARKI İLE
                /*
                 * HANDLE ALGORITHM===>
                 * SAVE OLD POSITION
                 * MOVE PAWN ACCORDING TO TOTAL COUNT
                 * HERE I ENCODE MOVE ***INDEX*** AS SUPPOSED TO BE
                 * SO MOVE AGAIN WITH RETURN VALUE
                 * CHECK WITH if(result >= 0 && result <= 39)
                 */
                return cardRes;
            }
        }else if(result == 2){
            int cardRes = drawCard(DrawableCardType.Community);
            if (cardRes == -99){
                return payDebtBot(bot);
            }else if (cardRes == 3){
                //TODO: SAİT BURAYI HANDLE ET POSITION FARKI İLE
                /*
                 * HANDLE ALGORITHM===>
                 * SAVE OLD POSITION
                 * MOVE PAWN ACCORDING TO TOTAL COUNT
                 * HERE I ENCODE MOVE ***COUNT*** AS SUPPOSED TO BE
                 * SO MOVE AGAIN WITH RETURN VALUE
                 * CHECK WITH if(result < 0 && result > -90)
                 */
                return cardRes;
            }else if(cardRes == 4){
                //TODO: SAİT BURAYI HANDLE ET POSITION FARKI İLE
                /*
                 * HANDLE ALGORITHM===>
                 * SAVE OLD POSITION
                 * MOVE PAWN ACCORDING TO TOTAL COUNT
                 * HERE I ENCODE MOVE ***INDEX*** AS SUPPOSED TO BE
                 * SO MOVE AGAIN WITH RETURN VALUE
                 * CHECK WITH if(result >= 0 && result <= 39)
                 */
                return cardRes;
            }else if (cardRes >= 100000){
                int decision = (int) (Math.random() * 2 + 1);
                if(decision == 1){
                    if(bot.removeMoney(Constants.CURRENCY_NAMES[0], cardRes)){
                        return -97;
                    }else{
                        //YİNE Mİ BATTIN BE HACI YETER YA
                        return payDebtBot(bot);
                    }
                }else{
                    int cardResAgain = drawCard(DrawableCardType.Chance);
                    if (cardResAgain == -99){
                        return payDebtBot(bot);
                    }else if (cardResAgain == 3){
                        //TODO: SAİT BURAYI HANDLE ET POSITION FARKI İLE
                        /*
                         * Same as above
                         */
                        return cardResAgain;
                    }else if(cardResAgain == 4){
                        //TODO: SAİT BURAYI HANDLE ET POSITION FARKI İLE
                        /*
                         * Same as above
                         */
                        return cardResAgain;
                    }
                }
            }
        }else if(result == 7){
            if(checkBuyProperty(bot.getCurrentPosition()) && board.getSquares().get(bot.getCurrentPosition()).getType() == SquareType.NormalSquare){
                //Just buy the area
                buyProperty();
            }
            int totalBoughtProperties = bot.getOwnedPlaces().size();
            int randomArea = (int)(Math.random() * totalBoughtProperties + 1);
            if(checkBuildBuilding(Building.House, board.getSpecificSquare(randomArea)).containsKey(true)){
                //Build house
                buildBuilding(Building.House, randomArea);
            }
            if(checkBuildBuilding(Building.Hotel, board.getSpecificSquare(randomArea)).containsKey(true)){
                //Build hotel
                buildBuilding(Building.Hotel, randomArea);
            }
            return -97;
        }
        return -100;
    }

    //************
    // Bankruptcy Handlers
    //************
    public boolean payDebt(){
        Player player = players.get(currentPlayerId);
        var debtData = brokenPlayersMoneyHash.get(currentPlayerId);
        int moneyPayIndex = debtData.entrySet().iterator().next().getKey();
        int debt = debtData.get(moneyPayIndex);
        if(moneyPayIndex < players.size()){
            //Paying money to other player
            player.removeMoney(Constants.CURRENCY_NAMES[0], debt);
            Player otherPlayer = players.get(moneyPayIndex);
            otherPlayer.addMoney(Constants.CURRENCY_NAMES[0], debt);
            player.setBankrupt(false);
            return true;
        }else if(moneyPayIndex == players.size()){
            //Paying to bank, just pay...
            player.removeMoney(Constants.CURRENCY_NAMES[0], debt);
            player.setBankrupt(false);
            return true;
        }else if(moneyPayIndex == players.size() + 1){
            //Paying to free park space :D
            player.removeMoney(Constants.CURRENCY_NAMES[0], debt);
            board.addToTaxMoney(debt);
            player.setBankrupt(false);
            return true;
        }
        return false;
    }

    public int payDebtBot(Player bot){
        var debtData = brokenPlayersMoneyHash.get(currentPlayerId);
        int moneyPayIndex = debtData.entrySet().iterator().next().getKey();
        int debt = debtData.get(moneyPayIndex);
        //Look in properties
        for(PropertyCard p : bot.getOwnedPlaces()){
            Square s = board.getSpecificSquare(p.getId());
            //Select the one with less valuable
            if(s.getLevel() == 0){
                //Mortgage that place
                levelDown(s.getId());
                //If has enough money
                if(bot.getMonopolyMoneyAmount() >= debt){
                    payDebt();
                    return -98;
                }
            }
        }
        //Look in properties but now start to sell hotels and houses
        for(PropertyCard p : bot.getOwnedPlaces()){
            Square s = board.getSpecificSquare(p.getId());
            //Select the first one that has hotels or houses
            while(s.getLevel() > 0){
                //Mortgage that place
                levelDown(s.getId());
                //If has enough money
                if(bot.getMonopolyMoneyAmount() >= debt){
                    payDebt();
                    return -98;
                }
            }
        }
        //BROKE HACI GEÇMİŞ OLSUN
        return -99;
    }

    //************
    // Private Functions
    //************
    private int getBuyer(int squareIndex){
        for(PropertyCard p: bank.getPropertyCards()){
            if(squareIndex == p.getId()){
                return p.getOwnedBy();
            }
        }
        return -1;
    }

    private int countPlayersColor(Square square){
        return (int) players.get(currentPlayerId).getOwnedPlaces().stream().filter(s-> s.getColor() == square.getColor()).count();
    }

    //************
    // Turn Related Functions
    //************
    public void rollDice(){
        dice.roll();
    }

    /*
     * RETURN VALUES EXPLAINED
     * -99 PLAYER BROKE, PAY DEBTS
     * -2 => PLAYER DOUBLED THREE TIMES
     * -1 => ERROR APPEARED SUCCESSFULLY
     * 1 => DRAW CHANCE
     * 2 => DRAW COMMUNITY
     * 3 => PAID TAX
     * 4 => GO TO JAIL
     * 5 => GET TAXES FROM PARK
     * 6 => PAID RENT
     * 7 => EVERYTHING IS DONE, GOODBYE!
     */
    public int startTurn(int diceResult, boolean hasRolledDouble, double multiplier){
        /*
        Basic structure of a turn is consists of:
            Rolling the dice
            Moving the Pawn where the dice show
                If Draw Card Square -> Draw according Card
                If Tax Square -> Pay Tax, the money will remain on the board
                If Go To Jail Square -> Go to Jail
                If Free Park -> Get the money that accumulated through paying taxes
                If Property Square
                    If not bought -> Buy or Pass
                    If bought -> Pay Rent
             After Movement, Player is released for actions but these calculations needs to be done:
                If player doubled -> double count incremented
                If player passed GO! Square without the help of a card, pay 2000000
                Return true as turn completed
         */

        //Start with getting player
        Player player = players.get(currentPlayerId);

        if(hasRolledDouble){
            player.incrementDoublesCount();
        }

        if(player.isThreeTimesDoubled()){
            player.setInJail(true);
            player.setCurrentPosition(10);
            player.resetDoublesCount();
            return -2;
        }

        //Moving the Pawn where the dice show
        int oldPosition = player.getCurrentPosition();
        player.setCurrentPosition(oldPosition + diceResult);

        if(oldPosition > player.getCurrentPosition()){
            //Passed GO! Square
            player.addMoney(Constants.CURRENCY_NAMES[0], Constants.GO_SQUARE_MONEY);
        }

        //Get the square where pawn landed
        Square square = board.getSpecificSquare(player.getCurrentPosition());

        //Start If's
        //If chance square
        if(player.getCurrentPosition() == square.getId()){
            if(square.getType() == SquareType.ChanceSquare){
                return 1;
            }
            //If Community Chest Square
            else if(square.getType() == SquareType.CommunityChestSquare){
                return 2;
            }
            //If Tax Square
            else if(square.getType() == SquareType.TaxSquare){
                // Get the tax amount
                int taxAmount = square.getCost();
                if(player.removeMoney(Constants.CURRENCY_NAMES[0], taxAmount)){
                    addToLog("paid tax of " + taxAmount, player.getName());
                    return 3;
                }else{
                    player.setBankrupt(true);
                    HashMap<Integer, Integer> payHash = new HashMap<>();
                    payHash.put(players.size()+1, taxAmount);
                    brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                    return -99;
                }
            }
            //If Go to Jail Square
            else if(square.getType() == SquareType.GoToJailSquare){
                player.setCurrentPosition(10); //Move to jail hardcode
                player.setInJail(true);
                //player.removeMoney(2000000, new Currency("tl", 1.0)); //Remove the money as player passes from GO! square while going to jail.
                addToLog("sent to the jail", player.getName());
                return 4;
            }
            //If Free Parking Square, do nothing...
            else if(square.getType() == SquareType.FreeParkingSquare){
                int taxAmountOnBoard = board.getMoneyOnBoard();
                if (taxAmountOnBoard != 0) {
                    player.addMoney(Constants.CURRENCY_NAMES[0], taxAmountOnBoard);
                    board.removeFromTaxMoney();
                    //players.set(currentPlayerId, player);
                    addToLog("got the money on the board with the amount of " + taxAmountOnBoard + " Monopoly Dollars", player.getName());
                }
                return 5;
            }else{
                //If pawn of the player landed on a property square :D Hardest part coming...
                if(square.isBought()){
                    //Find the player who bought that square
                    int buyerIdOfProperty = getBuyer(square.getId());

                    //Create a dummy player holder to change players data in the end
                    Player paidToPlayer = players.get(buyerIdOfProperty);

                    //Find rent amount
                    PropertyCard prop = getSpecificProperty(square.getId());
                    assert prop != null;
                    int rentAmount = prop.getRentPrices().get(square.getRentMultiplier());

                    //Remove money from current player
                    boolean isAbleToPay = player.removeMoney(Constants.CURRENCY_NAMES[0], rentAmount);
                    if(!isAbleToPay){
                        player.setBankrupt(true);
                        HashMap<Integer, Integer> payHash = new HashMap<>();
                        payHash.put(buyerIdOfProperty, rentAmount);
                        brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                        return -99;
                    }
                    //Add money to other player
                    paidToPlayer.addMoney(Constants.CURRENCY_NAMES[0], rentAmount);

                    addToLog("paid " + String.valueOf(rentAmount) + " as rent", player.getName());
                    addToLog("received " + String.valueOf(rentAmount) + " as rent income", paidToPlayer.getName());
                    return 6;
                }else{
                    //Not bought, this part left to frontend
                    //players.set(currentPlayerId, player);
                    return 7;
                }
            }
        }else{
            //Error occur!
            return -1;
        }
    }

    /*
     * RETURN VALUES
     * 1 => NORMAL END
     * 2 => DID NOT PAID LOANS
     */
    public int endTurn(){
        Player player = players.get(currentPlayerId);
        if(player.isBankrupt()){
            //Remove player
            players.remove(currentPlayerId);
            //Return properties
            for(PropertyCard p : player.getOwnedPlaces()){
                p.setOwnedBy(-1);
                Square s = board.getSpecificSquare(p.getId());
                //Return houses and hotels
                if(s.getHotelCount() > 0 || s.getHouseCount() > 0){
                    s.setHotelCount(0);
                    s.setHouseCount(0);
                    s.setLevel(0);
                }
            }
            //Return GOOJC
            if(player.getSavedCards().size() != 0){
                var c = player.removeFromSavedCards();
                board.returnSavedCard(c);
            }
            currentPlayerId--;
            //Clear debts :D
            brokenPlayersMoneyHash.clear();
        }
        if (gameMode.equals(GameMode.bankman)) {
            // check loan for bankman mod
            player.decrementLoanTurn();
            if (checkHasToPayLoanBack()) {
                player.setDiscardedFromGame(true);
                return 2;
            }
        }
        this.currentPlayerId += 1;
        if(this.currentPlayerId > players.size() - 1){
            this.currentPlayerId = 0;
        }
        if (this.gameMode == GameMode.bankman){
            changeCurrenciesOfBank();
        }
        return 1;
    }

    //************
    // Action Related Functions
    //************
    public void buyProperty(){
        //Get changing data
        Player currentPlayer = players.get(currentPlayerId);
        Square square = board.getSpecificSquare(currentPlayer.getCurrentPosition());
        PropertyCard card = getSpecificProperty(square.getId());

        //Make changes on data
        card.setOwnedBy(currentPlayerId);
        currentPlayer.ownProperty(getSpecificProperty(square.getId()));

        //Save changes on data
        //players.set(currentPlayerId, currentPlayer);
        //TODO ponçik ali taha olur böyle şeyler
        bank.getPropertyCards().set(bank.getPropertyCards().indexOf(card), card);
        board.buySquare(square.getId());

        addToLog("bought property named : " + card.getName(), currentPlayer.getName());
    }

    public void mortgagePlace(int squareIndex) {
        Player player = players.get(getCurrentPlayerId());
        player.getSpecificCard(squareIndex).setMortgaged(true);
        board.getSquares().get(squareIndex).setLevel(-1);
        PlaceCard currentPlace = (PlaceCard) player.getSpecificCard(squareIndex);
        int moneyToAdd = currentPlace.getMortgagePrice();
        player.addMoney(Constants.CURRENCY_NAMES[0], moneyToAdd);

    }
    public boolean removeMortgageFromPlace(int squareIndex) {
        Player player = players.get(getCurrentPlayerId());
        PlaceCard currentPlace = (PlaceCard) player.getSpecificCard(squareIndex);
        int mortgagePrice = currentPlace.getMortgagePrice();
        int mortgagePenalty = (int) (mortgagePrice * PropertyCard.getMortgagePenalty());
        if (player.removeMoney(Constants.CURRENCY_NAMES[0], mortgagePrice + mortgagePenalty)) {
            player.getSpecificCard(squareIndex).setMortgaged(false);
            board.getSquares().get(squareIndex).setLevel(0);
            return true;
        }
        return false;
    }

    public void createAuction(){
        this.state = GameState.Auction;
        this.auctionPropertyIndex = players.get(currentPlayerId).getCurrentPosition();
        PropertyCard card = bank.getPropertyCards().get(this.auctionPropertyIndex);
        this.currentBid = card.getCost();
        for(Player p : players){
            if(!(p.getName().equals(players.get(currentPlayerId).getName()))){
                participants.add(p);
            }
        }
        this.currentPlayerAuctioning = 0;
    }

    public void continueAuction(int bidIncrease){
        this.currentPlayerAuctioning += 1;
        if(this.currentPlayerAuctioning > participants.size()){
            this.currentPlayerAuctioning = 0;
        }
        this.currentBid += bidIncrease;
    }

    //TODO: POSSIBLE LOGIC ERROR (but I think I solved it :D - Ali the Lele)
    public void pullOffAuction(){
        participants.remove(currentPlayerAuctioning);
        if(currentPlayerAuctioning == this.participants.size() - 1){
            //If last player
            this.currentPlayerAuctioning = -1;
        }else{
            this.currentPlayerAuctioning -= 1;
        }
        continueAuction(0);
    }

    public void endAuction(){
        if(checkAuctionStatus()){
            Player currentPlayer = participants.get(currentPlayerAuctioning);
            Square square = board.getSpecificSquare(auctionPropertyIndex);
            PropertyCard card = bank.getPropertyCards().get(square.getId());

            //Make changes on data
            card.setOwnedBy(currentPlayerAuctioning);
            currentPlayer.ownProperty(getSpecificProperty(square.getId()));
            bank.getPropertyCards().set(square.getId(), card);
            board.buySquare(square.getId());
        }
        this.state = GameState.Linear;
    }

    public void buildBuilding(Building buildingType, int squareIndex) {
        Player player = players.get(currentPlayerId);

        Square squareToBuild = board.getSpecificSquare(squareIndex);
        PlaceCard currentPlace = (PlaceCard) player.getSpecificCard(squareToBuild.getId());

        int money;
        if ( buildingType == Building.House ){
            money = currentPlace.getHousePrice();
        }
        else{
            money = currentPlace.getHotelPrice();
        }

        board.build(buildingType, squareToBuild.getId());

        player.removeMoney(Constants.CURRENCY_NAMES[0], money);

        addToLog("built structures on the property: " + bank.getPropertyCards().get(squareToBuild.getId()).getName(), player.getName());
        //players.set(currentPlayerId, player);
    }

    public void destructBuilding(Building buildingType, int squareIndex){
        Player player = players.get(currentPlayerId);

        Square squareToDestruct = board.getSpecificSquare(squareIndex);
        PlaceCard currentPlace = (PlaceCard) player.getSpecificCard(squareToDestruct.getId());

        int money;
        if ( buildingType == Building.House ){
            money = (int) (currentPlace.getHousePrice() / Bank.getReturnRate());
        }
        else{
            money = currentPlace.getHotelPrice();
        }

        board.destroy(buildingType, squareToDestruct.getId());

        player.addMoney(Constants.CURRENCY_NAMES[0], money);
        addToLog("built structures on the property: " + bank.getPropertyCards().get(squareToDestruct.getId()).getName(), player.getName());
        //players.set(currentPlayerId, player);
    }

    /*
     * RETURN VALUES EXPLAINED
     * -99 => PLAYER BROKE
     * 1 => DRAWN GOOJC
     * 2 => DRAWN GTJC
     * 3 => CHANGED POSITION FORWARD
     * 4 => CHANGED POSITION TO INDEX
     * 5 => PAY MONEY FOR BUILDINGS
     * 6 => PAY TO BANK
     * 7 => BIRTHDAY GIFT BABY!
     * UNKNOWN VALUE > 100000 => EITHER PAY MONEY OR DRAW CHANCE CARD
     */
    public int drawCard(DrawableCardType cardType){
        if(cardType == DrawableCardType.Chance){
            var cardDrawn = board.drawChanceCard();
            var player = players.get(currentPlayerId);
            //Algorithm
            addToLog("drawn a chance card including message: " + cardDrawn.getMessage(), player.getName());
            if(cardDrawn.isGOOJC()){
                //If card is a GOOJC, save to inventory of the current player
                player.addToSavedCards(cardDrawn);
                return 1;
            }else if(cardDrawn.isGTJC()){
                //If card is a GTJC, move player to jail
                player.setCurrentPosition(10);
                player.setInJail(true);
                return 2;
            }else{
                if(cardDrawn.isComposed()){
                    //If the card has more than one operation
                    if(cardDrawn.isMoving()){
                        //If requires moving, look for movement
                        int moveToIndex = cardDrawn.getMoveToIndex();
                        int moveInCounts = cardDrawn.getMoveInCounts();
                        if(moveToIndex == -1 && moveInCounts != -1){
                            //If card specifies to move forward
                            player.setCurrentPosition(player.getCurrentPosition() + moveInCounts);
                            return 3;
                        }else if(moveToIndex != -1 && moveInCounts == -1){
                            //If card specifies to move to another square
                            if(cardDrawn.isGettingMoney()){
                                //If passed GO! Square during move
                                int currentPosition = player.getCurrentPosition();
                                if(currentPosition > moveToIndex){
                                    player.addMoney(Constants.CURRENCY_NAMES[0], Constants.GO_SQUARE_MONEY);
                                }
                            }
                            player.setCurrentPosition(moveToIndex);
                            return 4;
                        }
                    }else if(cardDrawn.isRelatedToBuildings()){
                        //If not moving but paying for each building owned
                        var owned = player.getOwnedPlaces();
                        int housesOwned = 0;
                        int hotelsOwned = 0;
                        for(PropertyCard c : owned){
                            for(Square s : board.getSquares()){
                                if(c.getId() == s.getId()){
                                    housesOwned += s.getHouseCount();
                                    hotelsOwned += s.getHotelCount();
                                }
                            }
                        }
                        int moneyToHotels = hotelsOwned * cardDrawn.getMoneyForHotels();
                        int moneyToHoses = housesOwned * cardDrawn.getMoneyForHouses();
                        if(player.removeMoney(Constants.CURRENCY_NAMES[0], moneyToHoses + moneyToHotels)){
                            return 5;
                        }else{
                            player.setBankrupt(true);
                            HashMap<Integer, Integer> payHash = new HashMap<>();
                            payHash.put(players.size(), moneyToHoses + moneyToHotels);
                            brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                            return -99;
                        }
                    }
                }else{
                    //If not composed or moving, hence paying money
                    if(player.removeMoney(Constants.CURRENCY_NAMES[0], cardDrawn.getMoneyOwe())){
                        return 6;
                    }else{
                        player.setBankrupt(true);
                        HashMap<Integer, Integer> payHash = new HashMap<>();
                        payHash.put(players.size(), cardDrawn.getMoneyOwe());
                        brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                        return -99;
                    }
                }
            }
        }else{
            var cardDrawn = board.drawCommunityChestCard();
            var player = players.get(currentPlayerId);
            //Algorithm
            addToLog("drawn a community chest card including message: " + cardDrawn.getMessage(), player.getName());
            if(cardDrawn.isGOOJC()){
                //If card is a GOOJC, save to inventory of the current player
                player.addToSavedCards(cardDrawn);
                return 1;
            }else if(cardDrawn.isGTJC()){
                //If card is a GTJC, move player to jail
                player.setCurrentPosition(10);
                player.setInJail(true);
                return 2;
            }else if(cardDrawn.isDrawingChanceCard()){
                //Needed to be handled in front-end.
                return cardDrawn.getMoneyOwe();
            }else{
                if(cardDrawn.isComposed()){
                    //If the card has more than one operation
                    if(cardDrawn.isMoving()){
                        //If requires moving, look for movement
                        int moveToIndex = cardDrawn.getMoveToIndex();
                        int moveInCounts = cardDrawn.getMoveInCounts();
                        if(moveToIndex == -1 && moveInCounts != -1){
                            //If card specifies to move forward
                            player.setCurrentPosition(player.getCurrentPosition() + moveInCounts);
                            return 3;
                        }else if(moveToIndex != -1 && moveInCounts == -1){
                            //If card specifies to move to another square
                            if(cardDrawn.isGettingMoney()){
                                //If passed GO! Square during move
                                int currentPosition = player.getCurrentPosition();
                                if(currentPosition > moveToIndex){
                                    player.addMoney(Constants.CURRENCY_NAMES[0], Constants.GO_SQUARE_MONEY);
                                }
                            }
                            player.setCurrentPosition(moveToIndex);
                            return 4;
                        }
                    }else if(cardDrawn.isRelatedToBuildings()){
                        //If not moving but paying for each building owned
                        var owned = player.getOwnedPlaces();
                        int housesOwned = 0;
                        int hotelsOwned = 0;
                        for(PropertyCard c : owned){
                            for(Square s : board.getSquares()){
                                if(c.getId() == s.getId()){
                                    housesOwned += s.getHouseCount();
                                    hotelsOwned += s.getHotelCount();
                                }
                            }
                        }
                        int moneyToHouses = housesOwned * cardDrawn.getMoneyForHouses();
                        int moneyForHotels = hotelsOwned * cardDrawn.getMoneyForHotels();
                        if(player.removeMoney(Constants.CURRENCY_NAMES[0], moneyToHouses + moneyForHotels)){
                            return 5;
                        }else{
                            player.setBankrupt(true);
                            HashMap<Integer, Integer> payHash = new HashMap<>();
                            payHash.put(players.size(), moneyToHouses + moneyForHotels);
                            brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                            return -99;
                        }
                    }else if(cardDrawn.isEachPlayerIncluded()){
                        //Or paying money to other players
                        for (int i = 0; i < players.size(); i++) {
                            if(i != currentPlayerId){
                                var otherPlayer = players.get(i);
                                boolean ableToPay = otherPlayer.removeMoney(Constants.CURRENCY_NAMES[0], cardDrawn.getMoneyOwe());
                                if(!ableToPay){
                                    otherPlayer.setBankrupt(true);
                                }
                            }
                        }
                        player.addMoney(Constants.CURRENCY_NAMES[0], cardDrawn.getMoneyOwe() * players.size() - 1);
                        return 7;
                    }
                }else{
                    //If not composed or moving, hence paying money
                    if(player.removeMoney(Constants.CURRENCY_NAMES[0], cardDrawn.getMoneyOwe())){
                        return 6;
                    }else{
                        player.setBankrupt(true);
                        HashMap<Integer, Integer> payHash = new HashMap<>();
                        payHash.put(players.size(), cardDrawn.getMoneyOwe());
                        brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                        return -99;
                    }
                }
            }
        }
        return -1;
    }

    public void levelUp(int squareIndex){
        Square square = board.getSpecificSquare(squareIndex);
        int level = square.getLevel();

        if (level == -1){
            removeMortgageFromPlace(squareIndex);
        }
        else if (level == 0 && !square.isBought()){
            buyProperty();
        }
        else if(level == 0 && square.isBought()){
            buildBuilding(Building.House, squareIndex);
        }
        else if(level == 1){
            buildBuilding(Building.House, squareIndex);
        }
        else if(level == 2){
            buildBuilding(Building.House, squareIndex);
        }
        else if(level == 3){
            buildBuilding(Building.House, squareIndex);
        }
        else if(level == 4){
            buildBuilding(Building.Hotel, squareIndex);
        }
    }

    public void levelDown(int squareIndex){
        Square square = board.getSpecificSquare(squareIndex);
        int level = square.getLevel();

        if (level == 5){
            destructBuilding(Building.Hotel, squareIndex);
        }
        else if(level == 4){
            destructBuilding(Building.House, squareIndex);
        }
        else if(level == 3){
            destructBuilding(Building.House, squareIndex);
        }
        else if(level == 2){
            destructBuilding(Building.House, squareIndex);
        }
        else if(level == 1){
            destructBuilding(Building.House, squareIndex);
        }
        else if(level == 0){
            mortgagePlace(squareIndex);
        }
    }

    public int payForGetOutOfJail() {
        Player player = players.get(currentPlayerId);
        if (player.isInJail()) {
            if (player.removeMoney(Constants.CURRENCY_NAMES[0], Bank.getJailPenalty())) {
                return 1; // player can get out
            }
            else {
                player.setBankrupt(true); // player has gone bankrupt
                HashMap<Integer, Integer> payHash = new HashMap<>();
                payHash.put(players.size(), Bank.getJailPenalty());
                brokenPlayersMoneyHash.put(currentPlayerId, payHash);
                return -99; // player has not enough money to get out of jail, go bankrupt
            }
        }
        return 3; // player is not even in jail, control return
    }

    //************
    // Bankman Mode Functions
    //************
    public boolean giveLoan(int amount) {
        Player player = players.get(currentPlayerId);
        if (player.isGetLoanCurrently()) {
            System.out.println("You need to pay your loan first");
            return false;
        }
        System.out.println("Gave loan to the player " + player.getName());
        addToLog("got loan in amount " + amount, player.getName());
        bank.giveLoan(amount, player);
        return true;
    }

    public boolean receiveLoanBackFromPlayer() {
        Player player = players.get(currentPlayerId);
        if (!player.isGetLoanCurrently()) {
            System.out.println("The player is not on the loan currently");
            return false;
        }
        int amount = player.getLoan();
        if(player.removeMoney(Constants.CURRENCY_NAMES[0], amount)){
            System.out.println("Player paid his/her loan " + player.getName());
            addToLog("paid loan back with amount of: " + amount, player.getName());
            return true;
        }else{
            player.setBankrupt(true);
            HashMap<Integer, Integer> payHash = new HashMap<>();
            payHash.put(players.size(), amount);
            brokenPlayersMoneyHash.put(currentPlayerId, payHash);
            return false;
        }
    }

    public boolean checkHasToPayLoanBack() {
        Player player = players.get(currentPlayerId);
        if (player.getLoanTurn() == 0) {
            return true;
        }
        return false;
    }

    public void changeCurrenciesOfBank() {
        // call this function in the end of the each turn
        this.bank.changeCurrencies();
    }

    public int exchangeCurrency(String fromCurrency, String toCurrency, int amount) {
        Player player = players.get(currentPlayerId);
        return this.bank.exchangeMoney(player, fromCurrency, toCurrency, amount);
    }

    public double generateChanceMultiplier(int diceResult) {
        double result;
        int randomResult = (int) ((Math.random() * (12 - 1)) + 1);
        if (randomResult >= 1 && randomResult <= 3) {
            result = 0.5;
        }
        else if (randomResult >= 4 && randomResult <= 6) {
            result = 1;
        }
        else if (randomResult >= 7 && randomResult <= 9) {
            result = 1.5;
        }
        else {
            result = 2.0;
        }
        return result;
    }

    //************
    // Checker Functions
    //************

    public int checkJailStatus() {
        Player player = players.get(currentPlayerId);
        if (!player.isInJail()) {
            return -1; // Player is not in jail
        }
        int jailTurnCount = player.getInJailTurnCount();
        if (jailTurnCount == JAIL_TURN_COUNT) {
            return 1; // the player has to go out right now
        }
        return 2; // the player will remain in jail
    }


    public HashMap<String, Boolean> checkLevelStatus(int squareIndex){
        Player player = players.get(currentPlayerId);
        Square square = board.getSpecificSquare(squareIndex);
        PropertyCard card = getSpecificProperty(squareIndex);

        HashMap<String, Boolean> returningHash = new HashMap<>();

        if(currentPlayerId == card.getOwnedBy()){
            if(checkBuildBuilding(Building.House, square).containsKey(true) || checkBuildBuilding(Building.Hotel, square).containsKey(true) || checkDismortgage(player, square)){
                returningHash.put("levelUp", true);
            } else{
                returningHash.put("levelUp", false);
            }

            if(checkDestructBuilding(Building.House, square).containsKey(true) || checkDestructBuilding(Building.Hotel, square).containsKey(true) || checkMortgage(player, square)){
                returningHash.put("levelDown", true);
            } else{
                returningHash.put("levelDown", false);
            }
        }
        else if(currentPlayerId != card.getOwnedBy() && card.getOwnedBy() != -1){
            returningHash.put("levelUp", false);
            returningHash.put("levelDown", false);
        }
        else {
            if (checkBuyProperty(squareIndex)){
                returningHash.put("levelUp", true);
            }
            else {
                returningHash.put("levelUp", false);
            }
            returningHash.put("levelDown", false);
        }
        return returningHash;
    }

    public boolean checkBrokenStatus(){
        for(Player p : players){
            if(p.isBankrupt()){
                this.state = GameState.Broken;
                return true;
            }
        }
        return false;
    }

    public boolean checkAuctionStatus(){
        return participants.size() == 1;
    }

    public boolean checkMortgage( Player currentPlayer, Square squareToMortgage ){

        int squareId = squareToMortgage.getId();
        int squareLevel = squareToMortgage.getLevel();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareId);

        if ( currentPlayer.isOwned(currentPlace ) && squareLevel == 0 ) {
            //System.out.println("Player can mortgage this place");
            return true;
        }
        //System.out.println("Player cannot mortgage this place");
        return false;
    }

    public boolean checkDismortgage( Player currentPlayer, Square squareToDismortgage){
        int squareId = squareToDismortgage.getId();
        int squareLevel = squareToDismortgage.getLevel();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareId);
        int dismortgageMoney = (int) (currentPlace.getMortgagePrice() * 1.10);

        if (currentPlayer.isOwned(currentPlace)) {
            if (squareLevel == -1){
                if (currentPlayer.getMonopolyMoneyAmount() >= dismortgageMoney){
                    //System.out.println("Player can dismortgage");
                    return true;
                }
                else {
                    //System.out.println("player does not have enough money to dismortgage");
                }
            }
            else {
                //System.out.println("This square is not mortgaged");
            }
        }
        else{
            //System.out.println("Player does not have this place");
        }
        return false;
    }
    public boolean checkBuyProperty(int index){
        Player currentPlayer = players.get(currentPlayerId);
        Square squareToBuy = board.getSpecificSquare(index);
        PropertyCard toGetCostOfPropertyCard = getSpecificProperty(squareToBuy.getId());
        boolean isBuyable = squareToBuy.getType() == SquareType.NormalSquare || squareToBuy.getType() == SquareType.UtilitySquare || squareToBuy.getType() == SquareType.RailroadSquare;
        if (isBuyable && currentPlayer.getCurrentPosition() == squareToBuy.getId()){
            if (!squareToBuy.isBought()){
                if (currentPlayer.getMonopolyMoneyAmount() >= toGetCostOfPropertyCard.getCost()){
                    //System.out.println("Player can buy this property ");
                    return true;
                }
                else {
                    //System.out.println("Player does not have enough money");
                    return false;
                }
            }
            else {
                //System.out.println("square is already bought");
                return false;
            }
        }
        else {
            //System.out.println("Player is not on this square");
            return false;
        }
    }

    public Map<Boolean, Integer> checkBuildBuilding(Building buildingType, Square squareToBuild){

        // TODO: Check house&hotel count of bank
        // Also, maybe the algorithm can change, according to the rulebook
        Map<Boolean, Integer> checkAndCountHouses = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotel = new HashMap<>();

        Player currentPlayer = players.get(currentPlayerId);

        int squareToBuildIndex = squareToBuild.getId();

        int colorsCountOnBoard = board.countColors(squareToBuild);
        int colorsCountOnPlayer = countPlayersColor(squareToBuild);
        int houseCountOnSquare = squareToBuild.getHouseCount();
        int hotelCountOnSquare = squareToBuild.getHotelCount();
        int currentMoney = currentPlayer.getMonopolyMoneyAmount();
        int squareLevel = squareToBuild.getLevel();

        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToBuildIndex);

        if ( colorsCountOnBoard == colorsCountOnPlayer ){   // Checks player has all squares with same the color (is there a mortgage or not check it!!!)
            if ( buildingType == Building.House && squareLevel < 4 && squareLevel > -1 ){

                int priceOfAHouse = currentPlace.getHousePrice();

                if (squareToBuild.isHouseCheck()){  // Checks the square has a house once or not

                    if (board.hasHouseAllSquares(squareToBuild)){   // Checks other squares have houses or not
                        int availableHouses = 4 - houseCountOnSquare;
                        int count = 0;
                        for ( int i = 1; i <= availableHouses; i++ ) {    // Calculates how many houses can be bought with player's money
                            if (currentMoney >= i * priceOfAHouse){
                                count++;
                            }
                        }
                        if (count > 0) { // checks if count is positive, count houses can be bought
                            checkAndCountHouses.put(true, count);
                            //System.out.println("Player can buy " + count + " houses on this square");
                        }
                        else {  // player cannot buy a house
                            checkAndCountHouses.put(false, 0);
                            //System.out.println("Max number of houses or not enough money");
                        }
                    }
                    else if ( !board.hasHouseAllSquares(squareToBuild) && houseCountOnSquare > 0 ){ // if there is one house and other squares don't have a house
                        checkAndCountHouses.put(false, 0);
                        //System.out.println("Player has already a house in this square");
                    }
                    else if ( !board.hasHouseAllSquares(squareToBuild) && houseCountOnSquare == 0 ){
                        if ( currentMoney > priceOfAHouse && currentPlayer.getCurrentPosition() == squareToBuildIndex ){    // checks player's money is enough for a house
                            checkAndCountHouses.put(true, 1);
                            //System.out.println("Player can buy a house");
                        }
                        else {
                            checkAndCountHouses.put(false, 0);
                            //System.out.println("Not sufficient money or player is not on that square");
                        }
                    }
                }
                else {  // if the square has no house
                    if ( currentMoney > priceOfAHouse && currentPlayer.getCurrentPosition() == squareToBuildIndex ){    // checks player's money is enough for a house
                        checkAndCountHouses.put(true, 1);
                        //System.out.println("Player can buy a house");
                    }
                    else {
                        checkAndCountHouses.put(false, 0);
                        //System.out.println("Not sufficient money or player is not on that square");
                    }
                }
                return checkAndCountHouses;
            }
            if ( buildingType == Building.Hotel && squareLevel == 4){
                if ( (houseCountOnSquare == 4 && hotelCountOnSquare == 0)) { // checks square has 4 houses
                    int priceOfAHotel = currentPlace.getHotelPrice();
                    if ( currentMoney > priceOfAHotel ){ // checks money is enough or not
                        checkAndCountHotel.put(true, 1);
                        //System.out.println("Player can buy an hotel");

                    }
                    else {  //money is not enough
                        checkAndCountHotel.put(false, 0);
                        //System.out.println("Not sufficient money");
                    }
                }
                else {  // houses are not enough or there is an hotel
                    checkAndCountHotel.put(false, 0);
                    //System.out.println("not enough houses or there is an hotel");
                }
                return checkAndCountHotel;
            }
            else{

                checkAndCountHouses.put(false, 0);
                //System.out.println("place is mortgaged");
            }
        }
        else{   // player does not have all squares with that color
            checkAndCountHouses.put(false, 0);
            //System.out.println("Player haven't got all squares with that color");
        }
        return checkAndCountHouses;
    }
    public Map<Boolean, Integer> checkDestructBuilding(Building buildingType, Square squareToDestruct){

        // TODO: Increment house&hotel count of bank
        // Also, maybe the algorithm can change, according to the rulebook

        Map<Boolean, Integer> checkAndCountHousesDestruct = new HashMap<>();
        Map<Boolean, Integer> checkAndCountHotelDestruct = new HashMap<>();

        Player currentPlayer = players.get(currentPlayerId);

        int houseCountOnSquare = squareToDestruct.getHouseCount();
        int hotelCountOnSquare = squareToDestruct.getHotelCount();

        int squareToDestructId = squareToDestruct.getId();
        PlaceCard currentPlace = (PlaceCard) currentPlayer.getSpecificCard(squareToDestructId);

        if ( currentPlace != null ){
            if ( buildingType == Building.Hotel) {
                if ( hotelCountOnSquare == 1 ) {
                    checkAndCountHotelDestruct.put(true, 1);
                    //System.out.println("Player can destruct an hotel");
                }
                else{
                    checkAndCountHotelDestruct.put(false, 0);
                    //System.out.println("there is no hotel to destruct");
                }
                return checkAndCountHotelDestruct;

            }
            if ( buildingType == Building.House ) {
                if (houseCountOnSquare > 0) {
                    checkAndCountHousesDestruct.put(true, hotelCountOnSquare);
                    //System.out.println("Player can destruct " + hotelCountOnSquare + " houses ");
                }
                else {
                    checkAndCountHousesDestruct.put(false, 0);
                    //System.out.println("There is no house to destruct");
                }
                return checkAndCountHousesDestruct;
            }
        }
        else {
            checkAndCountHousesDestruct.put(false, 0);
            //System.out.println("Player does not have this square");
            return checkAndCountHousesDestruct;
        }
        checkAndCountHousesDestruct.put(false, 0);
        return checkAndCountHousesDestruct;

    }


    //Getters and Setters

    public ArrayList<String> getChat() {
        return chat;
    }

    public void setChat(ArrayList<String> chat) {
        this.chat = chat;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public int getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public int getAuctionPropertyIndex() {
        return auctionPropertyIndex;
    }

    public void setAuctionPropertyIndex(int auctionPropertyIndex) {
        this.auctionPropertyIndex = auctionPropertyIndex;
    }

    public int getCurrentPlayerAuctioning() {
        return currentPlayerAuctioning;
    }

    public void setCurrentPlayerAuctioning(int currentPlayerAuctioning) {
        this.currentPlayerAuctioning = currentPlayerAuctioning;
    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Player> participants) {
        this.participants = participants;
    }
}
