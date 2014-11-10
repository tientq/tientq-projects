package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import control.Sudoku;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel {
	public static final Image bg = new ImageIcon("images/bg0.png").getImage();
	private Sudoku sudoku;
	private CardLayout card;
	JPanel menu, setting, help, highscore;
	public MyButton butSelect, butLogout, butSetting, butHelp, butHighScore, butExit, butBack, butBack2, butBack3, butCandidate, butError, butLighting, butBlock, butSmartDigit, butSound;
	Vector<String> v;
	public MenuPanel(Sudoku sudokuin) {
		super();
		card = new CardLayout();
		setLayout(card);
		this.sudoku = sudokuin;
		v = sudoku.getHighScore();
		this.setVisible(true);
		this.setBounds(0, 0, 300, MyFrame.getScreenHeight());
		menu = new JPanel(null) {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
				drawBackground(g);
			}
		};
		butSelect = new MyButton("butselect", "Select AutoCreateMap", 50, 200, 200, 80);
		butLogout = new MyButton("butlogout", "logout", 50, 280, 200, 80);
		butSetting = new MyButton("butsetting", "Setting", 50, 360, 200, 80);
		butHelp = new MyButton("buthelp", "Help", 50, 440, 200, 80);
		butHighScore = new MyButton("buthighscore", "High Score", 50, 520, 200, 80);
		butExit = new MyButton("butexit", "Exit game", 50, 600, 200, 80);
		butBack = new MyButton("butback", "Back to Menu", 50, 200, 200, 80);
		butBack2 = new MyButton("butback", "Back to Menu", 50, 200, 200, 80);
		butBack3 = new MyButton("butback", "Back to Menu", 50, 200, 200, 80);
		butCandidate = new MyButton("butcandidate", "Show Candidate", 35, 300, 100, 100, true);
		butError = new MyButton("buterror", "Show Error", 165, 300, 100, 100, true);
		butLighting = new MyButton("butlighting", "Show Row & Column Highlighting", 35, 420, 100, 100, true);
		butBlock = new MyButton("butblock", "Show Block Highlighting", 165, 420, 100, 100, true);
		butSmartDigit = new MyButton("smartdigit", "Show Smart Digit", 35, 540, 100, 100, true);
		butSound = new MyButton("butsound", "Turn On/Off Sound", 165, 540, 100, 100, true);
		
		menu.add(butSelect);
		menu.add(butLogout);
		menu.add(butSetting);
		menu.add(butHelp);
		menu.add(butHighScore);
		menu.add(butExit);
		
		setting = new JPanel(null) {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
				drawBackground(g);
			}
		};
		setting.add(butBack);
		setting.add(butCandidate);
		setting.add(butError);
		setting.add(butLighting);
		setting.add(butBlock);
		setting.add(butSmartDigit);
		setting.add(butSound);
		
		help = new JPanel(null) {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
				drawBackground(g);
				g.setFont(new Font(null, Font.ITALIC, 18));
				g.setColor(Color.GREEN);
				g.drawString("Author", 50, 350);
				g.drawString("Trần Quang Tiến   20102315", 25, 380);
				g.drawString("Nguyễn Phú Tùng   20102484", 25, 410);
				g.drawString("Nguyễn Văn Trường 20102397", 25, 440);
				g.setColor(Color.WHITE);
				g.drawString("Cách chơi Sudoku:", 50, 480);
				g.drawString("Sử dụng bàn phím hoặc chuột", 25, 510);
				g.drawString("để điền các số từ 1-9 vào từng", 25, 530);
				g.drawString("ô tương ứng sao cho các ô trên", 25, 550);
				g.drawString("một hàng, một cột hay trong các", 25, 570);
				g.drawString("ô lớn 3x3 không được trùng nhau", 20, 590);
				g.drawString("Một số chức năng trong trò chơi:", 20, 620);
				g.drawString("Mỗi người chơi có thể đăng ký 1", 20, 640);
				g.drawString("tài khoản để chơi, sau đó dùng", 20, 660);
				g.drawString("tài khoản đó để đăng nhập", 20, 680);
				g.drawString("Người chơi có thể chọn màn chơi", 20, 700);
				g.drawString("hoặc tìm 1 lời giải cho màn chơi", 20, 720);
				g.drawString("Admin có thể tạo thêm màn chơi", 20, 740);
			}
		};
		help.add(butBack2);
		
		highscore = new JPanel(null) {
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g) {
				drawBackground(g);
				g.setColor(Color.GREEN);
				String s = "High Score";
				g.setFont(new Font(null, 0, 30));
				int w = g.getFontMetrics().stringWidth(s);
				g.drawString(s, 150-w/2, 350);
				for (int i=0; i<v.size(); i++) {
					s = v.get(i);
					g.drawString(s, 30+150*(i%2), 420+40*(i/2));
				}
			}
		};
		highscore.add(butBack3);
		add(menu, "MENU");
		add(setting, "SETTING");
		add(help, "HELP");
		add(highscore, "HIGH SCORE");
	}

	public void drawBackground(Graphics g) {
		for (int y = 0; y < MyFrame.getScreenHeight(); y += 100)
			for (int x = 0; x < 300; x += 100) {
				g.drawImage(bg, x, y, null);
			}
		g.setFont(new Font(null, 20, 20));
		g.setColor(Color.CYAN);
		g.drawString("MENU", 130, 50);
		g.drawRect(10, 10, 280, MyFrame.getScreenHeight() - 20);
		g.setColor(Color.GREEN);
		g.drawRect(20, 20, 260, 140);
		g.drawLine(20, 60, 280, 60);
		g.drawString("User: " + sudoku.getUser(), 50, 100);
		g.drawString("Score: " + sudoku.getScore(), 80, 130);
	}
	
	public void show(String name) {
		card.show(this, name);
	}
}
