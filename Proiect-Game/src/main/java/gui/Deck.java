package gui;

import cards.Card;
import cards.CardStack;
import cards.Rank;
import cards.Suit;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck
{
    private CardStack aCards;

    /**
     * Creates a new deck of 52 cards, shuffled.
     */
    public Deck()
    {
        shuffle();
    }

    /**
     * Reinitializes the deck with all 52 cards, and shuffles them.
     */
    public void shuffle()
    {
        List<Card> cards = new ArrayList<>();
        for( Suit suit : Suit.values() )
        {
            for( Rank rank : Rank.values() )
            {
                cards.add( Card.get( rank, suit ));
            }
        }
        Collections.shuffle(cards);
        aCards = new CardStack(cards);
    }

    /**
     * Places pCard on top of the deck.
     * @param pCard The card to place on top of the deck.
     * @pre pCard !=null
     */
    public void push(Card pCard)
    {
        aCards.push(pCard);
    }

    /**
     * Draws a card from the deck and removes the card from the deck.
     * @return The card drawn.
     * @pre !isEmpty()
     */
    public Card draw()
    {
        return aCards.pop();
    }

    /**
     * @return True iff there are no cards in the deck.
     */
    public boolean isEmpty()
    {
        return aCards.isEmpty();
    }
}
