package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;


import control.Sudoku;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements Runnable {
	private static int screenWidth;
	private static int screenHeight;
	public static final Image BG = new ImageIcon("images/bg0.png").getImage();
	private Sudoku sudoku;
	private JLayeredPane jlp;
	private JPanel mainPanel;
	private CardLayout card;
	private JPanel loadPanel;
	private LoginPanel loginPanel;
	private SelectPanel selectPanel;
	private PlayPanel playPanel;
	private ResultPanel resultPanel;
	private CreatePanel createPanel;
	private WinPanel winPanel;
	private JPanel pausePanel;
	private MenuPanel menuPanel;
	private JButton butMenu;
	public MyFrame(Sudoku sudoku) {
		super();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		this.setSize(getScreenWidth(), getScreenHeight());
		this.setTitle("AutoCreateMap Sudoku");
		this.setUndecorated(true);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.sudoku = sudoku;
		mainPanel = new JPanel();
		card = new CardLayout();
		mainPanel.setLayout(card);
		mainPanel.setBounds(0, 0, getScreenWidth(), getScreenHeight());
		JRootPane jrp = this.getRootPane();
		jlp = jrp.getLayeredPane();
		jlp.add(mainPanel);
		jlp.setLayer(mainPanel, JLayeredPane.PALETTE_LAYER);

		loadPanel = new JPanel(new BorderLayout());
		JLabel lblLoad = new JLabel("Loading SUDOKU", JLabel.CENTER);
		lblLoad.setFont(new Font(null, Font.BOLD, 50));
		lblLoad.setForeground(Color.ORANGE);
		loadPanel.setBackground(Color.BLACK);
		loadPanel.add(lblLoad, BorderLayout.CENTER);
		mainPanel.add(loadPanel, "LOAD");
		this.setVisible(true);
	}

	public void init() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				sudoku.exit();
			}
		});
		loginPanel = new LoginPanel();
		selectPanel = new SelectPanel(sudoku);
		playPanel = new PlayPanel(sudoku, this);
		resultPanel = new ResultPanel(sudoku);
		createPanel = new CreatePanel(sudoku);
		winPanel = new WinPanel(sudoku, this);
		pausePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				for (int x = 0; x < MyFrame.getScreenWidth(); x += 100)
					for (int y = 0; y < MyFrame.getScreenHeight(); y += 100) {
						g.drawImage(BG, x, y, this);
					}
				g.setColor(Color.WHITE);
				g.setFont(new Font(null, 0, 60));
				g.drawString("Click to continue", screenWidth/2-250, screenHeight/2);
			}
		};
		pausePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				show("PLAY");
				sudoku.start();
			}
		});
		
		mainPanel.add(loginPanel, "LOGIN");
		mainPanel.add(selectPanel, "SELECT");
		mainPanel.add(playPanel, "PLAY");
		mainPanel.add(resultPanel, "RESULT");
		mainPanel.add(createPanel, "CREATE");
		mainPanel.add(winPanel, "WIN");
		mainPanel.add(pausePanel, "PAUSE");
		
		menuPanel = new MenuPanel(sudoku);
		jlp.add(menuPanel);
		jlp.setLayer(menuPanel, JLayeredPane.POPUP_LAYER);
		menuPanel.setVisible(false);
		
		new SelectPanelAction(this, selectPanel, sudoku);
		new LoginPanelAction(this, loginPanel, sudoku);
		new MenuPanelAction(this, menuPanel, sudoku);
		
		butMenu = new JButton(new ImageIcon("images/menu.png"));
		butMenu.setBounds(screenWidth-50, 0, 50, 50);
		butMenu.setContentAreaFilled(false);
		butMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menuPanel.setVisible(!menuPanel.isVisible());
				menuPanel.show("MENU");
			}
		});
		butMenu.addKeyListener(playPanel);
		jlp.add(butMenu, JLayeredPane.MODAL_LAYER);
		
		addKeyListener(playPanel.getKeyListeners()[0]);
		setFocusable(true);
		card.show(mainPanel, "LOGIN");
	}

	public void run() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		gd.setFullScreenWindow(this);
		init();
	}
	
	public static int getScreenWidth() {
		return screenWidth;
	}
	public static int getScreenHeight() {
		return screenHeight;
	}
	String currentPanel = "LOGIN";
	@Override
	public String toString() {
		return currentPanel;
	}
	public void show(String namePanel) {
		card.show(mainPanel, namePanel);
		currentPanel = namePanel;
	}
}
