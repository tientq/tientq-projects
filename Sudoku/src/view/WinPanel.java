package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.Sudoku;

@SuppressWarnings("serial")
public class WinPanel extends JPanel implements MouseListener{
	private static final Image bg = new ImageIcon("images/bg0.png").getImage();
	private Sudoku sudoku;
	private MyFrame frame;
	public WinPanel(Sudoku sudoku, MyFrame frame) {
		super(null);
		this.sudoku = sudoku;
		this.frame = frame;
		addMouseListener(this);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D gg = (Graphics2D) g;
		for (int y = 0; y < MyFrame.getScreenHeight(); y += 100)
			for (int x = 0; x < MyFrame.getScreenWidth(); x += 100) {
				g.drawImage(bg, x, y, this);
			}
		gg.setFont(new Font(null, 0, 35));
		gg.setColor(Color.RED);
		String s="Level Complete";
		gg.drawString(s, MyFrame.getScreenWidth()/2-gg.getFontMetrics().stringWidth(s)/2, MyFrame.getScreenHeight()/2-250);
		gg.setColor(Color.GREEN);
		String s1 = ""+sudoku.getUser()+"     Score: "+sudoku.getScore();
		gg.drawString(s1, MyFrame.getScreenWidth()/2-gg.getFontMetrics().stringWidth(s1)/2, MyFrame.getScreenHeight()/2-200);
		Vector<String> v = sudoku.getHighScore();
		gg.setColor(Color.WHITE);
		gg.drawString("CLick to continue", MyFrame.getScreenWidth()/2-gg.getFontMetrics().stringWidth("CLick to continue")/2, MyFrame.getScreenHeight()/2-100);
		if (v.size() > 0) {
			g.drawString("Top Score", MyFrame.getScreenWidth()/2-50, MyFrame.getScreenHeight()/2);
			for (int i=0; i<v.size(); i++) {
				g.drawString(v.get(i), MyFrame.getScreenWidth()/2-200+200*(i%2), MyFrame.getScreenHeight()/2+50+50*(i/2));
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		boolean b = sudoku.playNextLevel();
		if  (b) {
			sudoku.saveData();
			frame.show("PLAY");
		} else frame.show("SELECT");
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
}
