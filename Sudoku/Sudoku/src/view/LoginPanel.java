package view;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {
	private int xBox, yBox; // box Width = 200, box height = 300
	private JLabel status;
	private JTextField txtUser;
	private JPasswordField txtPass;
	public Button butLogin;
	public Button butRegister;
	public Button butGuest;
	public Button butExit;

	public LoginPanel() {
		super(null);
		xBox = MyFrame.getScreenWidth() / 2 - 100;
		yBox = MyFrame.getScreenHeight() / 2 - 150;
		status = new JLabel("Please enter user and password",
				JLabel.CENTER);
		status.setForeground(Color.WHITE);
		status.setBounds(xBox, yBox + 10, 200, 20);
		this.add(status);
		JLabel lbl1 = new JLabel("User's Name", JLabel.CENTER);
		lbl1.setForeground(Color.CYAN);
		lbl1.setBounds(xBox, yBox + 30, 200, 20);
		this.add(lbl1);
		JLabel lbl2 = new JLabel("Password", JLabel.CENTER);
		lbl2.setForeground(Color.CYAN);
		lbl2.setBounds(xBox, yBox + 100, 200, 20);
		this.add(lbl2);
		txtUser = new JTextField();
		txtUser.setFont(new Font(null, Font.ITALIC + Font.BOLD, 20));
		txtUser.setForeground(Color.ORANGE);
		txtUser.setBounds(xBox, yBox + 50, 200, 30);
		this.add(txtUser);
		txtPass = new JPasswordField();
		txtPass.setBounds(xBox, yBox + 120, 200, 30);
		this.add(txtPass);
		butLogin = new Button("Login");
		butLogin.setBounds(xBox, yBox + 180, 80, 30);
		butLogin.setBackground(Color.CYAN);
		this.add(butLogin);
		butRegister = new Button("Register");
		butRegister.setBounds(xBox + 120, yBox + 180, 80, 30);
		butRegister.setBackground(Color.CYAN);
		this.add(butRegister);
		butGuest = new Button("Play as Guest"); // dang nhap nhu tai khoan khach
		butGuest.setBounds(xBox, yBox + 230, 80, 30);
		butGuest.setBackground(Color.CYAN);
		this.add(butGuest);
		butExit = new Button("Exit Game");
		butExit.setBounds(xBox + 120, yBox + 230, 80, 30);
		butExit.setBackground(Color.CYAN);
		this.add(butExit);
		txtUser.requestFocus();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Image bg = new ImageIcon("images/bg0.png").getImage();
		for (int y = 0; y < MyFrame.getScreenHeight(); y += 100)
			for (int x = 0; x < MyFrame.getScreenWidth(); x += 100) {
				g.drawImage(bg, x, y, null);
			}
		g.drawImage(new ImageIcon("images/logo-big.png").getImage(), MyFrame
				.getScreenWidth() / 2 - 550, yBox > 250 ? (yBox - 250) : 10,
				null);
		Image bg1 = new ImageIcon("images/bg-blur.png").getImage();
		for (int y = yBox; y < yBox + 300; y += 100)
			for (int x = 0; x < MyFrame.getScreenWidth(); x += 100) {
				g.drawImage(bg1, x, y, null);
			}
	}
	public String getUser() {
		return txtUser.getText();
	}
	@SuppressWarnings("deprecation")
	public String getPass() {
		String s = txtPass.getText();
		txtPass.setText("");
		return s;
	}
	public void setStatus(String status) {
		this.status.setText(status);
	}
}
