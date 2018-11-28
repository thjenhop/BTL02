package bomberman.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bomberman.Board;
import bomberman.Game;
import highscore.highScore;

public class typingInfo extends JDialog{
	JTextField info;
	JButton ok;
	File highScore_file = new File(highScore.class.getResource("/highscore/highScore.txt").getFile());
	public typingInfo(Board _board) {	
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 173);
		setLayout(null);
		setLocationRelativeTo(_board.getGame());
		setTitle("You win");
		info = new JTextField();
		JLabel lblNewLabel = new JLabel("Please enter your name");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 208, 27);
		add(lblNewLabel);
		
		info.setBounds(12, 41, 208, 33);
		//info.setBackground(Color.gray);
		info.setEditable(true);
		info.setColumns(10);
		info.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if( e.getKeyCode() == e.VK_ENTER) {
					String name = info.getText();
					int point = _board.getPoints();
					if( name.equals("")) name = "noname";
					try {
						comfirmScore(name, point);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		add(info);
		ok = new JButton("Enter");
		ok.setBounds(59, 87, 115, 33);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = info.getText();
				int point = _board.getPoints();
				if( name.equals("")) name = "noname";
				try {
					comfirmScore(name, point);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(ok);
		addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if( e.getKeyCode() == e.VK_ENTER) {
					String name = info.getText();
					int point = _board.getPoints();
					if( name.equals("")) name = "noname";
					try {
						comfirmScore(name, point);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				if( e.getKeyCode() == e.VK_ESCAPE) {
					setHide();
				}
			}
		});
		setFocusable(true);
	}
	protected void comfirmScore(String name, int point) throws Exception {
		System.out.println("xuất");
		String content =  name +":" + point;
		BufferedWriter bw = new BufferedWriter(new FileWriter(highScore_file, true));
		bw.write("\n"+content);
		bw.close();
		setHide();		
	}
	protected void setHide() {
		this.setVisible(false);
	}
}
