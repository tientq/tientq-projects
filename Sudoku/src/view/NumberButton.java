package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class NumberButton extends JButton{
	private int value;
	private int state;
	private static final Image img  = new ImageIcon("images/number.png").getImage();
	private static final Image imgc = new ImageIcon("images/numberc.png").getImage();
	private static final Image imgd = new ImageIcon("images/numberd.png").getImage();
	public static final int width = 50;
	public static final int height = 50;
	/**
	 * dùng cho butNumber trong lớp {@link Board}
	 * có thể thay đổi ảnh nền khi di chuột đến, có thể disable, có thể get/set value
	 * @param value giá trị của nút đó
	 * @param x tọa độ x
	 * @param y tọa độ y
	 */
	public NumberButton(int value, int x, int y) {
		super();
		this.value = value;
		this.setToolTipText(""+value);
		this.setBounds(x, y, width, height);
		this.setBorderPainted(true);
		this.setContentAreaFilled(false);
		state = 0;
		this.addMouseListener(new MouseAdapter() {
			public void mouseExited(MouseEvent e) {
				if (state == 1) state = 0;
			}
			public void mouseEntered(MouseEvent e) {
				if (state == 0) state = 1;
			}
		});
		
	}
	public String toString() {
		return "number button";
	}
	public void paintComponent(Graphics g) {
		switch (state) {
		case 1: g.drawImage(imgc, 0, 0, this); break ;
		case 2: g.drawImage(imgd, 0, 0, this); break ;
		default: g.drawImage(img, 0, 0, this); break ;
		}
		g.setFont(new Font(null, Font.BOLD, 30));
		g.setColor(Color.WHITE);
		if (value>0) g.drawString(""+value, 17, 37);
	}
	public boolean isDisable() {
		return state == 2;
	}
	public void disable() {
		state = 2;
		repaint();
	}
	public void enable() {
		state = 0;
		repaint();
	}
	public void enable(boolean b) {
		state = b?0:2;
		repaint();
	}
	public int getValue() {
		return value;
	}
}
