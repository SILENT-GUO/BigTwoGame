/**
 * this class model a deck of card used in a Big Two card game.
 * @author Guo Zebin
 */
public class BigTwoDeck extends Deck{
    //overriding method:
    @Override
    /**
     * model a deck of cards used in a Big Two card game
     */
    public void initialize(){
        removeAllCards();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                BigTwoCard card = new BigTwoCard(i, j);
                addCard(card);
            }
        }
    }
}
