package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import control.Cell;
import control.Setting;

public class ViewCell extends JButton implements ActionListener, MouseListener {
	
	private static final long serialVersionUID = 1L;
	public static int width = 53;
	public static int height = 53;
	int row;
	int column;
	private boolean select;
	private Cell cell;
	private Board board;
	public ViewCell(Board board, Cell cell, int row, int column) {
		super();
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setSize(width, height);
		this.board = board;
		this.cell = cell;
		this.row = row;
		this.column = column;
		this.select = false;
		addActionListener(this);
		addMouseListener(this);
		addKeyListener(this.board);
	}
	public void paintComponent(Graphics g) {
		if (select) g.drawImage(new ImageIcon("images/cellc.png").getImage(), 0, 0, this);
		g.setFont(new Font(null, Font.BOLD, 40));
		g.setColor(Color.WHITE);
		if (cell.isError() && Setting.isShowError()) g.setColor(Color.RED);
		if (cell.isRootCell()) g.setColor(new Color(155, 76, 1));
		if (cell.getValue() != 0) g.drawString(""+(cell.getValue()>0?cell.getValue():""), 16, 41);
		else {
			g.setFont(new Font(null, 0, 15));
			if (cell.isNote()) {
				g.setColor(new Color(45, 238, 209));
				for (int i=0; i<9; i++) {
					if (cell.getNote(i+1)) g.drawString(""+(i+1), 8+i%3*15, 15+i/3*15);
				}
				return ;
			}
			if (Setting.isShowCandidate()) {
				g.setColor(Color.BLACK);
				for (int i=0; i<9; i++) {
					if (cell.getNote(i+1)) g.drawString(""+(i+1), 8+i%3*15, 15+i/3*15);
				}
				return ;
			}
		}
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
		this.repaint();
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public String toString() {
		return "cell";
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		board.select(row, column);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() >=2) {
			board.doubleClick(row, column);
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {	}
	@Override
	public void mouseExited(MouseEvent arg0) { }
	@Override
	public void mousePressed(MouseEvent arg0) { }
	@Override
	public void mouseReleased(MouseEvent arg0) { }
}