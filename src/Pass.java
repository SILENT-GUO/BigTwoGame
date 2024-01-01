import java.util.ArrayList;

/**
 * this class is used to define a type where input null by players
 */
public class Pass extends Hand{
    private String type = "";
    /**
     * the public constructor.
     * @param player
     * @param cards
     */
    public Pass(CardGamePlayer player, CardList cards){
        super(player, cards);
        this.type = "Pass";
    }
    /**
     * this method determines if the hand corresponds to the construction of single type
     * @return whether the hand corresponds to the construction of single type.
     */
    public boolean isValid() {
        if (this.size() == 0) {
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
