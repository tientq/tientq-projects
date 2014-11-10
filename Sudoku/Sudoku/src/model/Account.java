package model;

public class Account {
	private String user;
	private String password;
	private boolean manager;
	private boolean _continue;
	private int score;
	private int time;
	private int unlockLevel;
	private int idLevel;
	private String currentMap;
	private int difficult;
	private boolean showCandidate, showError, showLighting, showBlock, smartDigit, soundOn;
	public Account() {
		user = "guest";
		password = "";
		manager = false;
		_continue = false;
		score = 0;
		time = 0;
		unlockLevel = 1;
		idLevel = 1;
		currentMap = "";
		difficult = 0;
		showCandidate = false;
		showError = true;
		showLighting = true;
		showBlock = true;
		smartDigit = false;
		soundOn = true;
	}
	public int getUnlockLevel() {
		return unlockLevel;
	}
	public void setUnlockLevel(int unlockLevel) {
		this.unlockLevel = unlockLevel;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isManager() {
		return manager;
	}
	public void setManager(boolean manager) {
		this.manager = manager;
	}
	public boolean isContinue() {
		return _continue;
	}
	public void setContinue(boolean _continue) {
		this._continue = _continue;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public String getCurrentMap() {
		return currentMap;
	}
	public void setCurrentMap(String currentMap) {
		this.currentMap = currentMap;
	}
	public int getDifficult() {
		return difficult;
	}
	public void setDifficult(int difficult) {
		this.difficult = difficult;
	}
	public boolean isShowCandidate() {
		return showCandidate;
	}
	public void setShowCandidate(boolean showCandidate) {
		this.showCandidate = showCandidate;
	}
	public boolean isShowError() {
		return showError;
	}
	public void setShowError(boolean showError) {
		this.showError = showError;
	}
	public boolean isShowLighting() {
		return showLighting;
	}
	public void setShowLighting(boolean showLighting) {
		this.showLighting = showLighting;
	}
	public boolean isShowBlock() {
		return showBlock;
	}
	public void setShowBlock(boolean showBlock) {
		this.showBlock = showBlock;
	}
	public boolean isSmartDigit() {
		return smartDigit;
	}
	public void setSmartDigit(boolean smartDigit) {
		this.smartDigit = smartDigit;
	}
	public boolean isSoundOn() {
		return soundOn;
	}
	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
	}
}
