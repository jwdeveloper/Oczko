package mini.game.components;

public class Player
{
    private int points;
    private String name;
    private boolean hasAI;


    public Player(String name)
    {
        this.name = name;
    }

    public String GetName() {
        return name;
    }

    public void ResetPoints()
    {
        points= 0;
    }

    public int GetPoints() { return points;}

    public void SetAI(boolean ai)
    {
        this.hasAI = ai;
    }
    public boolean HasAI()
    {
        return hasAI;
    }

    public void AddPoints(int points)
    {
        this.points+=points;
    }
}
