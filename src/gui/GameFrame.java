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

	public void wonMessage(int player)
	{
		if (player == 0)
			JOptionPane.showMessageDialog(this, "Congratulations! You've won " + menuPanel.p1Name + ".",
										  Constants.gameTitle, JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(this, "Congratulations! You've won " + menuPanel.p2Name + ".",
										  Constants.gameTitle, JOptionPane.INFORMATION_MESSAGE);
		boardPanel.setEnabled(false);
	}

	

	public void drawMessage()
	{
		JOptionPane.showMessageDialog(this, "Draw game!",
									  Constants.gameTitle, JOptionPane.INFORMATION_MESSAGE);
		boardPanel.setEnabled(false);
	}

	public String getName()
	{
		int idx_cur_player = (logicManager.getMoves().getPlayer() + 1) % 2;
		if (idx_cur_player == 0)
		{
			return menuPanel.p1Name;
		}
		else
		{
			return menuPanel.p2Name;
		}
	}


	public void getMove(LogicManager logicManager, SaveMove save)
	{
		boardPanel.getMove(save);
	}

	
}
