package main;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import main.gui.Options;

public class Launcher extends JFrame{
	protected JPanel window = new JPanel();
	private JButton play, options,help,quit;
	private Rectangle rplay,roptions,rhelp,rquit;
	private int width = 240;
	private int height = 320;
	//variable is visible to class and any class that extends the class
	protected int button_width = 80;
	protected int button_height = 40;
	private int screenMidPoint = (width/2) - (button_width / 2);
	
	
	public Launcher(int id ) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("Game Launcher");
		setSize(new Dimension(width,height));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(window);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		window.setLayout(null);
		if(id == 0) {
			drawButtons();
		}
		
	}
	
	private void drawButtons() {
		play = new JButton("Play");
		rplay = new Rectangle(screenMidPoint,50,button_width,button_height);
		play.setBounds(rplay);
		window.add(play);
		
		options = new JButton("Options");
		roptions = new Rectangle(screenMidPoint,100,button_width,button_height);
		options.setBounds(roptions);
		window.add(options);
		
		help = new JButton("Help");
		rhelp = new Rectangle(screenMidPoint,150,button_width,button_height);
		help.setBounds(rhelp);
		window.add(help);
		
		quit = new JButton("Quit");
		rquit = new Rectangle(screenMidPoint,200,button_width,button_height);
		quit.setBounds(rquit);
		window.add(quit);
		
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new RunGame();
			}
		});
		
		options.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new Options();
			}
		});
		
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("help");
			}
		});
		
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		
	}
}
