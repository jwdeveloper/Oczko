package mini.game;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {


    /*
      Aby zacząc gre należy dodać conajmniej 2 graczy.
      Może to być człowiek (graczeDodaj (imie)) lub komputer (graczeDodajBot)
       ==================== Gra w oczko =================
       Dostępne komendy:
       graczeLiczba (2-4)   - ustawianie liczby graczy
       graczeDodaj (imie)   - dodawanie gracza
       graczeDodajBot   - dodawanie komputera do gry
       start    - rozpoczynanie gry
       stop    - kończenie gry
       ==================================================
     */
    
    public static void main(String[] args)
    {
        Oczko game = new Oczko();
        game.Enable();
    }
}
