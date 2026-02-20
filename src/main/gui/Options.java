package main.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Display;
import main.Launcher;

public class Options extends Launcher{
	private int width = 540;
	private int height = 440;
	private JButton OKButton;
	private Rectangle rOKButton, rResolution;
	
	
	private Choice resolution = new Choice();
	
	public Options() {
		super(1); 
		setTitle("Options for the Game");
		setSize(new Dimension(width,height));
		setLocationRelativeTo(null);
		drawOptions();
	}
	
	private void drawOptions() {
		OKButton = new JButton("OK");
		rOKButton = new Rectangle((width - 100),(height - 70),button_width,button_height - 10);
		OKButton.setBounds(rOKButton);
		window.add(OKButton);
		
		rResolution = new Rectangle(50,80,80,25);
		
		resolution.setBounds(rResolution);
		resolution.add("640, 480");
		resolution.add("800, 600");
		resolution.add("1024, 768");
		resolution.select(1);
		window.add(resolution);
		
		OKButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Display.selection = resolution.getSelectedIndex();
				//System.out.println(Display.selection);
				dispose();
				new Launcher(0);
			}
		});
	}
}
