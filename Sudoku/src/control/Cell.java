package control;

public class Cell {
	private int value;
	private boolean rootCell;
	private boolean error;
	private boolean[] note;
	public Cell() {
		value = 0;
		rootCell = false;
		error = false;
		this.note = new boolean[10];
	}
	public void copyCell(Cell cell) {
		if (!this.isRootCell()) {
			this.value = cell.value;
			this.error = cell.error;
			this.note = new boolean[10];
			for (int i=0; i<10; i++) this.note[i] = cell.note[i];
		}
	}
	public void loadFrom(String s) {
		if (s.length() != 15) return;
		char c = s.charAt(0);
		if (c>='0' && c<='9') value = Character.digit(c, 10);
		c = s.charAt(1);
		rootCell = (c == '0')? false:true;
		c = s.charAt(2);
		error = (c == '0')? false:true;
		c = s.charAt(3);
		note[0] = (c == '0')? false:true;
		for (int i=1; i<10; i++) {
			c = s.charAt(3+i);
			note[i] = (c == '0')? false:true;
		}
	}
	public String toString() {
		String s = "";
		s = s+value+(rootCell?1:0)+(error?1:0)+(note[0]?1:0);
		for (int i=1; i<10; i++) s = s +  (note[i]?i:0);
		s = s + "  ";
		return s;
	}
	public boolean isRootCell() {
		return rootCell;
	}
	public void setRootCell(boolean rootCell) {
		this.rootCell = rootCell;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public boolean[] getNote() {
		return note;
	}
	public void setNote(boolean[] note) {
		this.note = note;
	}
	public int getValue() {
		if (this.isRootCell()) return value;
		if (note[0]) return 0;
		return value;
	}
	public void setValue(int value) {
		if (this.isRootCell()) return ;
		if (note[0]) this.setNote(value, true);
		else this.value = value;
	}
	public boolean setValue(int value, boolean note) {
		if (this.isRootCell()) return false;
		this.setNote(note);
		if (note) {
			if (value == 0)
				for (int i=0; i<10; i++) setNote(i, false);
			else this.setNote(value, !getNote(value));
		}
		else this.value = value;
		return true;
	}
	public boolean getNote(int index) {
		return note[index];
	}
	public boolean isNote() {
		return note[0];
	}
	public void setNote(boolean b) {
		note[0] = b;
	}
	public void setNote(int index, boolean b) {
		this.note[index] = b;
	}
	public void reset() {
		rootCell = false;
		value = 0;
		error = false;
		for (int i=0; i<10; i++) this.note[i] = false;
		return ;
	}
}
