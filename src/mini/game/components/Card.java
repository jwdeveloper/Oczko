package mini.game.components;

public class Card
{
    private int number;
    private char symbol;
    private boolean used = false;

    public Card(int number,char symbol)
    {

        this.number = number;
        this.symbol = symbol;
    }

    public void SetUsed(boolean used) { this.used = used;}

    public boolean IsUsed() { return used;}

    public int GetNumber() {
        return number;
    }

    public char GetSymbol() {
        return symbol;
    }

    public int GetPoints()
    {
        switch (number)
        {
            case 1: //as
                return 11;
            case 11:   //walet
                return  2;
            case 12: //królowa
                return  3;
            case 13: //król
                return  4;
            default: //cała reszta
                return 1;
        }
    }



    @Override
    public String toString()
    {
        String name = GetCardName();

        if(name.length() == 0)
           return "[ "+number+" "+symbol+" ]";
        else
            return "[ "+name+" "+symbol+" ]";
    }


    private String GetCardName()
    {

        switch (number)
        {
            case 1:
              return   "AS";
            case 11:
                return   "Walet";
            case 12:
                return   "Królowa";
            case 13:
                return   "Król";
            default:
                return "";
        }
    }
}
