import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public abstract class MessageListener implements ActionListener {

	private JTextArea destination;
	private JLabel labelWithImg;
	
	public MessageListener(JLabel labelWithImg) {
		this.setLabelWithImg(labelWithImg);
	}


	public JLabel getLabelWithImg() {
		return labelWithImg;
	}
	public void setLabelWithImg(JLabel labelWithImg) {
		this.labelWithImg = labelWithImg;
	}
	public JTextArea getDestination() {
		return destination;
	}
	public void setDestination(JTextArea destination) {
		this.destination = destination;
	}}