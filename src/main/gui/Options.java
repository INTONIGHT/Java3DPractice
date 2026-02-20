package main.gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Launcher;

public class Options extends Launcher{
	private int width = 540;
	private int height = 440;
	private JButton dummyButton;
	private Rectangle rDummyButton;
	
	public Options() {
		super(1); 
		setTitle("Options for the Game");
		setSize(new Dimension(width,height));
		setLocationRelativeTo(null);
		drawOptions();
	}
	
	private void drawOptions() {
		dummyButton = new JButton("OK");
		rDummyButton = new Rectangle((width - 100),(height - 70),button_width,button_height - 10);
		dummyButton.setBounds(rDummyButton);
		window.add(dummyButton);
		dummyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
				new Launcher(0);
			}
		});
	}
}
