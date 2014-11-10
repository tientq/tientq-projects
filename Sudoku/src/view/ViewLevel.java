package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class ViewLevel extends JButton implements MouseListener {
	private int[][] map;
	protected String image;
	protected boolean select;
	protected boolean lock;

	public ViewLevel(String name, String image, String tooltip, int x, int y) {
		this.setMap(new int[9][9]);
		this.select = false;
		this.image = image;
		this.setToolTipText(tooltip);
		this.setSize(350, 350);
		this.setBounds(x, y, 350, 350);
		this.setBorderPainted(false);
		this.setLock(false);
		this.setContentAreaFilled(false);
		this.addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		if (select)
			g.drawImage(new ImageIcon("images/select.png").getImage(), 0, 0, this);
		g.drawImage(new ImageIcon("images/net.png").getImage(), 0, 0, this);
		g.drawImage(new ImageIcon("images/" + image + ".png").getImage(), 0, 0, this);
		g.setFont(new Font(null, Font.BOLD, 20));
		g.setColor(new Color(155, 76, 1));
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				g.drawString("" + (map[i][j] > 0 ? map[i][j] : ""),
							(int) (52 + 29.4 * j),
							(int) (91 + 29.3 * i));
			}
		if (lock) g.drawImage(new ImageIcon("images/lock.png").getImage(), 100, 85, this);
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}
	public void mouseClicked(MouseEvent arg0) {	}

	public void mouseEntered(MouseEvent arg0) {
		this.select = true;
		this.repaint();
	}

	public void mouseExited(MouseEvent arg0) {
		this.select = false;
		this.repaint();
	}

	public void mousePressed(MouseEvent arg0) {	}

	public void mouseReleased(MouseEvent arg0) { }

	public boolean isLock() {
		return lock;
	}
	public void setLock(boolean lock) {
		this.lock = lock;
	}
}
