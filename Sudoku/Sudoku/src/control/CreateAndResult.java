package control;

//import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//import model.Database;
//import model.LevelData;

public class CreateAndResult {
    private int[][] solution;       // màn chơi hoàn thiện đã điền hết các ô
    private int[][] game;           // lưu màn chơi đã tạo
    private int[][] map;			// ghi nhớ map
    private int count = 0;			// số ô muốn giữ lại
    /**
     * hàm khởi tao
     */
    public CreateAndResult() {
    	this.solution = new int[9][9];
        this.map = new int[9][9];
        this.game = new int[9][9];
    }
    /**
    public static void main(String[] args) {
    	// tao them level vao csdl
    	int levelstart = 21;
    	int count = 600; // tong so level
    	int creat = 30; // so level se tao cho moi level
    	int dif = creat/3; // so level se tao cho moi muc do
    	int[] get = {35, 27, 0}; // so o muon giu lai cho moi level
		Connection con = Database.creatConnection();
		Database database = new Database(con);
		CreateAndResult createAndResult = new CreateAndResult();
		for (int i=0; i<count; i++) {
			int[][] map = new int[9][9];
			LevelData lv = new LevelData();
			lv.setLevel(levelstart+i/creat);
			lv.setDifficult((i/dif)%3);
			boolean b = createAndResult.createMap(map, get[lv.getDifficult()]);
			System.out.println(b);
			createAndResult.print(map);
			String s = "";
			for (int row=0; row<9; row++)
				for (int col=0; col<9; col++)
					s += map[row][col];
			lv.setMap(s);
			database.addLevel(lv);
		}
		database.close();
	}
    */
    public boolean findResult(int[][] map) {
    	this.map = copy(map);
    	solution = generateSolution(this.map, 0);
    	if (solution == null) return false;
    	for (int i=0; i<9; i++)
        	for (int j=0; j<9; j++)
        		map[i][j] = solution[i][j];
        return true;
    }
    /**
     * tạo 1 map với map đầu vào là map và số ô muốn giữ lại (nếu count=0 thì giữ lại ít nhất có thể)
     * @param map
     * @param count
     * @return
     */
    public boolean createMap(int[][] map, int count) {
    	// khởi tạo 1 màn chơi điền đầy đủ các số như 1 màn chơi đã hoàn thiện
    	this.map = copy(map);
    	this.count = count;
        solution = generateSolution(map, 0);
        if (solution == null) return false; 
        // sao chép 1 mảng mới từ mảng màn chơi đầy đủ và bỏ các ô có thể tạo thành mảng game
        game = generateGame(copy(solution));
        for (int i=0; i<9; i++)
        	for (int j=0; j<9; j++)
        		map[i][j] = game[i][j];
        return true;
    }
    /**
     * Generates a new Sudoku game.<br />
     * All observers will be notified, update action: new game.
     * khởi tạo trò chơi mới
     */
    public void newGame() {
    	// khởi tạo 1 màn chơi điền đầy đủ các số như 1 màn chơi đã hoàn thiện
        solution = generateSolution(new int[9][9], 0);
        
        // sao chép 1 mảng mới từ mảng màn chơi đầy đủ và bỏ các ô có thể tạo thành mảng game
        game = generateGame(copy(solution));
    }
    /**
     * kiểm tra xem 1 số có trùng với 1 số nào trong hàng không
     * @param game
     * @param y
     * @param number
     * @return
     */
    private boolean isPossibleX(int[][] game, int y, int number) {
        for (int x = 0; x < 9; x++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }

    /**
     * kiểm tra xem 1 số có trùng trong cột không
     * @param game
     * @param x
     * @param number
     * @return
     */
    private boolean isPossibleY(int[][] game, int x, int number) {
        for (int y = 0; y < 9; y++) {
            if (game[y][x] == number)
                return false;
        }
        return true;
    }

    /**
     * kiểm tra xem 1 số có trùng trong 1 ô 3x3 không
     * @param game      map
     * @param x         cột
     * @param y         hàng
     * @param number    số được kiểm tra
     * @return
     */
    private boolean isPossibleBlock(int[][] game, int x, int y, int number) {
        int x1 = x < 3 ? 0 : x < 6 ? 3 : 6;
        int y1 = y < 3 ? 0 : y < 6 ? 3 : 6;
        for (int yy = y1; yy < y1 + 3; yy++) {
            for (int xx = x1; xx < x1 + 3; xx++) {
                if (game[yy][xx] == number)
                    return false;
            }
        }
        return true;
    }

    /**
     * Trả về vị trí 1 số từ dánh sách list là vị trí đưa vào hoặc -1 khi danh sách list rỗng
     * @param game      AutoCreateMap to check. dữ liệu game để kiểm tra
     * @param x         X position in game. vị trí x trong game
     * @param y         Y position in game. vị trí y trong game
     * @param numbers   List of remaining numbers.
     * @return          Next possible number for position in game or -1 when
     *                  list is empty.
     */
    private int getNextPossibleNumber(int[][] game, int x, int y, List<Integer> numbers) {
    	// nếu danh sách còn chưa rỗng thì nó sẽ lấy ra số đầu tiên
    	// nếu số đó trùng với các số trong hàng, trong cột, hoặc trong ô 3x3 thì lấy số khác lúc này lại là số đầu tiên
    	// lặp cho đến khi tìm được số đó thì trả về hoặc trả về -1 nếu không tìm được số nào
        while (numbers.size() > 0) {
            int number = numbers.remove(0);
            if (isPossibleX(game, y, number) && isPossibleY(game, x, number) && isPossibleBlock(game, x, y, number))
                return number;
        }
        return -1;
    }

    /**
     * tạo ra trò chơi sudoku
     * @param game		map
     * @param index		chỉ số hiện tại, phải lớn hơn 0
     * @return          trò chơi đã được khởi tạo
     */
    private int[][] generateSolution(int[][] game, int index) {
    	// nếu chỉ số index vượt qua giá trị chỉ số mảng thì trả về mảng game đầu vào
        if (index > 80)
            return game;
        // tính ra chỉ số cột x và hàng y theo mảng 2 chiều
        int x = index % 9;
        int y = index / 9;
        
        // tạo ra 1 danh sách numbers và thêm các số từ 1-9 sau đó xáo trộn chúng
        List<Integer> numbers = new ArrayList<Integer>();
        if (game[y][x] == 0) {
	        for (int i = 1; i <= 9; i++) numbers.add(i);
	        Collections.shuffle(numbers);
        } else {
        	numbers.add(game[y][x]);
        	game[y][x] = 0;
        }

        // trong khi danh sách list numbers còn chưa rỗng ta sẽ lặp
        while (numbers.size() > 0) {
        	// lấy 1 số từ mảng numbers sau quá trình khởi tạo và xáo trộn lúc này đã trở thành 1 giá trị có thể điền vào vị trí x, y hoặc là -1 nếu không có số nào điền được
        	// nếu số đó la -1 thì mảng game đã sai và trả về null, nếu không thì gán số đó vào vị trí hàng y, cột x game[y][x] 
            int number = getNextPossibleNumber(game, x, y, numbers);
            if (number == -1)
                return null;

            game[y][x] = number;
            // tiếp tục khởi tạo với số bên cạnh nó
            // thủ tục này sẽ gọi đệ quy để cuối cùng nếu thành công sẽ khởi tạo thành công 1 màn chơi
            int[][] tmpGame = generateSolution(game, index + 1);
            if (tmpGame != null)
                return tmpGame;
            game[y][x] = 0;
        }
        return null;
    }

    /**
     * Khởi tạo 1 màn chơi từ 1 mảng đã đã được điền hết các số
     * 
     * @param game
     * @return
     */
    private int[][] generateGame(int[][] game) {
    	// tạo ra 1 danh sách 81 số và xáo trộn chúng
        List<Integer> positions = new ArrayList<Integer>(), remember = new ArrayList<Integer>();
        for (int i = 0; i < 81; i++) 
        	if (map[i/9][i%9] == 0) positions.add(i);
            else remember.add(i);
        Collections.shuffle(positions);
        Collections.shuffle(remember);
        for (int j = 0; j < remember.size(); j++) {
			int i = remember.get(j);
			positions.add(i);
		}
        return generateGame(game, positions);
    }

    /**
     * khởi tạo game từ 1 mảng đã hoàn thiện với 1 mảng 81 số đã được xáo trộn ta sẽ lần lượt xóa đi các ô theo thứ tự đã được xáo trộn
     *
     * @param game          map đầu vào
     * @param positions     danh sách thứ tự sẽ được xóa
     * @return
     */
    private int[][] generateGame(int[][] game, List<Integer> positions) {
    	// nếu mảng vị trí vẫn còn chưa rỗng ta sẽ thực hiện như sau
    	int max = 81-this.count;
    	int removed = 0;
        while (positions.size() > 0 && removed < max) {
        	// lấy ra 1 phần tử là vị trí
            int position = positions.remove(0);
            // chuyển sang tọa độ hàng và cột
            int x = position % 9;
            int y = position / 9;
            
            // xóa đi ô ở vị trí đó
            int temp = game[y][x];
            game[y][x] = 0;
            removed++;
            // nếu không thể xóa thì lại giữ nguyên
            if (!isValid(game)) {
                game[y][x] = temp;
                removed--;
            }
        }
        // kết quả trả về đã xóa tất cả các ô có thể xóa được
        return game;
    }

    /**
     * kiểm tra xem 1 ô có thể xóa được hay không: thử giải ô số đó xem có đc không
     * @param game
     * @return
     */
    private boolean isValid(int[][] game) {
        return isValid(game, 0, new int[] { 0 });
    }

    /**
     * kiểm tra xem 1 ô có thể xóa được hay không bằng cách thử xóa đi ô đó và tìm lời giải bằng đệ quy, nếu có lời giải thì tức là số đó xóa được, nếu không sẽ trả về false
     * @param game                  mảng chứa dữ liệu bản đồ game
     * @param index                 chỉ số hiện tại sẽ được kiểm tra
     * @param numberOfSolutions     số lần duyệt đến điểm cuối cùng khởi tạo ban đâu là new int[] { 0 }
     * @return                      True nếu khả thi, ngược lại false
     */
    private boolean isValid(int[][] game, int index, int[] numberOfSolutions) {
    	// nếu chỉ số vượt ngoài mảng thì trả về
        if (index > 80)
        	// tăng vị trí lên 1 và trả về: nếu vị trí = 0 thì sẽ trả về true, nếu ko thì false
            return ++numberOfSolutions[0] == 1;

        // chuyển vị trí sang chỉ số trong mảng 2 chiều 
        int x = index % 9;
        int y = index / 9;

        // gán ô có vị trí đó = 0 chưa điền số nào thì ta sẽ thử điền 1 số vào số đó, nếu diền rồi thì chuyển sang vị trí tiếp theo;
        if (game[y][x] == 0) {
        	// tạo 1 danh sach từ 1-9
            List<Integer> numbers = new ArrayList<Integer>();
            for (int i = 1; i <= 9; i++)
                numbers.add(i);

            while (numbers.size() > 0) {
            	// lấy ra 1 số từ danh sách có thể điền được vào vị trí đó
            	// nó sẽ ưu tiên điền những số nhỏ trước
                int number = getNextPossibleNumber(game, x, y, numbers);
                
                // nếu không có ô nào điền được thì nhảy ra khỏi vòng lặp
                if (number == -1)
                    break;
                // nếu có số điền được thì gán vị trí đó bằng số điền được
                game[y][x] = number;

                // gọi đệ quy thủ tục này đến vị trí tiếp theo, nếu không thể thì lại gán lại giá trị 0 trước đó và trả về false
                if (!isValid(game, index + 1, numberOfSolutions)) {
                    game[y][x] = 0;
                    return false;
                }
                // sau khi đệ quy kết thúc ta sẽ trả lại các giá trị 0 ban đầu đó
                game[y][x] = 0;
            }
        } else if (!isValid(game, index + 1, numberOfSolutions))
        	// khi không thể bỏ thì sẽ trả về false;
            return false;
        // cuối cùng trả về true;
        return true;
    }

    /**
     * sap chép 1 mảng y hệt như mảng đầu vào
     *
     * @param game
     * @return
     */
    private int[][] copy(int[][] game) {
        int[][] copy = new int[9][9];
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                copy[y][x] = game[y][x];
        }
        return copy;
    }

    /**
     * in ra
     * @param game
     *
    public void print(int[][] game) {
        System.out.println();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++)
                System.out.print(" " + (game[y][x]>0?game[y][x]:" "));
            System.out.println();
        }
    }
    */
}
