import java.util.ArrayList;
/**
 * this class record a card type called Quad:4 cards have the same rank while the other two have the same
 * @author Guo Zebin
 */
public class Quad extends Hand{
    private String type;
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Quad(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Quad";
    }
    /**
     * this method determines if the hand corresponds to the construction of full house type
     * @return whether the hand corresponds to the construction of full house type.
     */
    public boolean isValid(){

        if (this.size() == 5) {
            int rank1 = 1, rank2 = 0;
            int rank1Type = this.getTopCard().getRank(), rank2Type = 99;
            for (int i = 1; i < 5; ++i){
                if(this.getCard(i).getRank() == rank1Type){
                    rank1 ++;
                }else if(rank2Type == 99 && this.getCard(i).getRank() != rank1Type){
                    rank2Type = this.getCard(i).getRank();
                    rank2 ++;
                }
                else if(this.getCard(i).getRank() == rank2Type){
                    rank2 ++;
                }
            }
            if ((rank1 == 4 && rank2 == 1) || (rank1 == 4 && rank2 == 1)){
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
        if (hand.getType() == "Quad"){
            int tarRank = 99, htarRank = 99;
            Card tarCard = null, htarCard = null;
            int rank1 = this.getCard(0).getRank(), rank2 = this.getCard(1).getRank(), rank3 = this.getCard(2).getRank();
            int hrank1 = hand.getCard(0).getRank(), hrank2 = hand.getCard(1).getRank(), hrank3 = hand.getCard(2).getRank();
            if(rank1 == rank2){tarRank = rank1;tarCard =  this.getCard(0);}
            else if (rank1 == rank3){tarRank = rank1;this.getCard(2);}
            else if (rank2 == rank3){tarRank = rank2;this.getCard(1);}
            if(hrank1 == hrank2){htarRank = hrank1;htarCard = hand.getCard(0);}
            else if (hrank1 == hrank3){htarRank = hrank1;htarCard = hand.getCard(2);}
            else if (hrank2 == hrank3){htarRank = hrank2;htarCard = hand.getCard(1);}
            if(tarCard.compareTo(htarCard) == 1){
                return true;
            }else if(tarCard.compareTo(htarCard) == -1){
                return false;
            }
        else if (hand.getType() == "Straight" || hand.getType() == "Flush" || hand.getType() == "FullHouse"){
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
