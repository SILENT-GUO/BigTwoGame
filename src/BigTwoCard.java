/**
 *  this class model a card  used in a Big Two card game.
 * @author Guo Zebin
 */
public class BigTwoCard extends Card{
    // public constructor:

    /**
     * the public constructor of Big Two Card
     * @param suit
     * @param rank
     */
    public BigTwoCard(int suit, int rank){
        super(suit, rank);

    }

    //overriding method:

    /**
     * a method for comparing the order of this card with the specified card
     * @param card the card to be compared
     * @return Returns a negative integer, zero, or a
     *         positive integer when this card is less than, equal to, or greater than the specified card.
     */
    @ Override
    public int compareTo(Card card) {
        int num = 0;
        if(this.getRank() > 1 && card.getRank() > 1){
            if (this.getRank() > card.getRank()){
                num = 1;
                return num;
            }
            else if (this.getRank() < card.getRank()){
                num = -1;
                return num;
            }else{
                num = 0;
            }
        }
        else if(this.getRank() > 1 && card.getRank() <= 1){
            num = -1;
            return num;
        }else if(this.getRank()<= 1 && card.getRank() <= 1){
            if (this.getRank() > card.getRank()){
                num = 1;
                return num;
            }
            else if (this.getRank() < card.getRank()){
                num = -1;
                return num;
            }else{
                num = 0;
            }
        }else if(this.getRank() <= 1 && card.getRank() > 1){
            num = 1;
            return num;
        }
        if(num == 0){//this.rank = card.rank
            if(this.getSuit() > card.getSuit()){
                num = 1;
                return num;
            }
            if(this.getSuit() < card.getSuit()){
                num = -1;
                return num;
            }
            else{
                num = 0;
            }
        }
        return num;
    }
}
