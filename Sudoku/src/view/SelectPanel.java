package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import control.Level;
import control.Sudoku;

@SuppressWarnings("serial")
public class SelectPanel extends JPanel {
	private Font FONT = new Font(null, Font.BOLD, 25);
	private int xBox, yBox;
	private Sudoku sudoku;
	private Level continueLevel, easyLevel, normalLevel, hardLevel;
	protected ViewLevel viewContinue, viewResult, viewCreate, viewEasy,
			viewNormal, viewHard;
	protected MyButton butPrevious, butNext, butRandom1, butRandom2;
	private JLabel lblEasyLevel, lblNormalLevel, lblHardLevel, lblContinueLevel;

	public SelectPanel(Sudoku sudoku) {
		super();
		this.setLayout(null);
		xBox = MyFrame.getScreenWidth() / 2 - 525;
		yBox = MyFrame.getScreenHeight() / 2 - 350;
		this.sudoku = sudoku;
		this.init();
	}

	public void init() {
		continueLevel = sudoku.getContinueLeveL();
		easyLevel = sudoku.getEasyLevel();
		normalLevel = sudoku.getNormalLevel();
		hardLevel = sudoku.getHardLevel();
		lblEasyLevel = new JLabel("Level "+easyLevel.getLevel(), JLabel.CENTER);
		lblEasyLevel.setBounds(MyFrame.getScreenWidth() / 2 - 450,
				MyFrame.getScreenHeight() / 2 - 145, 200, 80);
		lblEasyLevel.setFont(FONT);
		lblEasyLevel.setForeground(Color.WHITE);
		this.add(lblEasyLevel);
		JLabel lblLevelIcon1 = new JLabel(new ImageIcon("images/level.png"));
		lblLevelIcon1.setBounds(MyFrame.getScreenWidth() / 2 - 425,
				MyFrame.getScreenHeight() / 2 - 145, 150, 150);
		this.add(lblLevelIcon1);
		lblNormalLevel = new JLabel("Level " + normalLevel.getLevel(), JLabel.CENTER);
		lblNormalLevel.setBounds(MyFrame.getScreenWidth() / 2 - 100,
				MyFrame.getScreenHeight() / 2 - 145, 200, 80);
		lblNormalLevel.setFont(FONT);
		lblNormalLevel.setForeground(Color.WHITE);
		this.add(lblNormalLevel);
		JLabel lblLevelIcon2 = new JLabel(new ImageIcon("images/level.png"));
		lblLevelIcon2.setBounds(MyFrame.getScreenWidth() / 2 - 75,
				MyFrame.getScreenHeight() / 2 - 145, 150, 150);
		this.add(lblLevelIcon2);
		lblHardLevel = new JLabel("Level "+hardLevel.getLevel(), JLabel.CENTER);
		lblHardLevel.setBounds(MyFrame.getScreenWidth() / 2 +250,
				MyFrame.getScreenHeight() / 2 - 145, 200, 80);
		lblHardLevel.setFont(FONT);
		lblHardLevel.setForeground(Color.WHITE);
		this.add(lblHardLevel);
		JLabel lblLevelIcon3 = new JLabel(new ImageIcon("images/level.png"));
		lblLevelIcon3.setBounds(MyFrame.getScreenWidth() / 2 + 275,
				MyFrame.getScreenHeight() / 2 - 145, 150, 150);
		this.add(lblLevelIcon3);
		lblContinueLevel = new JLabel() {
			public void paintComponent(Graphics g) {
				if (sudoku.isContinue()) {
					String difficult[] = { " Easy", " Normal", " Hard" };
					String text = "Level " + continueLevel.getLevel()
							+ difficult[continueLevel.getDifficult()];
					g.drawImage(new ImageIcon("images/continuelevel.png")
							.getImage(), 0, 0, this);
					g.setFont(new Font(null, Font.BOLD + Font.ITALIC, 20));
					g.setColor(Color.WHITE);
					g.drawString(text, 80, 85);
				}
			}
		};
		lblContinueLevel.setBounds(xBox + 370, yBox + 60, 230, 150);
		this.add(lblContinueLevel);
		viewContinue = new ViewLevel("Continue Level", "continue",
				"continue level of user", xBox, yBox);
		viewResult = new ViewLevel("Find Result", "result",
				"find result of sudoku from user", xBox + 350, yBox);
		viewCreate = new ViewLevel("Creat Level", "create",
				"create new level, only manager can use", xBox + 700, yBox);
		viewEasy = new ViewLevel("Play Easy Level", "easy", "play easy level",
				xBox, yBox + 350);
		viewNormal = new ViewLevel("Play Normel Level", "normal",
				"play normal level", xBox + 350, yBox + 350);
		viewHard = new ViewLevel("Play Hard Level", "hard", "play hard level",
				xBox + 700, yBox + 350);
		viewContinue.setMap(continueLevel.getMap());
		viewEasy.setMap(easyLevel.getMap());
		viewNormal.setMap(normalLevel.getMap());
		viewHard.setMap(hardLevel.getMap());
		if (sudoku.isContinue() == false)
			viewContinue.setLock(true);
		if (sudoku.isManager() == false)
			viewCreate.setLock(true);
		if (sudoku.isLockEasyLevel())
			viewEasy.setLock(true);
		if (sudoku.isLockNormalLevel())
			viewNormal.setLock(true);
		if (sudoku.isLockHardLevel())
			viewEasy.setLock(true);
		butPrevious = new MyButton("previouslevel", "previous level",
				xBox - 105, yBox + 400, 100, 100);
		butNext = new MyButton("nextlevel", "next level", xBox + 1055,
				yBox + 400, 100, 100);
		butRandom1 = new MyButton("randomlevel", "random an other level",
				xBox - 105, yBox + 550, 100, 100);
		butRandom2 = new MyButton("randomlevel", "random an other level",
				xBox + 1055, yBox + 550, 100, 100);
		this.add(butPrevious);
		this.add(butNext);
		this.add(butRandom1);
		this.add(butRandom2);
		this.add(viewContinue);
		this.add(viewResult);
		this.add(viewCreate);
		this.add(viewEasy);
		this.add(viewNormal);
		this.add(viewHard);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image bg = new ImageIcon("images/bg0.png").getImage();
		for (int y = 0; y < MyFrame.getScreenHeight(); y += 100)
			for (int x = 0; x < MyFrame.getScreenWidth(); x += 100) {
				g.drawImage(bg, x, y, this);
			}
	}
	@Override
	public void repaint() {
		super.repaint();
		if (sudoku == null) return;
		continueLevel = sudoku.getContinueLeveL();
		viewContinue.setMap(continueLevel.getMap());
		viewContinue.setLock(!sudoku.isContinue());
		viewCreate.setLock(!sudoku.isManager());
		viewEasy.setLock(sudoku.isLockEasyLevel());
		viewNormal.setLock(sudoku.isLockNormalLevel());
		viewHard.setLock(sudoku.isLockHardLevel());
		lblEasyLevel.setText("Level "+easyLevel.getLevel());
		lblNormalLevel.setText("Level "+normalLevel.getLevel());
		lblHardLevel.setText("Level "+hardLevel.getLevel());
		viewContinue.repaint();
		viewResult.repaint();
		viewCreate.repaint();
		viewEasy.repaint();
		viewNormal.repaint();
		viewHard.repaint();
	}
}
