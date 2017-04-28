package twixt;

import logic.*;
import java.util.concurrent.*;
import ai.AbstractAI;
import ai.RandomAI;


/**
 *  The twixt game running class
 */
public class RunningGame extends Thread
{
	private LogicManager logicManager;
	private Display displayManager;
	private Player[] tabPlayers;
	private AbstractAI defaultAI;
	private int delay;
	private final int DEFAULT_DELAY = 2000;
	
	/**
	 *  Twixt game constructor (no AI)
	 *
	 *  @param logicManager the logic manager
	 *  @param displayManager the display Manage
	 */
	public RunningGame(LogicManager logicManager, Display displayManager)
	{
		this.logicManager = logicManager;
		this.displayManager = displayManager;
		this.tabPlayers = null;
		defaultAI = new RandomAI();
		this.delay = DEFAULT_DELAY;
	}


	/**
	 *  Twixt game constructor (with players)
	 *
	 *  @param logicManager the logic manager
	 *  @param displayManager the display Manage
	 *  @param tabPlayers the players
	 */
	public RunningGame(LogicManager logicManager, Display displayManager, Player[] tabPlayers)
	{
		this.logicManager = logicManager;
		this.displayManager = displayManager;
		this.tabPlayers = tabPlayers;
		this.delay = DEFAULT_DELAY;
	}

	public void setPoint(int[] p)
	{
	}
	
	/**
	 *   Setter of all TwixtPlayers
	 *
	 *   @param tabPlayers desire AIs
	 */
	public void setPlayers(Player[] tabPlayers)
	{
		this.tabPlayers = tabPlayers;
	}
	
	/**
	 *   Setter of all TwixtPlayers
	 *
	 *   @param player1 First player
	 *   @param player2 Second player
	 */
	public void setPlayers(Player player1, Player player2)
	{
		Player[] tabPlayers = {player1, player2};
		this.tabPlayers = tabPlayers;
	}

	/**
	 *   Setter of one of the player
	 *
	 *   @param player the desired player
	 *   @param index the player index of new AI
	 */
	public void setPlayer(Player player, int index)
	{
		// FIXME: throws exception if bad index
		tabPlayers[index] = player;
	}
	
	private Player getPlayer(int index) {
		// FIXME: throws exception if bad index
		return tabPlayers[index]; 
	}

	/**
	 *   Setter of the delay
	 *
	 *   @param delay the delay in milliseconds
	 */
	public void setDelay(int delay)
	{
		this.delay = delay;
	}

	/**
	 *  Twixt game run method (thread specific)
	 *
	 */
	public void run()
	{
		int[] p = null;
		int idx_cur_player;
		do
		{
			SaveMove save = new SaveMove();
			idx_cur_player= logicManager.getMoves().getTurn();
			Player cur_player = tabPlayers[idx_cur_player];
			try
			{
				/* AI player */
				if (cur_player instanceof AbstractAI)
				{
					ExecutorService executor = Executors.newSingleThreadExecutor();
					executor.execute(new RunAI(save, logicManager, (AbstractAI) cur_player, executor));
					if (!executor.awaitTermination(delay, TimeUnit.MILLISECONDS))
					{
						executor.shutdownNow();
					}
				}
				/* Human player */
				else
				{
					do {
						cur_player.getMove(logicManager, save);
					} while(!logicManager.isPossibleMove(save.getP()));
				}

				
				/* Check the point */
				p = save.getP();
				if (!logicManager.isPossibleMove(p))
				{
					throw new MoveNotSetException();
				}

				System.out.println("[" + cur_player.getName() + "]\tplayed in position (" + p[0] + "," + p[1] + ")");
			}
			catch (InterruptedException ex)
			{
				System.out.println("[" + cur_player.getName() + "\thas been abnormally interrupted");
				System.exit(-1);
			}
			/* Bad point => choose a random point */
			catch (MoveNotSetException ex)
			{
				System.out.println("[" + cur_player.getName() + "] \thas not set any coordinate");
				p = defaultAI.chooseMove(logicManager);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.exit(-1);
			}
			
			/* Add the point */
			logicManager.add(p);
			displayManager.addPoint(p);
			System.out.println();
		} while (logicManager.getStatus() == 0);

		/* End of the game */
		int whatsNext = 0;
		if (logicManager.getStatus() < 0 )
		{
			whatsNext = displayManager.drawMessage();
		}
		else {
			whatsNext = displayManager.wonMessage(idx_cur_player+1, getPlayer(idx_cur_player));
		}
		// Switch
		if (whatsNext == 1) {
			Player tmp = tabPlayers[0];
			tabPlayers[0] = tabPlayers[1]; 
			tabPlayers[1] = tmp;
			displayManager.switchPlayers();
		}
		// Switch or restart
		if (whatsNext < 2) {
			logicManager.reinit();
			RunningGame runningGame = new RunningGame(logicManager, displayManager);
			runningGame.setPlayers(tabPlayers);
			runningGame.start();
		}
	}
}
