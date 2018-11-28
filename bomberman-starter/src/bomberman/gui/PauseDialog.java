package bomberman.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bomberman.Game;
import highscore.highScore;

public class PauseDialog extends JDialog{
	JButton b1,b2,b3,b4;
	Game _game;
	File highScore_file = new File(PauseDialog.class.getResource("/highscore/highScore.txt").getFile());
	public PauseDialog(JFrame frame, Game game) {
		pack();
		_game = game;
		setBounds(game.getWidth()/2, game.getHeight()/2, 85, 180);
		setLayout(new GridLayout(4, 1,8,8));
		setLocationRelativeTo(game);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		setFocusable(true);
		b1 = new JButton("Resume");
		b2 = new JButton("How to play");
		b3 = new JButton("High score");
		b4 = new JButton("Exit");
		
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.getBoard().resume();
				setVisible(false);
				
			}
		});
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showGuideDialog();
			}
		});
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showHighScore();
			}
		});
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
				
		add(b1); add(b2); add(b3); add(b4);
		
		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}			
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if( e.getKeyCode() == e.VK_ESCAPE) closeDialog();
			}	
		});
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				_game.getBoard().resume();
			}
		});
	}
	private void showDialog(String content, String title) {
		JLabel label = new JLabel("<html><center>"+content);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JOptionPane.showMessageDialog(this, label, title, JOptionPane.PLAIN_MESSAGE);
	}
	protected void showGuideDialog() {
		showDialog("UP-DOWN-RIGHT-LEFT or W-S-D-A to move Bomber<br>SPACE or X to put bombs", "How to play");
	
		
	}
	private void closeDialog() {
		this.setVisible(false);
		_game.getBoard().resume();
	}
	protected void showHighScore() {
		String content = loadHighScoreFile(highScore_file);
		showDialog(content, "High score");
		
	}
	private String loadHighScoreFile(File file) {
		String content = "";
		int tmp;
		String str_tmp = null;
		BufferedReader br= null;
		ArrayList<highScore> h = new ArrayList<highScore>();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));			
			while ((str_tmp = br.readLine())!= null) {
				if ( !str_tmp.equals("firstLine") && !str_tmp.equals("")) {
					String[] y = str_tmp.split(":");
					h.add(new highScore(y[0], y[1]));
				}
			}
			Collections.sort(h, Collections.reverseOrder());
			for( highScore i : h) {
				content += i.toString() + "<br>";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
}
