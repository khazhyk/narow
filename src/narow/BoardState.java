package narow;

import java.util.ArrayList;
import java.util.List;

public class BoardState{
	int[][] board;
	int height;
	int width;
	int playerToMove;
	
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
	
	public int genHVal(Config c) {
		final int[][] narow = traverse(c, Player.US); // we're always max
		
		if (narow[0][c.arow - 1] > 0 ^ narow[1][c.arow - 1] > 0) { // Exactly one winner
	    	return ((narow[0][c.arow - 1] > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
	    }
	   
		return 0; // Multiple winners or no winners is a tie
	}
	
	/**
	 * Favor for player 1 is positive,
	 * Favor for player 2 is negative
	 * @param c
	 * @return
	 */
	public int[][] traverse(Config c, int maxPlayer) {
	    // horizontal, vertical, \, /
	    int numInRow = 0;
	    
	    int[][] narow = new int[][] {
	    		new int[c.arow],
	    		new int[c.arow]
	    };
	    
	    int lastP = Player.NONE;
	    
	    // Horizontal Test
	    for (int x = 0; x < height; x++) {
	        lastP = Player.NONE; // Beginning of a row
	        numInRow = 0;
	        for (int y = 0; y < width; y++) {
	        	int piece = board[x][y];
	            switch (piece) {
    	            case Player.NONE:
    	            	if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
    	            	numInRow = 0;
    	                break;
    	            case Player.US:
    	            case Player.THEM:
    	                if (lastP == Player.NONE || lastP == piece) numInRow++;
                        else {
                        	narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                        	numInRow = 1;
                        }
    	                break;
	            }
	            lastP = piece;
	        }
	        if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
	    }
	    
	    // Vertical Test
	    for (int y = 0; y < width; y++) {
	        lastP = Player.NONE; // Beginning of a col
	        numInRow = 0;
	        vert:
	        for (int x = height - 1; x >= 0; x--) { // go from bottom to top
	        	int piece = board[x][y];
	            switch (piece) {
                    case Player.NONE:
                    	if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    	lastP = Player.NONE;
                        break vert; // You can't have a piece on top of nothing. :)
                    case Player.US:
                    case Player.THEM:
                        if (lastP == Player.NONE || lastP == piece) numInRow++;
                        else {
                        	narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                        	numInRow = 1;
                        }
                        break;
	            }
	            lastP = piece;
	        }
	        if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
	    }
	    
	    // Diagonal \ Test
	    /*
	     * 0,0 0,1 0,2 0,3
	     * 1,0 1,1 1,2 1,3
	     * 2,0 2,1 2,2 2,3
	     * 
	     * for n = 2, test starting at 1,0; 0,0; 0,1; 0,2
	     * 
	     * Start at x=(height - n + 1), go until 0,0, then go to y=(width - n + 1)
	     */
	    
	    // Going up to 0,0
	    for (int i = height - c.arow; i> 0; i--) {
	        lastP = Player.NONE;
	        numInRow = 0;
	        for (int x = 0; x < height - i; x++) {
	            int piece = board[x+i][x];
				switch (piece) {
	            case Player.NONE:
	            	if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
	                numInRow = 0;
	                break;
	            case Player.US:
	            case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                    	narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    	numInRow = 1;
                    };
	                break;
	            }
	            lastP = piece;
	        }
	        if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
	    }
	    
	    // Going Right from 0,0
	    for (int i = 0; i <= width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            
            // Diag \
            for (int x = 0; x < (height) && x + i < width; x++) {
                int piece = board[x][x+i];
				switch (piece) {
                case Player.NONE:
                	if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    numInRow = 0;
                    break;
                case Player.US:
                case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                    	narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    	numInRow = 1;
                    }
                    break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
        }
	    
	    // Diagonal / Test
	    
	    /*
	     * 0,0 0,1 0,2 0,3
	     * 1,0 1,1 1,2 1,3
	     * 2,0 2,1 2,2 2,3
	     * 
	     * for n = 2, test starting at 1,3; 0,3; 0,2; 0,1
	     * 
	     * start at y = height - n + 1, x = width - 1, 
	     */
	    for (int i = height - c.arow; i> 0; i--) {
	        lastP = Player.NONE;
	        numInRow = 0;
	        for (int x = 0; x < height - i; x++) {
	            int piece = board[x+i][width - 1 - x];
				switch (piece) {
	            case Player.NONE:
	            	if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
	                numInRow = 0;
	                break;
	            case Player.US:
	            case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                    	narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    	numInRow = 1;
                    }
	                break;
	            }
	            lastP = piece;
	        }
	        if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
	    }
	    
	    for (int i = 0; i <= width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            
            // Diag /
            for (int x = 0;  x < (height) && width - 1 - x - i >= 0; x++) {
                int piece = board[x][width - 1 - x - i];
				switch (piece) {
                case Player.NONE:
                	if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    numInRow = 0;
                    break;
                case Player.US:
                case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                    	narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++;
                    	numInRow = 1;
                    }
                    break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == maxPlayer ? 0 : 1][numInRow - 1]++; 
        }
	    
	    return narow;
	}
	
	
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
	
	
	
	public boolean isGameOver() {
	    return false;
	}
}
