/**
 * this class record a card type called flush: 5 cards with the same suit
 * @author Guo Zebin
 */
public class Flush extends Hand{
    private String type = "";

    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Flush(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Flush";
    }

    /**
     * this method determines if the hand corresponds to the construction of flush type
     * @return whether the hand corresponds to the construction of flush type.
     */
    public boolean isValid(){
        if (this.size() == 5){
            int topCardSuit = this.getTopCard().getSuit();
            for(int i = 1; i < 5; ++i){
                if(this.getCard(i).getSuit() != topCardSuit){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * this determine if this hand beats the given hand as the parameter
     * @param hand
     * @return whether this hand beats the given hand as the parameter
     */
    public boolean beats (Hand hand){
        if (hand.getType() == "Flush"){
            if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
                return true;
            }else if (this.getTopCard().compareTo(hand.getTopCard()) == -1){
                return false;
            }
        }
        else if (hand.getType() == "Straight"){
            return true;
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
