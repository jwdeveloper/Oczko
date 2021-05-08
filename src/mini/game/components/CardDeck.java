package mini.game.components;

import java.util.Arrays;
import java.util.Random;

public class CardDeck
{
    private Card[] cards = new Card[52];
    private Random random = new Random();
    private int usedCards = 0;

    public CardDeck()
    {
        Create();
    }

    public boolean CanGetCard()
    {
        return usedCards < cards.length-1;
    }

    public void Show()
    {
        Arrays.stream(cards).forEach(card ->  System.out.println(card.toString()) );
    }

    public Card GetRandomCard()
    {
       int  index = random.ints(0, cards.length-1) .findFirst().getAsInt();

       if(usedCards >=cards.length-1) //wszystkie karty zostały wykorzystane
       {
           return null;
       }

       //losuje dopuki trafi na karte która nie była jeszcze uzyta
       while (cards[index].IsUsed())
       {
           index = random.ints(0, cards.length-1) .findFirst().getAsInt();
       }
       usedCards++;
       cards[index].SetUsed(true);
       return cards[index];
    }

    public void Shuffle()
    {

        Card temp;
        Integer newIndex;
        for(int i=0;i<cards.length;i++)
        {
            newIndex = random.ints(0, cards.length-1) .findFirst().getAsInt();
            temp = cards[i];
            cards[i] = cards[newIndex];
            cards[newIndex] = temp;
        }
    }
    private void Create()
    {
        this.usedCards = 0;
        char symbol = 'X';
        int cardTypeSize = cards.length/4;
        for(int i=0;i<cards.length;i++)
        {
            switch (i/cardTypeSize)
            {
                case 0:
                    symbol = '♠';
                    break;
                case 1:
                    symbol = '♥';
                    break;
                case 2:
                    symbol = '♦';
                    break;
                case 3:
                    symbol = '♣';
                    break;
            }

            cards[i] = new Card((i%cardTypeSize) +1,symbol);
        }
    }
}
