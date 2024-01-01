import java.util.ArrayList;

/**
 * this class models the Big Two card game logics.
 * @author Guo Zebin
 */
public class BigTwo implements CardGame {
    //public constructor: (i) create 4 players and add them to the player list;
    // and (ii) create a BigTwoGUI object for providing the user interface.

    BigTwo(){
        for(int i = 0; i < 4; ++i) {
            this.playerList.add(new CardGamePlayer());
        }
        ui = new BigTwoGUI(this);
    }
    //private instant variables:
    private final int numOfPlayers = 4;
    private Deck deck = new BigTwoDeck();
    private ArrayList<CardGamePlayer> playerList = new ArrayList<CardGamePlayer>();
    private ArrayList<Hand> handsOnTable = new ArrayList<Hand>();
    private int currentPlayerIdx = -1;
    private BigTwoGUI ui;
    private boolean flagLegal = true;
    private int winner = 99;
    private int endprintNum = 0;
    //public methods:

    /**
     * a method for getting the number of players.
     * @return the number of players;
     */
    public int getNumOfPlayers(){
        return this.numOfPlayers;
    }

    /**
     * a method for retrieving the deck of cards being used.
     * @return the deck of cards being used.
     */
    public Deck getDeck(){
        return this.deck;
    }

    /**
     *  a method for retrieving the list of players.
     * @return the list of players.
     */
    public ArrayList<CardGamePlayer> getPlayerList(){
        return this.playerList;
    }

    /**
     *  a method for retrieving the list of hands played on the table.
     * @return the list of hands played on the table.
     */
    public ArrayList<Hand> getHandsOnTable(){
        return this.handsOnTable;
    }

    /**
     * a method for retrieving the index of the current player.
     * @return the index of the current player.
     */
    public int getCurrentPlayerIdx(){
        return this.currentPlayerIdx;
    }

    /**
     *  a method for starting/restarting the game with a given shuffled deck of cards
     * @param deck the deck of (shuffled) cards to be used in this game
     */
    public void start(Deck deck){
        for(int i = 0; i < 4; ++i){
            this.playerList.get(i).removeAllCards();
        }
        getHandsOnTable().clear();
        this.deck = deck;
        this.deck.initialize();
        this.deck.shuffle();
        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 13; ++j) {
                this.playerList.get(i).addCard(this.deck.getCard(i*13 + j));
            }
            this.playerList.get(i).sortCardsInHand();
        }
        for (int i = 0; i < 4; ++i){
            for (int j = 0; j < 13; ++j) {
                if (this.deck.getCard(i*13 + j).getSuit() == 0 && this.deck.getCard(i*13 + j).getRank() == 2) {
                    this.currentPlayerIdx = i;
                    ui.setActivePlayer(i);
                }
            }
        }
        ui.repaint();
        ui.promptActivePlayer();
    }


    /**
     * a method for making a move by a player with the specified index using the cards specified by the list of indices.
     * @param playerIdx the index of the player who makes the move
     * @param cardIdx   the list of the indices of the cards selected by the player
     */
    public void makeMove(int playerIdx, int[] cardIdx){
        this.checkMove(playerIdx, cardIdx);
    }

    /**
     * a method for checking a move made by a player
     * @param PlayerIdx
     * @param cardIdx   the list of the indices of the cards selected by the player
     */
    public void checkMove(int PlayerIdx, int[] cardIdx){//incomplete
        this.flagLegal = true;
        CardGamePlayer currentPlayer = playerList.get(PlayerIdx);
        CardList currentCards = currentPlayer.play(cardIdx);
        if(currentCards != null) {
            currentCards.sort();
        }
        Hand lastHand = null;
        Hand currentHand = this.composeHand(currentPlayer,currentCards);

        if (this.handsOnTable.size() > 0) {
            lastHand = this.handsOnTable.get(this.handsOnTable.size() - 1);
        }
        if (lastHand == null) {//first step;
            if (currentHand == null || currentHand.getType() == "Pass" ) {
                ui.printMsg("Not a legal move!\n");
                if(!endOfGame()) {
                    ui.promptActivePlayer();
                }
                this.flagLegal = false;
            } else {
                boolean flagD3 = false;
                for (int i = 0; i < currentHand.size(); ++i){
                    if(currentHand.getCard(i).getRank() == 2 && currentHand.getCard(i).getSuit() == 0){
                        flagD3 = true;
                        break;
                    }
                }
                if(flagD3){
                    this.flagLegal = true;
                }
                else {
                    ui.printMsg("Not a legal move!\n");
                    if(!endOfGame()) {
                        ui.promptActivePlayer();
                    }
                    this.flagLegal = false;
                }
            }
        } else if (lastHand != null) {
            if (currentHand == null) {
                ui.printMsg("Not a legal move!\n");
                if(!endOfGame()) {
                    ui.promptActivePlayer();
                }
                this.flagLegal = false;
            } else if (currentHand.getPlayer() == lastHand.getPlayer() && currentHand.getType() == "Pass") {
                ui.printMsg("Not a legal move!\n");
                if(!endOfGame()) {
                    ui.promptActivePlayer();
                }
                this.flagLegal = false;
            } else if (currentHand.getPlayer() != lastHand.getPlayer() && currentHand.beats(lastHand) == false && currentHand.getType() != "Pass") {
                ui.printMsg("Not a legal move!\n");
                if(!endOfGame()) {
                    ui.promptActivePlayer();
                }
                this.flagLegal = false;
            } else {
                this.flagLegal = true;
            }
        }if (this.flagLegal == false) {
            return;
        }
        // print valid move;
        if (lastHand == null && currentHand.getType() != "Pass" ) {//the first step;
            currentPlayer.removeCards(currentCards);
            handsOnTable.add(currentHand);
            ui.printMsg("{" + currentHand.getType() + "} ");
            ui.printMsg(currentHand.toString());
            ui.printMsg("\n");
            if(!endOfGame()) {
                currentPlayerIdx = (currentPlayerIdx + 1) % 4;
                ui.setActivePlayer(currentPlayerIdx);
                ui.repaint();
                ui.promptActivePlayer();
            }
        } else if (lastHand != null && currentHand.getType() == "Pass" ) {
            ui.printMsg("{pass}");
            ui.printMsg("\n");
            if(!endOfGame()) {
                currentPlayerIdx = (currentPlayerIdx + 1) % 4;
                ui.setActivePlayer(currentPlayerIdx);
                ui.repaint();
                ui.promptActivePlayer();
            }
        } else if (lastHand != null ) {
            currentPlayer.removeCards(currentCards);
            handsOnTable.add(currentHand);
            ui.printMsg("{" + currentHand.getType() + "} ");
            ui.printMsg(currentHand.toString());
            ui.printMsg("\n");
            if(!endOfGame()) {
                currentPlayerIdx = (currentPlayerIdx + 1) % 4;
                ui.setActivePlayer(currentPlayerIdx);
                ui.repaint();
                ui.promptActivePlayer();
            }
        }

        //end of game;
        if(endOfGame()){
            if (endprintNum == 0) {
                for (int i = 0; i < this.numOfPlayers; ++i) {
                    if (this.playerList.get(i).getNumOfCards() == 0) {
                        winner = i;
                        break;
                    }
                }
                ui.printMsg("Game ends\n");
                for (int i = 0; i < this.numOfPlayers; ++i) {
                    if (i != winner) {
                        ui.printMsg("Player " + i + " has " + this.playerList.get(i).getNumOfCards() + " card in hand\n");
                    } else {
                        ui.printMsg("Player " + winner + " wins the game\n");
                    }
                }
                endprintNum++;
            }
        }
    }

    /**
     * a method for checking if the game ends.
     * @return
     */
    public boolean endOfGame() {
        boolean flag = false;
        for (int i = 0; i < 4; ++i) {
            if (playerList.get(i).getNumOfCards() == 0) {
                flag = true;
                return flag;
            }
        }
        return flag;
    }

    //public static methods:

    /**
     * a method for starting a Big Two card game.
     * @param args
     */
    public static void main(String[] args){
        BigTwo game = new BigTwo();
        BigTwoDeck deck = new BigTwoDeck();
        deck.initialize();
        deck.shuffle();
        game.start(deck);
    }

    /**
     * a method for returning a valid hand from the specified list of cards of the player
     * @param player
     * @param cards
     * @return a valid hand from the specified list of cards of the player
     */
    public static Hand composeHand(CardGamePlayer player, CardList cards){
        Hand hand = null;
        if(cards == null){
            hand = new Pass(player, cards);
            return hand;
        }
        int length = cards.size();

        if (length == 1 &&(new Single(player, cards)).isValid()){
            hand = new Single(player, cards);
            return hand;
        }else if(length == 2 && (new Pair(player, cards)).isValid()){
            hand = new Pair(player, cards);
            return hand;
        }else if (length == 3 && (new Triple(player, cards)).isValid()){
            hand = new Triple(player, cards);
            return hand;
        }else if(length == 5){
            if((new Straight(player,cards)).isValid()){
                hand = new Straight(player, cards);
                return hand;
            }else if ((new Flush(player,cards)).isValid()){
                hand = new Flush(player,cards);
                return hand;
            }else if ((new FullHouse(player,cards)).isValid()){
                hand = new FullHouse(player,cards);
                return hand;
            }else if ((new Quad(player,cards)).isValid()) {
                hand = new Quad(player, cards);
                return hand;
            }else if((new StraightFlush(player,cards)).isValid()){
                hand = new StraightFlush(player,cards);
                return hand;
            }
        }
        return hand;
    }
}
