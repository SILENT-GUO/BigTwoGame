/**
 * a subclass of the CardList class and is used to model a hand of cards.
 * @author Guo Zebin
 */
public abstract class Hand extends CardList{
    //public constructor:

    /**
     * public constructor:
     * @param player
     * @param cards
     */
    public Hand(CardGamePlayer player, CardList cards){
        this.player = player;
        if(cards != null) {
            for (int i = 0; i < cards.size(); ++i) {
                this.addCard(cards.getCard(i));
            }
        }
    }

    //private instance variable:
    private CardGamePlayer player;

    //public methods:

    /**
     * a method for retrieving the player of this hand
     * @return the player of this hand
     */
    public CardGamePlayer getPlayer(){
        return this.player;
    }

    /**
     * a method for retrieving the top card of this hand.
     * @return the top card of this hand.
     */
    public Card getTopCard(){
        this.sort();
        return this.getCard(this.size()-1);
    }
    /**
     * this determine if this hand beats the given hand as the parameter
     * @param hand
     * @return whether this hand beats the given hand as the parameter
     */
    public boolean beats (Hand hand){
        return true;
    }

    //abstract methods:

    /**
     * this method determines if the hand corresponds to the construction of single type
     * @return whether the hand corresponds to the construction of single type.
     */
    public abstract boolean isValid();
    /**
     * this method retrieve the type of this hand
     * @return the type of this hand
     */
    public abstract String getType();

}
