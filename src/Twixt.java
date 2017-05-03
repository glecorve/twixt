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
    	
    	int boardSize = 12;
    	
    	GameFrame g = new GameFrame(boardSize);
//		LogicManager logicManager = g.getLogicManager();
//    	RunningGame runningGame = new RunningGame(logicManager, g);
//    	RunningGame runningGame = new RunningGame(g.getLogicManager(), g);
//		runningGame.setPlayers(g, g);
//		runningGame.start();

//		// No argument => GUI
//		if (args.length == 0)
//		{
//	    	Player player1 = g; // Human player plays through GUI
//	    	Player player2 = g; // Human player plays through GUI
//	    	runningGame.setPlayers(player1, player2);
//	    	runningGame.start();
//		}
//		// Need to 2 arguments
//		else if (args.length == 2)
//		{
//			Factory  f_ai = new Factory();
//			Player player1 = f_ai.getAI((String) args[0]);
//			Player player2 = f_ai.getAI((String) args[1]);
//			runningGame.setPlayers(player1, player2);
//			runningGame.start();  // FIXME: start
//		}
//		else
//		{
//			System.err.println("twixt [<IA joueur 1> <IA joueur 2>]");
//		}
    }

}
