package mini.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.function.Consumer;

public abstract class GameManager
{
    protected boolean isRunning = false;

    protected HashMap<String,Consumer<String>> gameCommands = new HashMap<>();

    protected abstract void OnEnable();

    protected abstract void OnDisable();

    protected void OnError(Exception e)
    {
        System.out.println(e.getMessage());
    }

    public void Print(String message)
    {
        System.out.println(message);
    }

    public void Enable()
    {
        isRunning = true;
        OnEnable();
        GameLoop();
    }
    public void Disable()
    {
        isRunning = false;
        OnDisable();
    }
    //Dodawanie nowej komendy oraz akcji którą ona wywołuj
    public void OnCommand(String command,Consumer<String> action)
    {
        if(!this.gameCommands.containsKey(command))
        {
            this.gameCommands.put(command,action);
        }
    }

    //Wywoływanie komendy
    public void InvokeCommand(String cmd)
    {
        if(this.gameCommands.containsKey(cmd))
        {
            this.gameCommands.get(cmd).accept("");
        }
    }



    private void GameLoop() {

        String consoleInput;
        String cmd;
        String args;
        while (isRunning)
        {
            try
            {
                if(System.in.available()!= 0)
                {
                    consoleInput = new BufferedReader(new InputStreamReader(System.in)).readLine();

                    int endIndex =consoleInput.indexOf(' ') == -1?consoleInput.length():consoleInput.indexOf(' ');
                    cmd = consoleInput.substring(0, endIndex); //komenda
                    if(endIndex != consoleInput.length())
                          endIndex+=1;

                    args = consoleInput.substring(endIndex,consoleInput.length()); //argumenty podane po komendzie

                    if(gameCommands.containsKey(cmd))  //sprawdzanie czy komenda jest zarejestrowana
                    {
                        gameCommands.get(cmd).accept(args); //wywoływanie akcji
                    }
                }
            }
            catch (IOException e)
            {
                OnError(e);
            }
            catch (Exception e)
            {
                OnError(e);
            }
        }
    }



}
