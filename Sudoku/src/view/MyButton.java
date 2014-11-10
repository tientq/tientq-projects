package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class MyButton extends JButton {
	private String image;
	private boolean en;
	/**
	 * khởi tạo 1 nút có chức năng di chuột vào thì thay đổi hình nền
	 * @param image ảnh icon
	 * @param tooltip tooltip của button
	 * @param x tọa độ x
	 * @param y tọa độ y
	 * @param width chiều rộng
	 * @param height chiều cao
	 */
	public MyButton(String image, String tooltip, int x, int y, int width, int height) {
		super();
		this.image = image;
		this.setToolTipText(tooltip);
		this.setSize(width, height);
		this.setIcon(new ImageIcon("images/" + image + ".png"));
		this.setBounds(x, y, width, height);
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e) {
				setIconc();
			}
			public void mouseExited(MouseEvent e) {
				setIcon();
			}
		});
	}
	/**
	 * khởi tạo 1 nút cho phép chức năng disable
	 * @param image ảnh icon
	 * @param tooltip tooltip của button
	 * @param x tọa độ x
	 * @param y tọa độ y
	 * @param width chiều rộng
	 * @param height chiều cao
	 * @param allowDisable cho phép disable
	 */
	public MyButton(String image, String tooltip, int x, int y, int width, int height, boolean enable) {
		super();
		this.setToolTipText(tooltip);
		this.image = image;
		this.en = enable;
		this.setSize(width, height);
		this.resetIcon();
		this.setBounds(x, y, width, height);
		this.setBorderPainted(true);
		this.setContentAreaFilled(false);
		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
//				if (en) 
				setIconc();
			}
			public void mouseExited(MouseEvent e) {
				resetIcon();
			}
		});
	}
	
	public String toString() {
		return "mybutton";
	}
	private void setIcon() {
		setIcon(new ImageIcon("images/" + image + ".png"));
	}
	private void setIconc() {
		setIcon(new ImageIcon("images/" + image + "c.png"));
	}
	private void setIcond() {
		setIcon(new ImageIcon("images/" + image + "d.png"));
	}
	private void resetIcon() {
		if (en) setIcon();
		else setIcond();
	}
	public boolean isEnable() {
		return en;
	}
	public void enable(boolean en) {
		this.en = en;
		resetIcon();
	}
	public void enable() {
		this.en = true;
		resetIcon();
	}
	public void disable() {
		this.en = false;
		resetIcon();
	}
}
