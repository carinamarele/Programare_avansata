package gui;


import cards.Card;
import cards.CardStack;

public class CardTransfer
{
    private static final String SEPARATOR = ";";

    private Card[] aCards;

    /**
     * Creates a card transfer from a serialized
     * version of the cards.
     * @param pString The serialized version
     */
    public CardTransfer(String pString)
    {
        String[] tokens = pString.split(SEPARATOR);
        aCards = new Card[tokens.length];
        for( int i = 0; i < tokens.length; i++ )
        {
            aCards[i] = Card.get(tokens[i]);
        }
    }

    /**
     * Converts an array of cards into an id string
     * that can be deserialized by the constructor.
     * @param pCards The array of cards with high-ranking cards first.
     * @return The id string.
     */
    public static String serialize(CardStack pCards)
    {
        String result = "";
        for(Card card : pCards)
        {
            result += card.getIDString() + SEPARATOR;
        }
        if( result.length() > 0)
        {
            result = result.substring(0, result.length()-1);
        }
        return result;
    }

    /**
     * @return The top card in the transfer (the one with the highest rank)
     */
    public Card getTop()
    {
        return aCards[0];
    }

    /**
     * @return The number of cards in the transfer.
     */
    public int size()
    {
        return aCards.length;
    }
}

