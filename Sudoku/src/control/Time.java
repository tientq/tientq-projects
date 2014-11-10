package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Time implements ActionListener{
	private Timer timer;
	private Component component;
	private Data data;
	public Time(Data data) {
		timer = new Timer(1000, this);
		this.data = data;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		data.setTime(data.getTime()+1);
		component.repaint();
	}
	public void start() {
		timer.start();
	}
	public void stop() {
		timer.stop();
	}
	public void setComponent(Component component) {
		this.component = component;
	}
}
