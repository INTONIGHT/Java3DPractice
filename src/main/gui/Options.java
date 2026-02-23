package main.gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.Configuration;
import main.Display;
import main.Launcher;

public class Options extends Launcher{
	private int width = 540;
	private int height = 440;
	private JButton OKButton;
	private Rectangle rOKButton, rResolution;
	private JTextField tWidth, tHeight;
	private JLabel lWidth, lHeight;
	Configuration config = new Configuration();
	private int w = 0;
	private int h = 0;
	
	
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
		
		lWidth = new JLabel("Width: ");
		lWidth.setBounds(30,150,120,20);
		window.add(lWidth);
		
		tWidth = new JTextField();
		tWidth.setBounds(80,150,60,20);
		window.add(tWidth);
		
		lHeight = new JLabel("Height: ");
		lHeight.setBounds(30,180,120,20);
		window.add(lHeight);
		
		tHeight = new JTextField();
		tHeight.setBounds(80,180,60,20);
		window.add(tHeight);
		
		
		OKButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				config.saveConfiguration("width", parseWidth());
				config.saveConfiguration("height", parseHeight());
				dispose();
				new Launcher(0);
			}
		});
	}
	private void dropdownChoice() {
		int selection  = resolution.getSelectedIndex();
		
		if(selection == 0) {
			w = 640;
			h = 480;
		}
		if(selection == 1 || selection == -1) {
			w = 800;
			h = 600;
		}
		if(selection == 2) {
			w = 1024;
			h = 768;
		}
		
	}
	private int parseWidth() {
		try {
			int w = Integer.parseInt(tWidth.getText());
			if(w <=0) {
				w = 640;
			}
			return w;
		} catch(NumberFormatException e) {
			dropdownChoice();
			return w;
		}
		
	}
	
	private int parseHeight() {
		try {
			int h = Integer.parseInt(tHeight.getText());
			if(h <= 0) {
				h = 480;
			}
			return h;
		} catch (NumberFormatException e) {
			dropdownChoice();
			return h;
		} 
		
	}
}
