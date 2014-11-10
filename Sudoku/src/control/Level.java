package control;

import model.LevelData;

public class Level {
	private int id;
	private int level;
	private int difficult;
	public int[][] map;

	public Level() {
		setId(0);
		setLevel(1);
		setDifficult(0);
		map = new int[9][9];
	}
	public void loadFrom(LevelData lv) {
		id = lv.getId();
		level = lv.getLevel();
		difficult = lv.getDifficult();
		String s = lv.getMap();
		char c = s.charAt(0);
		for (int row=0; row<9; row++)
			for (int col=0; col<9; col++) {
				c = s.charAt(9*row+col);
				map[row][col] = Character.digit(c, 10);
			}
	}
	public LevelData toLevelData() {
		LevelData lv = new LevelData();
		lv.setId(id);
		lv.setLevel(level);
		lv.setDifficult(difficult);
		String s = "";
		for (int row=0; row<9; row++)
			for (int col=0; col<9; col++)
				s += map[row][col];
		lv.setMap(s);
		return lv;
	}
	public int[][] getMap() {
		return this.map;
	}
	public boolean setMap(int[][] map) {
		this.map = map;
		return true;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDifficult() {
		return difficult;
	}

	public void setDifficult(int difficult) {
		this.difficult = difficult;
	}
	/**
	public void reset() {
		setId(0);
		setLevel(1);
		setDifficult(0);
		for (int i=0; i<9; i++)
			for (int j=0; j<9; j++)
				map[i][j] = 0;
		return ;
	}
	*/
}
