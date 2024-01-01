import java.util.ArrayList;
/**
 * this class record a card type called flush: 5 cards with consecutive rank
 * @author Guo Zebin
 */
public class Straight extends Hand{
    private String type = "";
    private int minV = 0, maxV = 0;
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Straight(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Straight";
    }
    /**
     * this method determines if the hand corresponds to the construction of straight type
     * @return whether the hand corresponds to the construction of straight type.
     */
    public boolean isValid(){
        boolean haveA = false, have2 = false;
        if(this.size() != 5){
            return false;
        }
        this.minV = this.getCard(0).getRank();
        this.maxV = this.getCard(0).getRank();
        int ii = 0;
        while(this.minV <= 1 && ii < 5){
            this.minV = this.getCard(ii).getRank();
            ii++;
        }
        if(this.size() == 5) {
            ArrayList<Card> cards = new ArrayList<Card>(5);
            for (int i = 0; i < 5; ++i){
                cards.add(this.getCard(i));

                if (this.getCard(i).getRank() < this.minV && this.getCard(i).getRank() != 0 && this.getCard(i).getRank() != 1){
                    this.minV = this.getCard(i).getRank();
                }else if(this.getCard(i).getRank() > this.maxV ){
                    this.maxV = this.getCard(i).getRank();
                }
                if(this.getCard(i).getRank() == 0){
                    haveA = true;
                }else if(this.getCard(i).getRank() == 1){
                    have2 = true;
                }
            }
            if(haveA && !have2 && this.minV == 9 && this.maxV == 12){
                return true;
            }else if (haveA && have2 && this.minV == 10 && this.maxV == 12){
                return true;
            }else if (!haveA && !have2 && this.maxV - this.minV == 4){
                return true;
            }else{
                return false;
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
        if (hand.getType() == "Straight"){
            if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
                return true;
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
