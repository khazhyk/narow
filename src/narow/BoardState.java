package narow;

import java.util.ArrayList;
import java.util.List;

import narow.state.Action;
import narow.state.Config;
import narow.state.Player;

public class BoardState{
	public int[][] board;
	public int height;
	public int width;
	public int playerToMove;
	
	// this is breadth, bad
	@Deprecated
	public List<BoardState> allLegalMoves(int player) {
	    List<BoardState> list = new ArrayList<BoardState>();
	    
	    for (int i = 0; i < width; i++) {
	        if (board[0][i] == Player.NONE) list.add(this.nextBoard(i, Action.Place, player));
	        if (board[height - 1][i] == player) list.add(this.nextBoard(i, Action.PopOut, player));
	    }
	    
	    return list;
	}
	
	/**
	 * Creates a blank board of given width and height
	 * @param height
	 * @param width
	 */
	public BoardState(int height, int width){
		this.board = new int[height][width];
		this.height = height;
		this.width = width;
	}
	
	/**
	 * Initializes board to the given 2d array. The array should be the same size for all entries
	 * @param nboard
	 */
	public BoardState(int[][] nboard){
		this.board = nboard.clone();
		for (int i = 0; i < nboard.length; i++)
			this.board[i] = nboard[i].clone();

		this.height = board.length;
		this.width = board[0].length;
	}
	
	public BoardState(String... rows ) {
	    this.width = rows[0].split(" ").length;
	    this.height = rows.length;
	    this.board = new int[height][width];
	    
	    for (int x = 0; x < rows.length; x++) {
	        String[] ent =  rows[x].split(" ");
	        for (int y = 0; y < ent.length; y++ ) {
	            board[x][y] = Integer.parseInt(ent[y]);
	            if (board[x][y] == 9) board[x][y] = 0;
	        }
	    }
	}

	/**
	 * Get the next board
	 * @param column
	 * @param action
	 * @param player
	 * @return
	 */
	public BoardState nextBoard(int column, int action, int player){
		BoardState nextB = new BoardState(this.board);
		nextB.playerToMove = (player == Player.US) ? Player.THEM : Player.US; 
		//System.err.print(nextB.board[this.height-1][column]);
		//System.err.println(this.height);
		//System.err.println(action);
		if (action == Action.Place) {
			
			if (this.board[this.height - 1][column] == Player.NONE) {
				// System.err.print("yes");
				if (player == Player.US) {
					nextB.board[this.height - 1][column] = Player.US;
				} else {
					nextB.board[this.height - 1][column] = Player.THEM;
				}
			}
			for (int i = 0; i < this.height - 1; i++) {
				if (this.board[i + 1][column] != Player.NONE) {
					// System.err.print("yes");
					if (player == Player.US) {
						nextB.board[i][column] = Player.US;
					} else {
						nextB.board[i][column] = Player.THEM;
					}
					break;
				}
			}
		}
		else if (action == Action.PopOut){
			for (int j = height-1; j > 0; j--){
				nextB.board[j][column] = board[j-1][column];
			}
			nextB.board[0][column] = Player.NONE;
		}
		return nextB;
	}
	
	/**
	 * This looks through and counts the number of active n in a rows, (1,2,3,...,n)
	 * @param c
	 * @return
	 */
	
	
	
	//test heuristic, counts the number of tokens in column 2
	public Integer hval(){
		int tokencount = 0;
		int testcolumn = 1;
		for (int i = 0; i < this.height; i++){
			if (board[i][testcolumn] != Player.NONE){
				tokencount++;
			}
		}
		return tokencount;	
	}
	
	
	public int genHVal2(Config c, int maxPlayer){
		int openruns = 0;
		//open horizontal runs
		for (int h = 0; h<height; h++){
		for (int w = 0; w<width-c.arow+1; w++){
			Boolean runIsClear = true;
			for (int x = 0; x<c.arow; x++){
				if (board[h][w+x] != maxPlayer && board[h][w+x] !=0){
					runIsClear = false;
				}	
			}
			if (runIsClear)
				openruns++;
		}
		}
		System.out.println("runs "+openruns);

		// open horizontal runs
		for (int h = 0; h < height - c.arow + 1; h++) {
			for (int w = 0; w < width; w++) {
				Boolean runIsClear = true;
				for (int x = 0; x < c.arow; x++) {
					if (board[h + x][w] != maxPlayer && board[h + x][w] != 0) {
						runIsClear = false;
					}
				}
				if (runIsClear)
					openruns++;
			}
		}
		System.out.println("runs "+openruns);

		// opendiagonals (left to right)
		for (int w = 0; w < width - c.arow + 1; w++) {
			for (int h = 0; h < height - c.arow + 1; h++) {
				Boolean runIsClear = true;
				for (int x = 0; x < c.arow; x++) {
					if (board[h + x][w + x] != maxPlayer
							&& board[h + x][w + x] != 0) {
						runIsClear = false;
					}
				}
				if (runIsClear)
					openruns++;
			}
		}
		System.out.println("runs "+openruns);

		// opendiagonals (right to left)
		for (int w = c.arow-1; w < width; w++) {
			for (int h = 0; h < height - c.arow + 1; h++) {
				Boolean runIsClear = true;
				for (int x = 0; x < c.arow; x++) {
					if (board[h + x][w - x] != maxPlayer
							&& board[h + x][w - x] != 0) {
						runIsClear = false;
					}
				}
				if (runIsClear)
					openruns++;
			}
		}
		System.out.println("runs "+openruns);

		return openruns;

	}
	
	
	
	public boolean isGameOver() {
	    return false;
	}
}
