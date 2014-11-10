package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Sudoku;


public class SelectPanelAction{
	private MyFrame frame;
	private SelectPanel select;
	private Sudoku sudoku;
	public SelectPanelAction(MyFrame frame, SelectPanel selectPanel, Sudoku sudoku) {
		this.frame = frame;
		this.select = selectPanel;
		this.sudoku = sudoku;
		addActionListener();
	}
	public void addActionListener() {
		select.viewContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!select.viewContinue.isLock()) {
					sudoku.playContinue();
					frame.show("PLAY");
				}
			}
		});
		select.viewResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!select.viewResult.isLock()) {
					sudoku.findResult();
					frame.show("RESULT");
				}
			}
		});
		select.viewCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!select.viewCreate.isLock()) {
					sudoku.createLevel();
					frame.show("CREATE");
				}
			}
		});
		select.viewEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!select.viewEasy.isLock()) {
					sudoku.playEasy();
					frame.show("PLAY");
				}
			}
		});
		select.viewNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!select.viewNormal.isLock()) {
					sudoku.playNormal();
					frame.show("PLAY");
				}
			}
		});
		select.viewHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!select.viewHard.isLock()) {
					sudoku.playHard();
					frame.show("PLAY");
				}
			}
		});
		select.butPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sudoku.previousLevel();
				select.repaint();
			}
		});
		select.butNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sudoku.nextLevel();
				select.repaint();
			}
		});
		select.butRandom1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sudoku.randomLevel();
				select.repaint();
			}
		});
		select.butRandom2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sudoku.randomLevel();
				select.repaint();
			}
		});
	}	
}
