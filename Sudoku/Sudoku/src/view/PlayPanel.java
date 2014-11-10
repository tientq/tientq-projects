package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import control.Sudoku;

@SuppressWarnings("serial")
public class PlayPanel extends Board {
	private MyFrame frame;
	private JLabel lblTime;
	private JButton butPause;
	public PlayPanel(Sudoku sudokuin, MyFrame framein) {
		super(sudokuin);
		this.frame = framein;
		lblTime = new JLabel() {
			public void paintComponent(Graphics g) {
				int time = sudoku.getTime();
				int s = time%60;
				int m = (time%3600)/60;
				int h = time/3600;
				g.setFont(new Font(null, Font.BOLD, 20));
				g.setColor(Color.WHITE);
				String t = (h>0?h:"00")+" : "+(m>9?"":"0")+m+" : "+(s>9?"":"0")+s;
				g.drawString(t, 0, 20);
			}
		};
		lblTime.setBounds(xBox+300, yBox-50, 200, 40);
		add(lblTime);
		butPause = new JButton("pause");
		butPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sudoku.saveData();
				sudoku.stop();
				frame.show("PAUSE");
			}
		});
		butPause.setBounds(xBox, yBox-60, 200, 40);
		butPause.setForeground(Color.WHITE);
		butPause.setFont(new Font(null, 0, 20));
		butPause.setContentAreaFilled(false);
		butPause.addKeyListener(this);
		add(butPause);
		sudoku.setViewTime(lblTime);
	}
	@Override
	public void setValue(int v) {
		if (isCellSelected()) {
			sudoku.setValue(v, cellSelected.x, cellSelected.y);
			repaint();
			if (sudoku.isWin()) {
				sudoku.winAndEndGame();
				sudoku.saveData();
				frame.show("WIN"); 
			}
		}
	}
	@Override
	public void doubleClick(int row, int column) {
		sudoku.doubleClick(row, column);
		repaint();
		if (sudoku.isWin()) {
			sudoku.winAndEndGame();
			sudoku.saveData();
			frame.show("WIN"); 
		}
	}
}
