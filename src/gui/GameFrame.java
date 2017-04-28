package gui;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

import logic.LogicManager;
import twixt.*;

public class GameFrame extends JFrame  implements Player, Display
{

    private static final long serialVersionUID = 1L;

    protected LogicManager logicManager;
    
    protected BoardPanel boardPanel;
    protected MenuPanel menuPanel;
    protected RunningGame runningGame;
	
    
    public GameFrame()
	{
        super();
		// Init model
		logicManager = new LogicManager();
        init();
    }
    
    public GameFrame(int size)
	{
        super();
		// Init model
		logicManager = new LogicManager(size);
        init();
    }
    
    
    private void init() {
		setTitle(Constants.gameTitle);
		setBackground(Constants.menuColor);
		setLayout(new BorderLayout());
		boardPanel = new BoardPanel(this);
		boardPanel.setEnabled(false);
		menuPanel = new MenuPanel(this);      
		add(boardPanel, BorderLayout.CENTER);
		add(menuPanel, BorderLayout.EAST);
		pack();
		setResizable(false);
		addWindowListener(new WindowAdapter(){
		    public void windowClosing(WindowEvent e) {
		        System.exit(1);
		    }
		});
		setLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - getWidth()) / 2,
					(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - getHeight()) / 2) ;
		setVisible(true);
    }

    
	public void prepareAICall()
	{
        boardPanel.setEnabled(false);
        menuPanel.setEnabled(false);
	}

	
	public void finishAICall()
	{
        menuPanel.setEnabled(true);
    }

	public void addPoint(int[] p)
	{
        boardPanel.addPoint(p);
	}

	@Override
	public void switchPlayers() {
		menuPanel.switchPlayers();
	}

	public int wonMessage(int playerIndex, Player player)
	{
		String[] choices = {"Restart", "Switch", "OK"};
		int chosen = JOptionPane.showOptionDialog(this,
				"<html><h1><b>" + player.getName() + " (player " + playerIndex + ")</b> wins!</h1></html>",
				Constants.gameTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices,
				choices[2]);
		boardPanel.setEnabled(false);
		return chosen;
	}

	

	public int drawMessage()
	{
		String[] choices = {"Restart", "Switch", "OK"};
		int chosen = JOptionPane.showOptionDialog(this,
				"<html><h1>Draw game!</h1></html>",
				Constants.gameTitle, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, choices,
				choices[2]);
		boardPanel.setEnabled(false);
		return chosen;
	}

	public String getName()
	{
		if (logicManager.getMoves().getPlayer() == 0)
		{
			return menuPanel.p1Name;
		}
		else
		{
			return menuPanel.p2Name;
		}
	}
	
	public void setName(String newName) {
		// Nothing
	}


	public void getMove(LogicManager logicManager, SaveMove save)
	{
		boardPanel.getMove(save);
	}

	public LogicManager getLogicManager() {
		return logicManager;
	}
	
}
