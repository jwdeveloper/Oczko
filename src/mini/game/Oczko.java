package mini.game;

import mini.game.components.Card;
import mini.game.components.CardDeck;
import mini.game.components.Player;

import java.io.Console;
import java.util.ArrayList;
import java.util.Optional;

public class Oczko extends GameManager
{
    private int minPlayers = 2;
    private int maxPlayers =4;
    private int playerAmount =4;

    private ArrayList<Player> players = new ArrayList<Player>();
    private CardDeck deck;
    private boolean isStarted = false;
    private int currentPlayerIndex = 0;

    @Override
    protected void OnEnable()
    {
        this.OnCommand( "pass",(args)->
        {
            if(!isStarted)return;

            currentPlayerIndex  = (currentPlayerIndex+1)%players.size();
            this.Print("Kolejka gracza "+players.get(currentPlayerIndex).GetName());
            this.InvokeCommand("next");
        });

        this.OnCommand( "next",(args)->
        {
            if(!isStarted)return;


            //Jesli wszystkie karty z tali zostały wykorzystana gra jest kończona
            if(!deck.CanGetCard())
            {
                this.Print("=======================================");
                this.Print("!!!!  W tali nie ma więcej kart    !!!!");
                this.InvokeCommand("stop");
                return;
            }

            Player player = players.get(currentPlayerIndex);

            //Jesli gracz przebił wynik 21, przechozi sie do kolejnego gracza
            if(player.GetPoints() >21)
            {

                //Sprawdzanie czy inni gracze mogą dobrać karte
                Optional<Player> can_move = this.players.stream().filter(p -> p.GetPoints() < 21).findAny();

                if(can_move.isPresent())  //kolejny gracz dobiera
                    this.InvokeCommand("pass");
                else  //nikt nie może juz dobrac kart, więc gra jest kończona
                    this.InvokeCommand("stop");
                return;
            }


            Card card = deck.GetRandomCard();
            player.AddPoints(card.GetPoints());
            this.Print("Wylosowano karte "+card.toString()+" [ +"+card.GetPoints() +" Pkt ]      [łączenie: "+player.GetPoints()+" ]");


            if(player.HasAI()) //zaawansowany algorytm AI
            {
                this.InvokeCommand("next");
            }

        });

        this.OnCommand( "graczeLiczba",(args)->
        {
            if(args.length() == 0) return;

            int number  = Integer.parseInt(args);
            if(number > maxPlayers || number < minPlayers)
            {
                this.Print("Liczba graczy musi być pomiędzy "+minPlayers+" a "+maxPlayers);
                return;
            }
            this.playerAmount = number;
            this.Print("Ustawiono "+number+" graczy");
        });

        this.OnCommand( "graczeDodaj",(args)->
        {
            if(args.length() == 0)
            {
                this.Print("Nie podano imienia!");
                return;
            }
            if(isStarted)
            {
                this.Print("Nie można dodać gracza podczas trwania gry");
                return;
            }
            if(this.players.size() >= playerAmount )
            {
                this.Print("Nie można dodać więcej graczy");
                return;
            }
            this.players.add(new Player(args));
            this.Print("Gracz "+args+ " został dodany");
        });

        this.OnCommand( "graczeDodajBot",(args)->
        {
            if(isStarted)
            {
                this.Print("Nie można dodać gracza podczas trwania gry");
                return;
            }
            if(this.players.size() >= playerAmount )
            {
                this.Print("Nie można dodać więcej graczy");
                return;
            }
            Player ai =new Player("Komputer#"+(this.players.size()+1));
            ai.SetAI(true);
            this.players.add(ai);
            this.Print("Komputer został dodany do gry");
        });

        this.OnCommand( "start",(args)->
        {
            if(isStarted)
            {
                this.Print("Gra już jest rozpoczęta");
                return;
            }
            if(this.players.size() <= 1)
            {
                this.Print("Potrzeba conajmniej dwóch graczy do rozpoczęcia gry");
                return;
            }
            this.deck = new CardDeck();
            this.deck.Shuffle();
            this.currentPlayerIndex=0;
            this.players.forEach(p -> p.ResetPoints());
            this.isStarted = true;
            this.Print("Gra rozpoczęta");
            this.InvokeCommand("pass");
        });

        this.OnCommand( "stop",(args)->
        {
            if(!isStarted)
            {
                this.Print("Gra jeszcze nie jest rozpoczęta");
                return;
            }

            this.Print("==================== Zakończono =================");
            this.isStarted = false;
            this.Print("Punktacja:");

            ArrayList<Player> winners = new ArrayList<>();
            int bestScore =  Integer.MAX_VALUE;
            boolean isGoldenEye = players.stream().anyMatch(p -> p.GetPoints() == 22);

            for(Player player:players)
            {

                if(isGoldenEye)  //jesli zlote oczko sie pojawilo, to wypisuje wszystkoch graczy ktorze je maja
                {
                    if(player.GetPoints() == 22)
                        winners.add(player);
                }
                else
                {
                    int playerScore = Math.abs(21 - player.GetPoints());

                    if(playerScore == bestScore )
                    {
                        winners.add(player);
                    }
                    if(playerScore < bestScore )
                    {
                        winners.clear();
                        winners.add(player);
                        bestScore = playerScore;
                    }
                }


                this.Print("Gracz: "+player.GetName()+" punkty:"+player.GetPoints());
            }



            this.Print("==================== Wygrali =====================");
            for(Player player:winners)
            {
                this.Print("Gracz: "+player.GetName()+" punkty:"+player.GetPoints());
            }
            this.Print("==================================================");
            this.InvokeCommand("menu");
        });

        this.OnCommand( "menu",(args)->
        {
            if(isStarted)
            {
                return;
            }

            this.Print("==================== Gra w oczko =================");
            this.Print("Dostępne komendy:");
            this.Print("graczeLiczba (2-4)   - ustawianie liczby graczy");
            this.Print("graczeDodaj (imie)   - dodawanie gracza");
            this.Print("graczeDodajBot   - dodawanie komputera do gry");
            this.Print("start    - rozpoczynanie gry");
            this.Print("stop    - kończenie gry");
            this.Print("==================================================");
        });

      this.InvokeCommand("menu");
    }
    @Override
    protected void OnDisable()
    {
       this.Print("Game closed");
    }
    @Override
    protected void OnError(Exception e)
    {
        this.Print("Pojawił się błąd, sprawdź poprawność danych");
        super.OnError(e);
    }
}
