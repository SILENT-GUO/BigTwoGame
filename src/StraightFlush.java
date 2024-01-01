import java.util.ArrayList;
/**
 * this class record a card type called straight flush: 5 card with consecutive ranks and the same suit
 * @author Guo Zebin
 */
public class StraightFlush extends Hand{
    private int minV = 0, maxV = 0;
    private String type = "";
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public StraightFlush(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "StraightFlush";
    }
    /**
     * this method determines if the hand corresponds to the construction of straight flush type
     * @return whether the hand corresponds to the construction of straight flush type.
     */
    public boolean isValid(){
        boolean flagStraight = false, flagFlush = false;
        boolean haveA = false, have2 = false;
        this.minV = this.getCard(0).getRank();
        this.maxV = this.getCard(0).getRank();
        int _ = 0;
        while(this.minV <= 1 && _ < 5){
            this.minV = this.getCard(_).getRank();
            _++;
        }
        if(this.size() == 5) {
            ArrayList<Card> cards = new ArrayList<Card>(5);
            for (int i = 0; i < 5; ++i){
                cards.add(this.getCard(i));

                if (this.getCard(i).rank < this.minV && this.getCard(i).rank != 0 && this.getCard(i).rank != 1){
                    this.minV = this.getCard(i).rank;
                }else if(this.getCard(i).rank > this.maxV && this.getCard(i).rank != 0 && this.getCard(i).rank != 1){
                    this.maxV = this.getCard(i).rank;
                }
                if(this.getCard(i).rank == 0){
                    haveA = true;
                }else if(this.getCard(i).rank == 1){
                    have2 = true;
                }
            }
            if(haveA && !have2 && this.minV == 10 && this.maxV == 13){
                flagStraight = true;
            }else if (haveA && have2 && this.minV == 11 && this.maxV == 13){
                flagStraight = true;
            }else if (!haveA && !have2 && this.maxV - this.minV == 4){
                flagStraight = true;
            }else{
                return false;
            }
            int topCardSuit = this.getTopCard().suit;
            for(int i = 1; i < 5; ++i){
                if(this.getCard(i).getSuit() != topCardSuit){
                    return false;
                }
            }
            flagFlush = true;
        }
        if(flagFlush == true && flagStraight == true){
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
        if (hand.getType() == "StraightFlush"){
            if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
                return true;
            }else if (this.getTopCard().compareTo(hand.getTopCard()) == -1){
                return false;
            }
        }
        else if (hand.getType() == "Straight" || hand.getType() == "Flush" || hand.getType() == "FullHouse" || hand.getType() == "Quad"){
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
