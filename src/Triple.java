/**
 *this class record a card type called triple: three cards with the same rank
 *@author Guo Zebin
 */
public class Triple extends Hand{
    private String type = "";
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Triple(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Triple";
    }
    /**
     * this method determines if the hand corresponds to the construction of triple type
     * @return whether the hand corresponds to the construction of triple type.
     */
    public boolean isValid(){
        if (this.size() == 3) {
            Card firstCard = this.getCard(0), secondCard = this.getCard(1), thirdCard = this.getCard(2);
            if (firstCard.rank == secondCard.rank && firstCard.rank == thirdCard.rank && firstCard.suit >= 0 && firstCard.suit <= 3 && firstCard.rank >= 0 && firstCard.rank <= 12) {
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
        if (hand.getType() == "Triple"){
            if (this.getTopCard().getRank() > hand.getTopCard().getRank()){
                return true;
            }else if(this.getTopCard().getRank() > hand.getTopCard().getRank()){
                return false;
            }else{
                if (this.getTopCard().getSuit() > hand.getTopCard().getSuit()){
                    return true;
                }else{
                    return false;
                }
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
