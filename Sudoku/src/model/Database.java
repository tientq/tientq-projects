package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

public class Database {
	private Connection con;

	public Database(Connection con) {
		this.con = con;
	}

	public static Connection creatConnection() {
		String url = "", user = "", password = "";
		FileReader file = null;
    	BufferedReader br = null;
    	try {
			file = new FileReader("data/database.cfg");
			br = new BufferedReader(file);
			url = br.readLine();
			user = br.readLine();
			password = br.readLine();
    	}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
    	finally {
    		try {
    			file.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean checkAccount(String user, String password) {
		boolean b = false;
		String sql = "SELECT user FROM `sudoku`.`account` WHERE user = '"
				+ user + "' and password = '" + password + "';";
		Statement state = null;
		ResultSet result = null;
		try {
			state = con.createStatement();
			result = state.executeQuery(sql);
			if (result.next())
				b = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				state.close();
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	public Account getAccount(String user) {
		Account acc = new Account();
		String sql = "SELECT * FROM `sudoku`.`account` WHERE user = '" + user
				+ "' ;";
		Statement getAccount = null;
		ResultSet result = null;
		try {
			getAccount = con.createStatement();
			result = getAccount.executeQuery(sql);
			if (result.next()) {
				acc.setUser(result.getString("user"));
				acc.setPassword("");
				acc.setManager(result.getBoolean("manager"));
				acc.setContinue(result.getBoolean("continue"));
				acc.setScore(result.getInt("score"));
				acc.setTime(result.getInt("time"));
				acc.setUnlockLevel(result.getInt("unlocklevel"));
				acc.setIdLevel(result.getInt("idlevel"));
				acc.setCurrentMap(result.getString("currentmap"));
				acc.setDifficult(result.getInt("difficult"));
				acc.setShowCandidate(result.getBoolean("candidate"));
				acc.setShowError(result.getBoolean("error"));
				acc.setShowLighting(result.getBoolean("lighting"));
				acc.setSmartDigit(result.getBoolean("smart"));
				acc.setSoundOn(result.getBoolean("sound"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				getAccount.close();
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return acc;
	}

	public boolean addAccount(Account acc) {
		boolean b = true;
		String sql = "INSERT INTO `sudoku`.`account` (`user`, `password`, `manager`, `continue`, `score`, `time`, `unlocklevel`, `idlevel`, `difficult`, `candidate`, `error`, `lighting`, `block`, `smart`, `sound`)"
				+ " VALUES ('"
				+ acc.getUser()
				+ "', '"
				+ acc.getPassword()
				+ "', "
				+ acc.isManager()
				+ ", "
				+ acc.isContinue()
				+ ", '"
				+ acc.getScore()
				+ "', '"
				+ acc.getTime()
				+ "', '"
				+ acc.getUnlockLevel()
				+ "', '"
				+ acc.getIdLevel()
				+ "', '"
				+ acc.getDifficult()
				+ "', "
				+ acc.isShowCandidate()
				+ ", "
				+ acc.isShowError()
				+ ", "
				+ acc.isShowLighting()
				+ ", "
				+ acc.isShowBlock()
				+ ", "
				+ acc.isSmartDigit()
				+ ", "
				+ acc.isSoundOn() + ");";
		Statement addAccount = null;
		try {
			addAccount = con.createStatement();
			addAccount.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			b = false;
		} finally {
			try {
				addAccount.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	public boolean updateAccount(Account acc) {
		boolean b = true;
		String sql = "UPDATE `sudoku`.`account` " + "SET `manager`="
				+ acc.isManager() + ", `continue`=" + acc.isContinue()
				+ " , `score`='" + acc.getScore() + "', `time`='"
				+ acc.getTime() + "', `unlocklevel`='" + acc.getUnlockLevel()
				+ "', `idlevel`='" + acc.getIdLevel() + "', `currentmap`='"
				+ acc.getCurrentMap() + "', `difficult`='" + acc.getDifficult()
				+ "', `candidate`=" + acc.isShowCandidate() + ", `error`="
				+ acc.isShowError() + ", `lighting`=" + acc.isShowLighting()
				+ ", `block`=" + acc.isShowBlock() + ", `smart`="
				+ acc.isSmartDigit() + ", `sound`=" + acc.isSoundOn()
				+ " WHERE `user`='" + acc.getUser() + "';";
		Statement addAccount = null;
		try {
			addAccount = con.createStatement();
			b = addAccount.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			b = false;
		} finally {
			try {
				addAccount.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}

	public LevelData getLevel(int id) {
		LevelData level = null;
		Statement getLevel = null;
		ResultSet results = null;
		try {
			getLevel = con.createStatement();
			String sql = "SELECT * FROM sudoku.level WHERE id = '" + id + "';";
			results = getLevel.executeQuery(sql);
			if (results.next()) {
				level = new LevelData();
				level.setId(results.getInt("id"));
				level.setLevel(results.getInt("level"));
				level.setDifficult(results.getInt("difficult"));
				level.setMap(results.getString("map"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				getLevel.close();
				results.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return level;
	}

	private Random r = new Random();

	public LevelData getLevel(int level, int difficult) {
		LevelData leveldata = null;
		Vector<LevelData> v = new Vector<LevelData>();
		Statement getLevel = null;
		ResultSet results = null;
		try {
			getLevel = con.createStatement();
			String sql = "SELECT * FROM sudoku.level WHERE level = '" + level
					+ "' and difficult = '" + difficult + "';";
			results = getLevel.executeQuery(sql);
			while (results.next()) {
				leveldata = new LevelData();
				leveldata.setId(results.getInt("id"));
				leveldata.setLevel(results.getInt("level"));
				leveldata.setDifficult(results.getInt("difficult"));
				leveldata.setMap(results.getString("map"));
				v.add(leveldata);
			}
			int i = v.size();
			if (i == 0) {
				leveldata = null;
			} else {
				i = r.nextInt(i);
				leveldata = v.get(i);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				getLevel.close();
				results.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return leveldata;
	}

	public boolean addLevel(LevelData lv) {
		boolean b = true;
		Statement addLevel = null;
		String sql = "INSERT INTO `sudoku`.`level` (`level`, `difficult`, `map`)" +
				" VALUES ('"+lv.getLevel()+"', '"+lv.getDifficult()+"', '"+lv.getMap()+"');";
		try {
			addLevel = con.createStatement();
			addLevel.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			b = false;
		} finally {
			try {
				addLevel.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	public Vector<String> getHighScore() {
		Vector<String> v = new Vector<String>();
		Statement getHighScore = null;
		ResultSet results = null;
		String sql = "SELECT user, score FROM `sudoku`.`account` ORDER BY score DESC;";
		try {
			getHighScore = con.createStatement();
			results = getHighScore.executeQuery(sql);
			int count = 0;
			while (results.next()) {
				String s = results.getString("user");
				v.addElement(s);
				s = ""+results.getInt("score");
				v.addElement(s);
				count++;
				if (count == 5) break;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				results.close();
				getHighScore.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return v;
	}
	public Vector<String> getListLevel(int difficult) {
		Vector<String> v = new Vector<String>();
		String sql = "SELECT DISTINCT level FROM `sudoku`.`level` WHERE difficult = '"+difficult+"' ORDER BY level ASC;";
		Statement getListLevel = null;
		ResultSet results = null;
		try {
			getListLevel = con.createStatement();
			results = getListLevel.executeQuery(sql);
			while (results.next()) {
				v.add(results.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				results.close();
				getListLevel.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return v;
	}
}
