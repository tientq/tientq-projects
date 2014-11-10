package control;

/**
 * Lưu trữ các dữ liệu lên quan đến người chơi sử dụng trong chương trình
 * @param user kiểu String chứa tên người chơi
 * @param isManager kiểu boolean, là true nếu là quản trị viên, false nếu là người chơi bình thường
 * @param isContinue kiểu boolean, là true nếu có màn chơi tiếp tục, false nếu không có (người chơi mới)
 * @param score kiểu int, số điểm hiện tại của người chơi
 * @author Quang Tien Pro
 */
public class Data {
	private String user;
	private boolean isManager;
	private boolean isContinue;
	private int score;
	private int time;
	private int unlockLevel;
	public Cell[][] map;
	private int idLevel;
	private int difficult;
	private Node current;
	public Data() {
		this.setUser("guest");
		this.setManager(false);
		this.setContinue(false);
		this.setScore(0);
		this.setTime(0);
		this.setUnlockLevel(1);
		this.map = new Cell[9][9];
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				this.map[i][j] = new Cell();
		this.setIdLevel(1);
		this.setDifficult(0);
		current = new Node(new Cell(), 0, 0);
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}

	public int getUnlockLevel() {
		return unlockLevel;
	}

	public void setUnlockLevel(int unlockLevel) {
		this.unlockLevel = unlockLevel;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getIdLevel() {
		return idLevel;
	}

	public void setIdLevel(int idLevel) {
		this.idLevel = idLevel;
	}

	public int getDifficult() {
		return difficult;
	}

	public void setDifficult(int difficult) {
		this.difficult = difficult;
	}
	public boolean isUndo() {
		if (current.prev == null) return false;
		return true;
	}
	public boolean isRedo() {
		if (current.next == null) return false;
		return true;
	}
	private void addNode(Cell cell, int row, int column) {
		Node temp = new Node(cell, row, column);
		if (current.next != null) current.next.prev = null;
		temp.prev = current;
		current.next = temp;
		current = current.next;
	}
	public int[] undo() {
		int[] u = new int[4];
		u[0] = current.row;
		u[1] = current.column;
		if (isUndo()) {
			Node temp = current.prev;
			map[u[0]][u[1]].copyCell(new Cell());
			while (temp.prev != null) {
				if (temp.row == u[0] && temp.column == u[1]) {
					map[u[0]][u[1]].copyCell(temp.cell);
					break;
				}
				temp = temp.prev;
			}
			current = current.prev;
		}
		u[2] = current.row;
		u[3] = current.column;
		return u;
	}
	public int[] redo() {
		int[] r = new int[4];
		r[0] = current.row;
		r[1] = current.column;
		
		if (isRedo()) {
			Node temp = current;
			map[r[0]][r[1]].copyCell(new Cell());
			while (temp.next != null) {
				if (temp.row == r[0] && temp.column == r[1]) {
					map[r[0]][r[1]].copyCell(temp.cell);
					break;
				}
				temp = temp.next;
			}
			current = current.next;
		}
		r[2] = current.row;
		r[3] = current.column;
		map[r[2]][r[3]].copyCell(current.cell);
		return r;
	}
	public void clearMap() {
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				map[i][j].reset();
	}
	public void loadFrom(Level myLevel) {
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++) {
				map[i][j].reset();
				if (myLevel.map[i][j] >0 && myLevel.map[i][j] <10) {
					map[i][j].setValue(myLevel.map[i][j]);
					map[i][j].setRootCell(true);
				}
			}
		setIdLevel(myLevel.getId());
		setDifficult(myLevel.getDifficult());
		current = new Node(new Cell(), 0, 0);
	}
	private void checkCell(int row, int column) {
		int v = map[row][column].getValue();
		if (v==0) {
			map[row][column].setError(false);
			return ;
		}
		for (int i=0; i<9; i++) {
			if (map[i][column].getValue() == v && i != row) {
				map[row][column].setError(true);
				return ;
			}
			if (map[row][i].getValue() == v && i != column) {
				map[row][column].setError(true);
				return ;
			}
		}
		
		int d1 = row/3*3, d2 = column/3*3;
		for (int i=d1; i<d1+3; i++)
			for (int j=d2; j<d2+3; j++) {
				if (i != row && j != column) {
					if (map[i][j].getValue() == v) {
						map[row][column].setError(true);
						return ;
					}
				}
			}
		
		map[row][column].setError(false);
		return ;
	}
	public void candidate() {
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++) {
				if (!map[i][j].isNote() || map[i][j].getValue() != 0)  
					for (int k=1; k<10; k++) map[i][j].setNote(k, true);
			}
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++) {
				int v = map[i][j].getValue();
				if (v != 0) {
					for (int k=0; k<9; k++)	{
						if (k!=j && !map[i][k].isNote()) map[i][k].setNote(v, false);
						if (k!=i && !map[k][j].isNote()) map[k][j].setNote(v, false);
					}
					int d1 = i/3*3, d2 = j/3*3;
					for (int m=d1; m<d1+3; m++)
						for (int n=d2; n<d2+3; n++) {
							if (m != i && n != j && !map[m][n].isNote()) {
								map[m][n].setNote(v, false);
							}
						}
				}
			}
		for (int row=0; row<9; row+=3)
			for (int col=0; col<9; col+=3) {
				loop:
				for (int val=1; val<=9; val++) {
					int count = 0, x = 0,y = 0;
					for (int i=row; i<row+3; i++)
						for (int j=col; j<col+3; j++) {
							if (map[i][j].isNote()) break loop;
							if (map[i][j].getValue() == 0 && map[i][j].getNote(val)) {
								count++;
								x = i; y=j;
							}
						}
					if (count==1) {
						for (int index=1; index<10; index++) map[x][y].setNote(index, false);
						map[x][y].setNote(val, true);
					}
				}
			}
	}
	public void setValue(int v, int row, int column, boolean note, boolean candidate) {
		if (!isUndo() && !isRedo()) addNode(map[row][column], row, column);
		boolean b = map[row][column].setValue(v, note);
		checkCell(row, column);
		if (b) addNode(map[row][column], row, column);
		if (candidate) candidate();
	}
	public void doubleClick(int row, int column) {
		candidate();
		if (!map[row][column].isNote()) {
			int count = 0;
			int index = 0;
			boolean[] note = map[row][column].getNote();
			for (int i=1; i<10; i++) 
				if (note[i]) {
					count++;
					index = i;
				}
			if (count == 1) setValue(index, row, column, false, true);
		}
	}
	public boolean isWin() {
		for (int row=0; row<9; row++)
			for (int col=0; col<9; col++)
				if (map[row][col].getValue() == 0) return false;
		for (int i=0; i<9; i++) {
			boolean[] rowCheck = new boolean[10];
			boolean[] colCheck = new boolean[10];
			int v = 0;
			for (int j=0; j<9; j++) {
				v = map[i][j].getValue();
				if (rowCheck[v]) return false;
				else rowCheck[v] = true;
				v = map[j][i].getValue();
				if (colCheck[v]) return false;
				else colCheck[v] = true;
			}
		}
		for (int row=0; row<9; row+=3)
			for (int col=0; col<9; col+=3) {
				boolean[] check = new boolean[10];
				int v = 0;
				for (int i=row; i<row+3; i++)
					for (int j=col; j<col+3; j++) {
						v = map[i][j].getValue();
						if (check[v]) return false;
						else check[v] = true;
					}
			}
		return true;
	}
}
