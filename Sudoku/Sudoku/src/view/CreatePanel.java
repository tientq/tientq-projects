package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import control.Sudoku;

@SuppressWarnings("serial")
public class CreatePanel extends Board {
	private static final String[] strDifficult = {"Easy Level", "Normal Level", "Hard Level" };
	private static final int[] COUNT = {35, 27, 0};
	private JButton butCreate, butSave, butReset;
	private JComboBox<String> jcbDifficult, jcbLevel;
	private JCheckBox jcbNext;
	private int next = 1;
	public CreatePanel(Sudoku sudokuin) {
		super(sudokuin);
		initCreate();
	}
	private void initCreate() {
		Font font = new Font(null, 0, 20);
		butCreate = new JButton("Auto Creat");
		butCreate.setFont(font);
		butCreate.setContentAreaFilled(false);
		butCreate.setBounds(xBox, yBox-50, 150, 30);
		butCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int difficult = jcbDifficult.getSelectedIndex();
				if (sudoku.autoCreatLevel(COUNT[difficult])) {
					repaint();
				} else {
					Graphics g = getGraphics();
					g.setColor(Color.RED);
					g.setFont(new Font(null, 0, 30));
					g.drawString("Sorry, Can not create Level", MyFrame.getScreenWidth()/2-230, MyFrame.getScreenHeight()/2);
				}
			}
		});
		add(butCreate);
		butSave = new JButton("Save to database");
		butSave.setFont(font);
		butSave.setContentAreaFilled(false);
		butSave.setBounds(xBox+150, yBox-50, 200, 30);
		butSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int level = next;
				if (!jcbNext.isSelected()) level = jcbLevel.getSelectedIndex()+1;
				int difficult = jcbDifficult.getSelectedIndex();
				sudoku.addLevel(level, difficult);
				refresh(difficult);
				Graphics g = getGraphics();
				g.setFont(new Font(null, 0, 30));
				g.setColor(Color.GREEN);
				g.drawString("Save Success", MyFrame.getScreenWidth()/2-50, yBox+300);
			}
		});
		add(butSave);
		butReset = new JButton("Reset All");
		butReset.setFont(new Font(null, 0, 20));
		butReset.setBounds(xBox+350, yBox-50, 150, 30);
		butReset.setContentAreaFilled(false);
		butReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sudoku.createLevel();
				repaint();
			}
		});
		add(butReset);
		jcbDifficult = new JComboBox<>(strDifficult);
		jcbDifficult.setBounds(xBox-200, yBox+200, 100, 30);
		jcbDifficult.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				refresh(jcbDifficult.getSelectedIndex());
			}
		});
		add(jcbDifficult);
		Vector<String> v = sudoku.getListLevel(0);
		jcbLevel = new JComboBox<String>(v);
		jcbLevel.setBounds(xBox-200, yBox+300, 50, 30);
		jcbLevel.setVisible(false);
		add(jcbLevel);
		next = v.size()+1;
		jcbNext = new JCheckBox("level "+next, true);
		jcbNext.setBounds(xBox-200, yBox+250, 100, 30);
		jcbNext.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				jcbLevel.setVisible(!jcbNext.isSelected());
			}
		});
		add(jcbNext);
	}
	private void refresh(int difficult) {
		Vector<String> v = sudoku.getListLevel(difficult);
		next = v.size()+1;
		jcbLevel.removeAllItems();
		if (!v.isEmpty()) {
			while (!v.isEmpty()) {
				jcbLevel.addItem(v.remove(0));
			}
		}
		jcbNext.setText("Level "+next);
	}
}
