package twixt;

/* @(#)RunIA.java
 */

import logic.*;
import ai.*;
import java.util.concurrent.*;

/**
 * Thread that encapsulate the IA execution 
 *
 * @author <a href="mailto:lemaguer@enssat.fr">Sébastien Le Maguer</a>
 */

public class RunAI extends Thread
{
	AbstractAI ai; 
	LogicManager logicManager;
	SaveMove to;
	ExecutorService executor;

	/**
	 *  Constructor
	 *
	 *  @param to alarm instance
	 *  @param logicManager a logic manager
	 *  @param ai the encapsulated ai
	 */
	public RunAI(SaveMove to, LogicManager logicManager, AbstractAI ai, ExecutorService executor)
	{
		this.to = to;
		this.logicManager = logicManager;
		this.ai = ai;
		this.executor = executor;
	}


	/**
	 *   Running method of the thread. The only thing done is calling of getMove from the
	 *   encapsulated AI.
	 */
	public void run()
	{
		try
		{
			ai.getMove(logicManager.clone(), to);
		}
		catch (Exception ex)
		{
		}
		finally
		{
			executor.shutdown();
		}
			// ai.getMove(logicManager.getMoves().getColorOnTurn(),
			// 		   logicManager.getBoard().clone(), 
			// 		   logicManager.cloneConnections(logicManager.getMoves().getColorOnTurn()), 
			// 		   logicManager.cloneConnections(logicManager.getMoves().getColor()), 
			// 		   (logicManager.getMoves().getSize() == 1), to);
	}
}
