/**
 *this class record a card type called single: one card
 *@author Guo Zebin
 */
public class Single extends Hand{
    private String type = "";
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Single(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Single";
    }
    /**
     * this method determines if the hand corresponds to the construction of single type
     * @return whether the hand corresponds to the construction of single type.
     */
    public boolean isValid() {
        if (this.size() == 1) {
            Card card = this.getTopCard();
            if (card.suit >= 0 && card.suit <= 3 && card.rank >= 0 && card.rank <= 12) {
                return true;
            }
        }
        return false;
    }
    /**
     * this determine if this hand beats the given hand as the parameter
     * @param hand
     * @return whether this hand beats the given hand as the parameter
     */
    public boolean beats (Hand hand){
        if (hand.getType() == "Single"){
            if (this.getTopCard().compareTo(hand.getTopCard()) > 0){
                return true;
            }else if(this.getTopCard().compareTo(hand.getTopCard()) < 0){
                return false;
            }
        }
        return false;
    }
    /**
     * this method retrieve the type of this hand
     * @return the type of this hand
     */
    public String getType(){
        return this.type;
    }
}
