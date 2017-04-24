import gui.*;
import ai.*;
import twixt.*;
import logic.*;

/**
 * The Twixt main class. 
 *
 */
public class Twixt {

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

		// No argument => GUI
		if (args.length == 0)
		{
			new GameFrame();
		}
		// Need to 2 arguments
		else if (args.length == 2)
		{
			Factory  f_ai = new Factory();
			AbstractAI ai[];
			ai = new AbstractAI[2];
			ai[0] = f_ai.getAI((String) args[0]);
			ai[1] = f_ai.getAI((String) args[1]);

			LogicManager logicManager = new LogicManager();
			ConsoleDisplay dp = new ConsoleDisplay();
			RunningGame runningGame = new RunningGame(logicManager, dp);
			runningGame.setPlayers(ai);
			runningGame.start();  // FIXME: start
		}
		else
		{
			System.err.println("twixt [<IA joueur 1> <IA joueur 2>]");
		}
    }

}
