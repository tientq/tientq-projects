package control;

import java.awt.Component;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import model.Account;
import model.Database;
import model.LevelData;

import view.MyFrame;

public class Sudoku {
	private Database database;
	private Data data;
	private Time time;
	private Level easyLevel, normalLevel, hardLevel;
	private SoundWAV sound;
	/**
	 * hàm khởi tạo
	 */
	@SuppressWarnings("deprecation")
	public Sudoku() {
		Connection con = Database.creatConnection();
		if (con == null) {
			System.err.println("Can not connect to database");
			System.exit(0);
		}
		database = new Database(con);
		new Setting();
		data = new Data();
		time = new Time(data);
		sound = new SoundWAV("data/game.wav");
		sound.start();
		if (!Setting.isSound()) sound.suspend();
		loadData("guest");
		easyLevel = new Level();
		normalLevel = new Level();
		hardLevel = new Level();
		LevelData lv = database.getLevel(1, 0);
		if (lv != null) easyLevel.loadFrom(lv);
		lv = database.getLevel(1, 1);
		if (lv != null) normalLevel.loadFrom(lv);
		lv = database.getLevel(1, 2);
		if (lv != null) hardLevel.loadFrom(lv);
	}
	/**
	 * hàm main
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Sudoku sudoku = new Sudoku();
			MyFrame fr = new MyFrame(sudoku);
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.invokeLater(fr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Đóng database chương trình
	 */
	@SuppressWarnings("deprecation")
	public void close() {
		database.close();
		sound.stop();
	}
	/**
	 * có thể đăng nhập hay không
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean login(String user, String password) {
		return database.checkAccount(user, password);
	}
	/**
	 * có thể đăng ký hay không
	 * @param user
	 * @param password
	 * @return
	 */
	public boolean register(String user, String password) {
		if (user.length() == 0) return false;
		Account acc = new Account();
		acc.setUser(user);
		acc.setPassword(password);
		return database.addAccount(acc);
	}
	/**
	 * nạp dữ liệu người chơi
	 * @param user
	 */
	public void loadData(String user) {
		Account acc = database.getAccount(user);
		// update user setting
		Setting.setShowCandidate(acc.isShowCandidate());
		Setting.setShowError(acc.isShowError());
		Setting.setShowLighting(acc.isShowLighting());
		Setting.setShowBlock(acc.isShowBlock());
		Setting.setSmartDigit(acc.isSmartDigit());
		Setting.setSound(acc.isSoundOn());
		// update user data
		data.setUser(acc.getUser());
		data.setManager(acc.isManager());
		data.setContinue(acc.isContinue());
		data.setScore(acc.getScore());
		data.setTime(acc.getTime());
		data.setUnlockLevel(acc.getUnlockLevel());
		data.setIdLevel(acc.getIdLevel());
		data.setDifficult(acc.getDifficult());
		String s = acc.getCurrentMap();
		if (s == null) return;
		if (s.length() == 1215)
			for (int i=0; i<81; i++) {
				int row = i/9;
				int col = i%9;
				String sub = s.substring(15*i, 15*i+15);
				data.map[row][col].loadFrom(sub);
			}
	}
	/**
	 * lưu dữ liệu người chơi
	 */
	public void saveData() {
		Account acc = new Account();
		// save setting
		acc.setShowCandidate(Setting.isShowCandidate());
		acc.setShowError(Setting.isShowError());
		acc.setShowLighting(Setting.isShowLighting());
		acc.setShowBlock(Setting.isShowBlock());
		acc.setSmartDigit(Setting.isSmartDigit());
		acc.setSoundOn(Setting.isSound());
		// save data
		acc.setUser(data.getUser());
		acc.setManager(data.isManager());
		acc.setContinue(data.isContinue());
		acc.setScore(data.getScore());
		acc.setTime(data.getTime());
		acc.setUnlockLevel(data.getUnlockLevel());
		acc.setIdLevel(data.getIdLevel());
		acc.setDifficult(data.getDifficult());
		String s = "";
		for (int i=0; i<81; i++) {
			int row = i/9;
			int col = i%9;
			s += data.map[row][col].toString();
		}
		acc.setCurrentMap(s);
		database.updateAccount(acc);
	}
	/**
	 * tên người chơi
	 * @return
	 */
	public String getUser() {
		return data.getUser();
	}
	/**
	 * có thế tiếp tục chơi level trước đó không
	 * @return
	 */
	public boolean isContinue() {
		return data.isContinue();
	}
	/**
	 * có phải quản trị viên không
	 * @return
	 */
	public boolean isManager() {
		return data.isManager();
	}
	/**
	 * lấy điểm
	 * @return
	 */
	public int getScore() {
		return data.getScore();
	}
	/**
	 * lấy thời gian
	 * @return
	 */
	public int getTime() {
		return data.getTime();
	}
	/**
	 * lấy bản đồ
	 * @return
	 */
	public Cell[][] getMap() {
		return data.map;
	}
	/**
	 * lấy level tiếp tục chơi
	 * @return
	 */
	public Level getContinueLeveL() {
		Level level = new Level();
		if (!isContinue()) return level;
		LevelData lv = database.getLevel(data.getIdLevel());
		if (lv != null) level.loadFrom(lv); 
		return level;
	}
	/**
	 * lấy màn chơi dễ
	 * @return
	 */
	public Level getEasyLevel() {
		return easyLevel;
	}
	/**
	 * lấy màn chơi trung bình
	 * @return
	 */
	public Level getNormalLevel() {
		return normalLevel;
	}
	/**
	 * lấy màn chơi khó
	 * @return
	 */
	public Level getHardLevel() {
		return hardLevel;
	}
	/**
	 * có thể chơi màn chơi dễ không
	 * @return
	 */
	public boolean isLockEasyLevel() {
		return data.getUnlockLevel() < easyLevel.getLevel();
	}
	/**
	 * có thể chơi màn trung bình không
	 * @return
	 */
	public boolean isLockNormalLevel() {
		return data.getUnlockLevel() < normalLevel.getLevel();
	}
	/**
	 * có thể chơi màn chơi khó không
	 * @return
	 */
	public boolean isLockHardLevel() {
		return data.getUnlockLevel() < hardLevel.getLevel();
	}
	/**
	 * khi người dùng bấm chơi continue Level
	 */
	public void playContinue() {
		loadData(getUser());
		apply();
		time.start();
	}
	/**
	 * Khi người dùng bấm find Result
	 */
	public void findResult() {
		data.clearMap();
	}
	/**
	 * Khi người dùng bấm clear map
	 */
	public void createLevel() {
		data.clearMap();
	}
	/**
	 * Khi người dùng bấm chơi easyLevel
	 */
	public void playEasy() {
		data.loadFrom(easyLevel);
		data.setContinue(true);
		apply();
		data.setTime(0);
		time.start();
	}
	/**
	 * khi người dùng bấm chơi Normal Level
	 */
	public void playNormal() {
		data.loadFrom(normalLevel);
		data.setContinue(true);
		apply();
		data.setTime(0);
		time.start();
	}
	/**
	 * Khi người dùng bấm chơi Hard Level
	 */
	public void playHard() {
		data.loadFrom(hardLevel);
		data.setContinue(true);
		apply();
		data.setTime(0);
		time.start();
	}
	/**
	 * Khi bấm previous level
	 */
	public void previousLevel() {
		int i = easyLevel.getLevel()-1;
		LevelData lv = database.getLevel(i, 0);
		if (lv != null) easyLevel.loadFrom(lv);
		i = normalLevel.getLevel()-1;
		lv = database.getLevel(i, 1);
		if (lv != null) normalLevel.loadFrom(lv);
		i = hardLevel.getLevel() - 1;
		lv = database.getLevel(i, 2);
		if (lv != null) hardLevel.loadFrom(lv);
	}
	/**
	 * Khi bấm next level
	 */
	public void nextLevel() {
		int i = easyLevel.getLevel()+1;
		LevelData lv = database.getLevel(i, 0);
		if (lv != null) easyLevel.loadFrom(lv);
		i = normalLevel.getLevel()+1;
		lv = database.getLevel(i, 1);
		if (lv != null) normalLevel.loadFrom(lv);
		i = hardLevel.getLevel()+1;
		lv = database.getLevel(i, 2);
		if (lv != null) hardLevel.loadFrom(lv);
	}
	/**
	 * khi bấm randomlevel
	 */
	public void randomLevel() {
		int i = easyLevel.getLevel();
		LevelData lv = database.getLevel(i, 0);
		if (lv != null) easyLevel.loadFrom(lv);
		i = normalLevel.getLevel();
		lv = database.getLevel(i, 1);
		if (lv != null) normalLevel.loadFrom(lv);
		i = hardLevel.getLevel();
		lv = database.getLevel(i, 2);
		if (lv != null) hardLevel.loadFrom(lv);
	}
	/**
	 * cập nhật lại
	 */
	public void apply() {
		if (Setting.isShowCandidate() || Setting.isShowBlock()) {
			data.candidate();
		}
	}
	/**
	 * thoát
	 */
	public void exit() {
		close();
		System.exit(0);
	}
	/**
	 * lưu và thoát
	 */
	public void saveAndExit() {
		saveData();
		close();
		System.exit(0);
	}
	/**
	 * gán giá trị v cho hàng row cột column
	 * @param v
	 * @param row
	 * @param column
	 */
	public void setValue(int v, int row, int column) {
		data.setValue(v, row, column, Setting.isNote(), Setting.isShowCandidate()||Setting.isSmartDigit());
	}
	/**
	 * Khi người dùng Click đúp chuột hoặc bấm phím A ở chế độ Candidate thì sẽ tự động điền
	 * @param row
	 * @param column
	 */
	public void doubleClick(int row, int column) {
		if (Setting.isShowCandidate()) data.doubleClick(row, column);
	}
	/**
	 * có thể undo không
	 * @return
	 */
	public boolean isUndo() {
		return data.isUndo();
	}
	/**
	 * có thể redo không
	 * @return
	 */
	public boolean isRedo() {
		return data.isRedo();
	}
	/**
	 * thực hiện undo
	 */
	public void undo() {
		data.undo();
		if (Setting.isShowCandidate()) {
			data.candidate();
		}
	}
	/**
	 * thực hiện redo
	 */
	public void redo() {
		data.redo();
		if (Setting.isShowCandidate()) {
			data.candidate();
		}
	}
	/**
	 * đã kết thúc màn chơi chưa
	 * @return
	 */
	public boolean isWin() {
		return data.isWin();
	}
	/**
	 * chiến thắng và lưu lại
	 */
	public void winAndEndGame() {
		data.clearMap();
		data.setContinue(false);
		int score = data.getScore();
		score += (data.getDifficult()+1)*10000-data.getTime();
		data.setScore(score);
		data.setTime(0);
		Level level = new Level();
		LevelData lv = database.getLevel(data.getIdLevel());
		if (lv == null) return;
		level.loadFrom(lv);
		if (data.getUnlockLevel()<=level.getLevel()) data.setUnlockLevel(level.getLevel()+1);
	}
	/**
	 * chơi level tiếp theo
	 * @return
	 */
	public boolean playNextLevel() {
		Level level = new Level();
		LevelData lv = database.getLevel(data.getIdLevel());
		if (lv == null) return false;
		level.loadFrom(lv);
		lv = database.getLevel(level.getLevel()+1, level.getDifficult());
		if (lv == null) return false;
		level.loadFrom(lv);
		data.loadFrom(level);
		data.setContinue(true);
		int score = data.getScore();
		score += data.getDifficult()*10000-data.getTime();
		data.setScore(score);
		data.setTime(0);
		if (data.getUnlockLevel()<level.getLevel()) data.setUnlockLevel(level.getLevel());
		apply();
		return true;
	}
	/**
	 * gán phần hiển thị cho Time
	 * @param component
	 */
	public void setViewTime(Component component) {
		time.setComponent(component);
	}
	/**
	 * dừng đếm thời gian
	 */
	public void stop() {
		time.stop();
	}
	/**
	 * tiếp tục đếm thời gian
	 */
	public void start() {
		time.start();
	}
	/**
	 * tự động tìm kết quả
	 * @return
	 */
	public boolean autoFindResult() {
		int[][] map = new int[9][9];
		for (int row=0; row<9; row++)
			for (int col=0; col<9; col++) {
				map[row][col] = data.map[row][col].getValue();
			}
		CreateAndResult createAndResult = new CreateAndResult();
		boolean b = createAndResult.findResult(map);
		if (b) {
			for (int row=0; row<9; row++)
				for (int col=0; col<9; col++) {
					data.map[row][col].reset();
					data.map[row][col].setValue(map[row][col]);
				}
		}
		return b;		
	}
	/**
	 * tự động sinh level
	 * @param count
	 * @return
	 */
	public boolean autoCreatLevel(int count) {
		
		int[][] map = new int[9][9];
		for (int row=0; row<9; row++)
			for (int col=0; col<9; col++) {
				map[row][col] = data.map[row][col].getValue();
			}
		CreateAndResult createAndResult = new CreateAndResult();
		boolean b = createAndResult.createMap(map, count);
		if (b) {
			for (int row=0; row<9; row++)
				for (int col=0; col<9; col++) {
					data.map[row][col].reset();
					data.map[row][col].setValue(map[row][col]);
				}
		}
		return b;
	}
	/**
	 * lưu 1 level vào csdl
	 * @param level
	 * @param difficult
	 */
	public void addLevel(int level, int difficult) {
		LevelData lv = new LevelData();
		lv.setLevel(level);
		lv.setDifficult(difficult);
		String s = "";
		for (int row=0; row<9; row++)
			for (int col=0; col<9; col++)
				s += data.map[row][col].getValue();
		lv.setMap(s);
		database.addLevel(lv);
	}
	/**
	 * lấy điểm cao
	 * @return
	 */
	public Vector<String> getHighScore() {
		return database.getHighScore();
	}
	/**
	 * bật tắt âm thanh
	 * @param b
	 */
	@SuppressWarnings("deprecation")
	public void soundOnOff() {
		if (!Setting.isSound()) {
			if (sound.isAlive()) sound.suspend();
		} else sound.resume();
	}
	public Vector<String> getListLevel(int difficult) {
		return database.getListLevel(difficult);
	}
}