import gui.*;
import ai.*;
import twixt.*;
import logic.*;

/**
 * The Twixt main class. 
 *
 */
public class ConsoleTwixt {

    /**
	 *   main function
	 *
	 *  2 choices : 
	 *     - if no argument is given => gui wanted
	 *     - if 2 arguments (ai classnames of both player) => command line wanted
	 *
	 *  The command line is used to automatically run games and collect results
	 *
	 *  @param args arguments of the program
	 */
    public static void main(String[] args)
	{
    	
    	int boardSize = 12;
    	
		if (args.length == 2)
		{
			LogicManager logicManager = new LogicManager(boardSize);
			ConsoleDisplay console = new ConsoleDisplay();
			RunningGame runningGame = new RunningGame(logicManager, console);
			Factory  f_ai = new Factory();
			AbstractAI ai[];
			Player player1 = f_ai.getAI((String) args[0]);
			Player player2 = f_ai.getAI((String) args[1]);
			runningGame.setPlayers(player1, player2);
			runningGame.start();  // FIXME: start
		}
		else
		{
			System.err.println("Usage: ConsoleTwixt [<IA joueur 1> <IA joueur 2>]");
		}
    }

}
