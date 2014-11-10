package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import control.Setting;
import control.Sudoku;

public class MenuPanelAction {
	private MyFrame frame;
	private MenuPanel menu;
	private Sudoku sudoku;
	public MenuPanelAction(MyFrame frame, MenuPanel menu, Sudoku sudoku) {
		this.frame = frame;
		this.menu = menu;
		this.sudoku = sudoku;
		addActionListener();
	}
	private void hiddenMenu() {
		menu.show("MENU");
		menu.setVisible(false);
	}
	private void addActionListener() {
		menu.butSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (frame.toString().equalsIgnoreCase("PLAY")) sudoku.saveData();
				sudoku.stop();
				hiddenMenu();
				sudoku.loadData(sudoku.getUser());
				frame.show("SELECT");
				return ;
			}
		});
		menu.butLogout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frame.toString().equalsIgnoreCase("PLAY")) sudoku.saveData();
				sudoku.stop();
				sudoku.loadData("guest");
				hiddenMenu();
				frame.show("LOGIN");
				return;
			}
		});
		menu.butSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menu.butCandidate.enable(Setting.isShowCandidate());
				menu.butError.enable(Setting.isShowError());
				menu.butLighting.enable(Setting.isShowLighting());
				menu.butBlock.enable(Setting.isShowBlock());
				menu.show("SETTING");
			}
		});
		menu.butHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menu.show("HELP");
			}
		});
		menu.butHighScore.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menu.v = sudoku.getHighScore();
				menu.show("HIGH SCORE");
			}
		});
		menu.butExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				sudoku.stop();
				if (frame.toString().equalsIgnoreCase("PLAY")) sudoku.saveAndExit();
				else sudoku.exit();
				return ;
			}
		});
		menu.butBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menu.show("MENU");
			}
		});
		menu.butBack2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menu.show("MENU");	
			}
		});
		menu.butBack3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				menu.show("MENU");
			}
		});
		menu.butCandidate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting.setShowCandidate(!Setting.isShowCandidate());
				menu.butCandidate.enable(Setting.isShowCandidate());
				sudoku.apply();
				frame.repaint();
			}
		});
		menu.butError.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting.setShowError(!Setting.isShowError());
				menu.butError.enable(Setting.isShowError());
				frame.repaint();
			}
		});
		menu.butLighting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting.setShowLighting(!Setting.isShowLighting());
				menu.butLighting.enable(Setting.isShowLighting());
				frame.repaint();
			}
		});
		menu.butBlock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Setting.setShowBlock(!Setting.isShowBlock());
				menu.butBlock.enable(Setting.isShowBlock());
				frame.repaint();
			}
		});
		menu.butSmartDigit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Setting.setSmartDigit(!Setting.isSmartDigit());
				menu.butSmartDigit.enable(Setting.isSmartDigit());
				sudoku.apply();
				frame.repaint();
			}
		});
		menu.butSound.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Setting.setSound(!Setting.isSound());
				menu.butSound.enable(Setting.isSound());
				sudoku.soundOnOff();
			}
		});
	}
}
