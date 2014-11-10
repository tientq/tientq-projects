package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import control.Sudoku;

@SuppressWarnings("serial")
public class ResultPanel extends Board{
	private JButton butResult, butReset;
	public ResultPanel(Sudoku sudokuin) {
		super(sudokuin);
		butResult = new JButton("Find Result");
		butResult.setFont(new Font(null, 0, 20));
		butResult.setBounds(xBox, yBox-50, 200, 30);
		butResult.setContentAreaFilled(false);
		butResult.addKeyListener(this);
		butResult.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sudoku.autoFindResult()) {
					repaint();
				} else {
					Graphics g = getGraphics();
					g.setColor(Color.RED);
					g.setFont(new Font(null, 0, 30));
					g.drawString("Sorry, Can not find any Result", MyFrame.getScreenWidth()/2-230, MyFrame.getScreenHeight()/2);
				}
			}
		});
		add(butResult);
		butReset = new JButton("Reset All");
		butReset.setFont(new Font(null, 0, 20));
		butReset.setBounds(xBox+250, yBox-50, 200, 30);
		butReset.setContentAreaFilled(false);
		butReset.addKeyListener(this);
		butReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudoku.findResult();
				repaint();
			}
		});
		add(butReset);
	}
	
}
