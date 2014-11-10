package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import control.Sudoku;


public class LoginPanelAction implements ActionListener {
	private MyFrame frame;
	private LoginPanel login;
	private Sudoku sudoku;
	public LoginPanelAction(MyFrame frame, LoginPanel login, Sudoku sudoku) {
		this.frame = frame;
		this.login = login;
		this.sudoku = sudoku;
		login.butLogin.addActionListener(this);
		login.butRegister.addActionListener(this);
		login.butGuest.addActionListener(this);
		login.butExit.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login.butLogin) {
			if (sudoku.login(login.getUser(), login.getPass())) {
				sudoku.loadData(login.getUser());
				frame.show("SELECT");
				return ;
			}
			login.setStatus("Login Error");
			return;
		}
		if (e.getSource() == login.butRegister) {
			if (sudoku.register(login.getUser(), login.getPass())) {
				sudoku.loadData(login.getUser());
				frame.show("SELECT");
				return ;
			}
			login.setStatus("Register Error");
			return ;
		}
		if (e.getSource() == login.butGuest) {
			sudoku.loadData("guest");
			frame.show("SELECT");
			return ;
		}
		if (e.getSource() == login.butExit) {
			sudoku.exit();
			return ;
		}		
	}
}
