/**
 *this class record a card type called pair: two cards with the same rank
 *@author Guo Zebin
 */
public class Pair extends Hand{
    private String type = "";
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Pair(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Pair";
    }
    /**
     * this method determines if the hand corresponds to the construction of pair type
     * @return whether the hand corresponds to the construction of pair type.
     */
    public boolean isValid() {
        if (this.size() == 2) {
            Card firstCard = this.getCard(0), secondCard = this.getCard(1);
            if (firstCard.rank == secondCard.rank && firstCard.suit >= 0 && firstCard.suit <= 3 && firstCard.rank >= 0 && firstCard.rank <= 12) {
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
        if (hand.getType() == "Pair"){
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
