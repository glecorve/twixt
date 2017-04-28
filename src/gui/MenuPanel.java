package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import logic.LogicManager;
import twixt.*;

// [FIXME: choose an IA by select in menu: IAGroupID]


public class MenuPanel extends JPanel{

    private static final long serialVersionUID = 1L;
    
    private GameFrame gameFrame;
    private JLabel p1Label;
    private JLabel p2Label;
    protected JButton backBtn;
    protected JButton forwardBtn;
    private JButton saveBtn;
    private JButton saveAsBtn;
    private NewGameDialog newGameDlg;
    protected JTextArea movesListTxt;
    protected JProgressBar aiProgress;

    protected String p1Name;
    protected String p2Name;
    protected Player[] players;
    private File saveFile;
    
    private JFileChooser fileChooser;
    
    public MenuPanel(GameFrame gf)
	{
        gameFrame = gf;
        p1Name = "A";
        p2Name = "B";
        newGameDlg = new NewGameDialog(p1Name, p2Name);
        setLayout(new BorderLayout());
        JPanel gamePanel = new JPanel(new GridLayout(5,1,0,0));
        gamePanel.setBackground(Constants.menuColor);
        gamePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "Game"));
        // new..., open..., save, save as..., exit
        JButton btn = new JButton("New...");
        btn.setBackground(Constants.btnColor);

		
        btn.addActionListener(new ActionListener()
		{
            public void actionPerformed(ActionEvent e) {
                newGameDlg.setVisible(true);
                if (newGameDlg.exitOK) {
                    gameFrame.logicManager.reinit();
                    gameFrame.boardPanel.repaint();
                    forwardBtn.setEnabled(false);
                    backBtn.setEnabled(false);
                    saveBtn.setEnabled(false);
                    
                    // Player 2
                    if (!newGameDlg.ai2Chb.getSelectedItem().equals("")) {
                        players[1] = newGameDlg.f_ai.getAI((String) newGameDlg.ai2Chb.getSelectedItem());
                        p2Name = newGameDlg.player2Tfd.getText();
                        players[1].setName(p2Name);
                    } else {
                        players[1] = gameFrame;
                        p2Name = newGameDlg.player2Tfd.getText().trim();
                        if (p2Name.length() > 12) p2Name = p2Name.substring(0, 12);
                    }
                    p2Label.setText(p2Name);
                    
                    // Player 1
                    if (!newGameDlg.ai1Chb.getSelectedItem().equals("")) {
                        players[0] = newGameDlg.f_ai.getAI((String) newGameDlg.ai1Chb.getSelectedItem());
                        p1Name = newGameDlg.player1Tfd.getText();
                        players[0].setName(p1Name);
                    } else {
                        players[0] = gameFrame;
                        p1Name = newGameDlg.player1Tfd.getText().trim();
                        if (p1Name.length() > 12) p1Name = p1Name.substring(0, 12);
                        gameFrame.boardPanel.setEnabled(true);
                        saveAsBtn.setEnabled(true);
                    }
                    p1Label.setText(p1Name);
                    
					run();
                }
            }
        });
        gamePanel.add(btn);


		// Open button
        btn = new JButton("Open...");
        btn.setBackground(Constants.btnColor);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showOpenDialog(gameFrame) == JFileChooser.APPROVE_OPTION) {
                    saveFile = fileChooser.getSelectedFile();
                    saveBtn.setEnabled(true);
                    saveAsBtn.setEnabled(true);
                    openIt();
                }               
            }
        });        
        gamePanel.add(btn);

		// Save button
        saveBtn = new JButton("Save");
        saveBtn.setEnabled(false);
        saveBtn.setBackground(Constants.btnColor);
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveIt();
            }
        });        
        gamePanel.add(saveBtn);

		// Save as button
        saveAsBtn = new JButton("Save as...");
        saveAsBtn.setEnabled(false);
        saveAsBtn.setBackground(Constants.btnColor);
        saveAsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fileChooser.showSaveDialog(gameFrame) == JFileChooser.APPROVE_OPTION) {
                    saveBtn.setEnabled(true);
                    saveFile = fileChooser.getSelectedFile();
                    saveIt();
                } 
            }
        });        
        gamePanel.add(saveAsBtn);

		// Quit button
        btn = new JButton("Quit");
        btn.setBackground(Constants.btnColor);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(gameFrame, "Do you really want to quit?", Constants.gameTitle,
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(1);
                }
            }
        });
        gamePanel.add(btn);

		// Adding button panel
        add(gamePanel, BorderLayout.NORTH);
        JPanel pnl = new JPanel(new BorderLayout());
        JPanel playersPnl = new JPanel(new GridLayout(2, 1, 0, 0));
        playersPnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "Players"));
        playersPnl.setBackground(Constants.menuColor);
        p1Label = new JLabel(p1Name);
        p2Label = new JLabel(p2Name);
        p1Label.setHorizontalAlignment(JLabel.CENTER);
        p2Label.setHorizontalAlignment(JLabel.CENTER);
        p1Label.setForeground(Constants.p1Color);
        p2Label.setForeground(Constants.p2Color);
        playersPnl.add(p1Label);
        playersPnl.add(p2Label);
        pnl.add(playersPnl, BorderLayout.NORTH);

		// Adding move bpannel
        JPanel movePnl = new JPanel(new GridLayout(2,1,0,0));
        JPanel pnl2 = new JPanel(new BorderLayout());
        movePnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "Move"));
        movePnl.setBackground(Constants.menuColor);

		// Adding back button
        backBtn = new JButton("Back");
        backBtn.setBackground(Constants.btnColor);
        backBtn.setEnabled(false);
        backBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameFrame.logicManager.canMoveBack()) {
                    backBtn.setEnabled(false);
                    return;
                }
                gameFrame.logicManager.back();
                if ((players[0] != null) || (players[1] != null)) {
                    if (gameFrame.logicManager.canMoveBack()) {
                        gameFrame.logicManager.back();
                        gameFrame.boardPanel.repaint();
                        forwardBtn.setEnabled(true);
                        if (gameFrame.logicManager.canMoveBack() && gameFrame.logicManager.getMoves().getSize() > 1) backBtn.setEnabled(true);
                        else backBtn.setEnabled(false);
                    } else {
                        gameFrame.logicManager.forward();
                        backBtn.setEnabled(false);
                    }
                } else {
                    gameFrame.boardPanel.repaint();
                    forwardBtn.setEnabled(true);
                    if (gameFrame.logicManager.canMoveBack()) backBtn.setEnabled(true);
                    else backBtn.setEnabled(false);
                }
                Color c = p1Label.getForeground();
                p1Label.setForeground(p2Label.getForeground());
                p2Label.setForeground(c);
                calculateMovesList();                
            }
        });
        movePnl.add(backBtn);

		// Adding forward button
        forwardBtn = new JButton("Forward");
        forwardBtn.setBackground(Constants.btnColor);
        forwardBtn.setEnabled(false);
        forwardBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!gameFrame.logicManager.canMoveForward()) {
                    forwardBtn.setEnabled(false);
                    return;
                }
                gameFrame.logicManager.forward();
                if ((players[0] != null) || (players[1] != null)) gameFrame.logicManager.forward();
                if (gameFrame.logicManager.canMoveForward()) forwardBtn.setEnabled(true);
                else forwardBtn.setEnabled(false);
                backBtn.setEnabled(true);
                gameFrame.boardPanel.repaint();
                calculateMovesList();
                Color c = p1Label.getForeground();
                p1Label.setForeground(p2Label.getForeground());
                p2Label.setForeground(c);
            }
        });
        movePnl.add(forwardBtn);
        pnl2.add(movePnl, BorderLayout.NORTH);
        JPanel moveListPnl = new JPanel(new BorderLayout());
        moveListPnl.setBackground(Constants.menuColor);
        moveListPnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "Move list"));
        movesListTxt = new JTextArea();
        movesListTxt.setEditable(false);
        movesListTxt.setAutoscrolls(true);
        movesListTxt.setBackground(Constants.btnColor);
        JScrollPane scp = new JScrollPane(movesListTxt, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scp.setBackground(Constants.menuColor);
        moveListPnl.add(scp, BorderLayout.CENTER);
        pnl2.add(moveListPnl, BorderLayout.CENTER);
        JPanel aiPnl = new JPanel(new BorderLayout());
        aiPnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Constants.menuBorderColor, 2), "AI progress"));
        aiPnl.setBackground(Constants.menuColor);
        aiProgress = new JProgressBar(0,100);
        aiProgress.setBackground(Constants.btnColor);
        aiProgress.setForeground(Constants.progressColor);
        aiPnl.add(aiProgress, BorderLayout.CENTER);
        pnl2.add(aiPnl, BorderLayout.SOUTH);
        pnl.add(pnl2, BorderLayout.CENTER);
        add(pnl, BorderLayout.CENTER);
		players = new Player[2];
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                if (!f.getName().equals(""))
                    return f.getName().endsWith(".mtp");
                return false;
            }
            public String getDescription() {
                return "MaraTwixt P&P file: *.mtp";
            }
        });
    }

    protected void calculateMovesList() {
        String s = "";
        for (int i = 0; i < gameFrame.logicManager.getMoves().getSize(); ++i) {
            int[] j = gameFrame.logicManager.getMoves().getMove(i);
            s += " " + (i+1) + ". " + ((char) (j[1] + 65)) + (j[0] + 1);
            s += "" + '\n' + '\r';
        }
        movesListTxt.setText(s);
    }
    
    private void saveIt() {
        String s = saveFile.getAbsolutePath();
        if (!s.endsWith(".mtp")) s += ".mtp";
			/*
        try {
            PrintWriter outS = new PrintWriter(new FileOutputStream(s));
            outS.println(p1Name);
            outS.println(hasAI[0]);
            outS.println(p2Name);
            outS.println(hasAI[1]);
            for (int i = 0; i < gameFrame.logicManager.getMoves().getSize(); ++i) {
                int[] j = gameFrame.logicManager.getMoves().getMove(i);
                outS.println(j[0]); outS.println(j[1]);
            }
            outS.close();
		   
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(gameFrame, "Error on saving file!", Constants.gameTitle, JOptionPane.ERROR_MESSAGE);
        }
			*/
    }
    
    private void openIt() {
        try {
            gameFrame.logicManager = new LogicManager();
			/*
            BufferedReader inS = new BufferedReader(new FileReader(saveFile.getAbsolutePath()));
            p1Name = inS.readLine();
            hasAI[0] = inS.readLine().equals("true");
            p2Name = inS.readLine();
            hasAI[1] = inS.readLine().equals("true");
            String s = inS.readLine();
            while (s != null) {
                gameFrame.logicManager.add(Integer.parseInt(s), Integer.parseInt(inS.readLine()));
                s = inS.readLine();
            }
            inS.close();
            if (gameFrame.logicManager.getMoves().getSwap()) {
                p1Label.setForeground(Constants.p2Color);
                p2Label.setForeground(Constants.p1Color);
            } else {
                p1Label.setForeground(Constants.p1Color);
                p2Label.setForeground(Constants.p2Color);
            }
            gameFrame.boardPanel.repaint();
            gameFrame.boardPanel.setEnabled(true);
            gameFrame.menuPanel.setEnabled(true);
			*/
        } catch (Exception e) {
            JOptionPane.showMessageDialog(gameFrame, "Error on opening file!", Constants.gameTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

	public void run()
	{
		RunningGame runningGame = new RunningGame(gameFrame.logicManager, gameFrame);
		runningGame.setPlayers(players);
		runningGame.start();
	}

	public void switchPlayers() {
		String tmpName = p1Name;
		p1Name = p2Name;
		p2Name = tmpName;
		
		p1Label.setText(p1Name);
		p2Label.setText(p2Name);
		
		repaint();
	}
    
}
