/**
 * this class record a card type called full house:3 cards have the same rank while the other two have the same
 * @author Guo Zebin
 */
public class FullHouse extends Hand{
    private String type = "";
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public FullHouse(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "FullHouse";
    }
    /**
     * this method determines if the hand corresponds to the construction of full house type
     * @return whether the hand corresponds to the construction of full house type.
     */
    public boolean isValid(){
        if (this.size() == 5) {
            int rank1 = 1, rank2 = 0;
            int rank1Type = this.getCard(0).getRank(), rank2Type = 99;
            for (int i = 1; i < this.size(); ++i){
                if(this.getCard(i).getRank() == rank1Type){
                    rank1 ++;
                }else if (rank2Type != 99 && this.getCard(i).getRank() == rank2Type){
                    rank2 ++;
                }
                else if(rank2Type == 99){
                    rank2Type = this.getCard(i).getRank();
                    rank2++;
                }else if( this.getCard(i).getRank() != rank1Type && this.getCard(i).getRank() != rank2Type){
                    return false;
                }
            }
            if (rank1 == 3 && rank2 == 2 || rank1 == 2 && rank2 == 3){
                return true;
            }
        }
        return false;
    }

    @Override
    public Card getTopCard(){
        this.sort();
        int num3 = 1;
        for(int i = 0; i < this.size() - 1; ++i){
            if(this.getCard(i).getRank() == this.getCard(i+1).getRank()){
                num3 ++;
            }else{
                num3 = 1;
            }
            if(num3 == 3){
                if(i == this.size() - 2){
                    return this.getCard(this.size() - 1);
                }else{
                    return this.getCard(2);
                }
            }
        }
        return null;
    }
    /**
     * this determine if this hand beats the given hand as the parameter
     * @param hand
     * @return whether this hand beats the given hand as the parameter
     */
    public boolean beats (Hand hand){
        System.out.println(hand.getTopCard().getRank());
        if (hand.getType() == "FullHouse"){
            if(this.getTopCard().compareTo(hand.getTopCard()) == 1) {
                return true;
            }else if (this.getTopCard().compareTo(hand.getTopCard()) == -1){
                return false;
            }
        }
        else if (hand.getType() == "Straight" || hand.getType() == "Flush"){
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
