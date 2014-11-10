package model;

public class LevelData {
	private int id;
	private int level;
	private int difficult;
	private String map;
	public LevelData() {
		
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
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		if (map.length() == 81) this.map = map;
	}
}
