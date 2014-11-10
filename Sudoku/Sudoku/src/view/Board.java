package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.Setting;
import control.Sudoku;

@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener, KeyListener {
	public static final int boxWidth = 510;
	public static final int boxHeight = 510;
	public static final int xBox = MyFrame.getScreenWidth() / 2 - boxWidth / 2
			+ 2;
	public static final int yBox = MyFrame.getScreenHeight() / 2 - boxHeight
			/ 2 + 19;
	public static final Image BG = new ImageIcon("images/bg0.png").getImage();
	protected Sudoku sudoku;
	private ViewCell[][] viewCell;
	protected Point cellSelected;
	private NumberButton[] butNumber;
	private MyButton butUndo, butRedo, butNote;

	public Board(Sudoku sudoku) {
		super(null);
		setBounds(0, 0, MyFrame.getScreenWidth(), MyFrame.getScreenHeight());
		this.setVisible(true);
		this.sudoku = sudoku;
		cellSelected = new Point(9, 9);
		butNumber = new NumberButton[10];
		viewCell = new ViewCell[9][9];
		addKeyListener(this);
		init();
	}

	public void init() {
		butUndo = new MyButton("undo", "undo", xBox - 150, yBox, 100, 100, true);
		butUndo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (butUndo.isEnable()) {
					sudoku.undo();
					repaint();
				}
			}
		});
		butUndo.addKeyListener(this);
		add(butUndo);
		butRedo = new MyButton("redo", "redo", xBox - 120, yBox + 100, 60, 60,
				true);
		butRedo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (butRedo.isEnable()) {
					sudoku.redo();
					repaint();
				}
			}
		});
		butRedo.addKeyListener(this);
		add(butRedo);
		butNote = new MyButton("note", "note", xBox - 120, yBox + 450, 50, 50,
				Setting.isNote());
		butNote.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				butNote.enable(!butNote.isEnable());
				Setting.setNote(butNote.isEnable());
				butNote.repaint();
			}
		});
		butNote.addKeyListener(this);
		add(butNote);
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				viewCell[i][j] = new ViewCell(this, sudoku.getMap()[i][j], i, j);
				viewCell[i][j].setBounds(xBox + 1 + ViewCell.width * j + 3 * j
						+ j / 3 * 3, yBox + 1 + ViewCell.height * i + 3 * i + i
						/ 3 * 3, ViewCell.width, ViewCell.height);
				this.add(viewCell[i][j]);
			}
		for (int i = 0; i < 10; i++) {
			butNumber[i] = new NumberButton(i, xBox + boxWidth + 100, yBox - 50
					+ 60 * i);
			butNumber[i].addActionListener(this);
			butNumber[i].addKeyListener(this);
			this.add(butNumber[i]);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		for (int x = 0; x < MyFrame.getScreenWidth(); x += 100)
			for (int y = 0; y < MyFrame.getScreenHeight(); y += 100) {
				g.drawImage(BG, x, y, this);
			}
		g.drawImage(new ImageIcon("images/board.png").getImage(),
				MyFrame.getScreenWidth() / 2 - 277,
				MyFrame.getScreenHeight() / 2 - 300, this);
		if (Setting.isShowBlock())
			g.drawImage(new ImageIcon("images/block.png").getImage(),
					MyFrame.getScreenWidth() / 2 - 277,
					MyFrame.getScreenHeight() / 2 - 300, this);
		if (Setting.isShowLighting() && isCellSelected()) {
			g.drawImage(new ImageIcon("images/rowselect.png").getImage(), xBox,
					viewCell[cellSelected.x][cellSelected.y].getY(), this);
			g.drawImage(new ImageIcon("images/columnselect.png").getImage(),
					viewCell[cellSelected.x][cellSelected.y].getX(), yBox, this);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().toString().equalsIgnoreCase("number button")) {
			NumberButton b = (NumberButton) e.getSource();
			setValue(b.getValue());
		}
	}

	public void select(int x, int y) {
		if (this.isCellSelected()) {
			viewCell[cellSelected.x][cellSelected.y].setSelect(false);
		}
		cellSelected.x = x;
		cellSelected.y = y;
		if (this.isCellSelected()) {
			viewCell[x][y].setSelect(true);
			if (Setting.isSmartDigit()) {
				boolean[] b = sudoku.getMap()[x][y].getNote();
				for (int i=1; i<=9; i++) butNumber[i].enable(b[i]);
			} else for (int i=1; i<=9; i++) butNumber[i].enable();
		}
		repaint();
	}

	protected boolean isCellSelected() {
		if (cellSelected.x <= 8 && cellSelected.y <= 8 && cellSelected.x >= 0
				&& cellSelected.y >= 0)
			return true;
		return false;
	}

	public void setValue(int v) {
		if (isCellSelected()) {
			sudoku.setValue(v, cellSelected.x, cellSelected.y);
			repaint();
		}
	}

	@Override
	public void repaint() {
		super.repaint();
		if (sudoku == null)
			return;
		butUndo.enable(sudoku.isUndo());
		butRedo.enable(sudoku.isRedo());
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				viewCell[i][j].repaint();
		for (int i=0; i<10; i++) butNumber[i].repaint();
	}

	public void doubleClick(int row, int column) {
		sudoku.doubleClick(row, column);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (isCellSelected()) {
			int row = cellSelected.x, col = cellSelected.y;
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				row = (row <= 0) ? 8 : row - 1;
				select(row, col);
				break;
			case KeyEvent.VK_DOWN:
				row = (row >= 8) ? 0 : row + 1;
				select(row, col);
				break;
			case KeyEvent.VK_LEFT:
				col = (col <= 0) ? 8 : col - 1;
				select(row, col);
				break;
			case KeyEvent.VK_RIGHT:
				col = (col >= 8) ? 0 : col + 1;
				select(row, col);
				break;
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_BACK_SPACE:
				setValue(0);
				break;
			case KeyEvent.VK_A:
				doubleClick(row, col);
				for (int i=0; i<9; i++)
					for (int j=0; j<9; j++)
						viewCell[i][j].setSelect(false);
				viewCell[row][col].setSelect(true);
				break;
			default:
				break;
			}
		} else
			select(0, 0);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (isCellSelected()) {
			char c = e.getKeyChar();
			if (Character.isDigit(c)) {
				int v = Character.digit(c, 10);
				setValue(v);
			}
		} else
			select(0, 0);
	}

}