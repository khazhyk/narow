package narow;

public class BoardState{
	Player[][] board;
	int height;
	int width;
	
	/**
	 * Creates a blank board of given width and height
	 * @param height
	 * @param width
	 */
	public BoardState(int height, int width){
		this.board = new Player[height][width];
		this.height = height;
		this.width = width;
	}
	
	/**
	 * Initializes board to the given 2d array. The array should be the same size for all entries
	 * @param nboard
	 */
	public BoardState(Player[][] nboard){
		this.board = nboard.clone();
		for (int i = 0; i < nboard.length; i++)
			this.board[i] = nboard[i].clone();

		this.height = board.length;
		this.width = board[0].length;
	}
	
	public BoardState(String... rows ) {
	    this.width = rows[0].split(" ").length;
	    this.height = rows.length;
	    this.board = new Player[height][width];
	    
	    for (int x = 0; x < rows.length; x++) {
	        String[] ent =  rows[x].split(" ");
	        for (int y = 0; y < ent.length; y++ ) {
	            board[x][y] = Player.values()[Integer.parseInt(ent[y])];
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
	public BoardState nextBoard(int column, Action action, boolean player){
		BoardState nextB = new BoardState(this.board);
		//System.err.print(nextB.board[this.height-1][column]);
		//System.err.println(this.height);
		if (this.board[this.height-1][column] == Player.NONE){
			//System.err.print("yes");
			if (player) {nextB.board[this.height-1][column] = Player.ONE;} 
			else {nextB.board[this.height-1][column] = Player.TWO;}
		}
		for (int i=0; i<this.height-1; i++){
			if (this.board[i+1][column] !=Player.NONE){
				//System.err.print("yes");
				if (player) {nextB.board[i][column] = Player.ONE;} 
				else {nextB.board[i][column] =Player.TWO;}
				break;}
		}
		return nextB;
	}
	
	/**
	 * Favor for player 1 is positive,
	 * Favor for player 2 is negative
	 * @param c
	 * @return
	 */
	public int traverse(Config c) {
	    // horizontal, vertical, \, /
	    int numInRow = 0;
	    int widthHeightDiff = Math.abs(width - height);
	    
	    Player lastP = Player.NONE;
	    
	    // Horizontal Test
	    for (int x = 0; x < height; x++) {
	        lastP = Player.NONE; // Beginning of a row
	        numInRow = 0;
	        for (int y = 0; y < width; y++) {
	            switch (board[x][y]) {
    	            case NONE:
    	                numInRow = 0;
    	                break;
    	            case ONE:
    	            case TWO:
    	                if (lastP == Player.NONE || lastP == board[x][y]) numInRow++;
                        else numInRow = 1;
    	                break;
	            }
	            lastP = board[x][y];
	            
	            // FINAL STATE
	            if (numInRow >= c.arow) {
	                return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
	            }
	        }
	    }
	    
	    // Vertical Test
	    for (int y = 0; y < width; y++) {
	        lastP = Player.NONE; // Beginning of a col
	        numInRow = 0;
	        vert:
	        for (int x = height - 1; x >= 0; x--) { // go from bottom to top
	            switch (board[x][y]) {
                    case NONE:
                        break vert; // You can't have a piece on top of nothing. :)
                    case ONE:
                    case TWO:
                        if (lastP == Player.NONE || lastP == board[x][y]) numInRow++;
                        break;
	            }
	            lastP = board[x][y];
            
	            // FINAL STATE
	            if (numInRow >= c.arow) {
	                return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
	            }
	        }
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
	            switch (board[x+i][x]) {
	            case NONE:
	                numInRow = 0;
	                break;
	            case ONE:
	            case TWO:
                    if (lastP == Player.NONE || lastP == board[x+i][x]) numInRow++;
                    else numInRow = 1;
	                break;
	            }
	            lastP = board[x+i][x];
	            
                // FINAL STATE
                if (numInRow >= c.arow) {
                    return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
                }
	        }
	    }
	    
	    // Going Right from 0,0
	    for (int i = 0; i <= width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            
            // Diag \
            for (int x = 0; x < ((i < widthHeightDiff) ? height : width) - i; x++) {
                switch (board[x][x+i]) {
                case NONE:
                    numInRow = 0;
                    break;
                case ONE:
                case TWO:
                    if (lastP == Player.NONE || lastP == board[x][x+i]) numInRow++;
                    else numInRow = 1;
                    break;
                }
                lastP = board[x][x+i];
                
                // FINAL STATE
                if (numInRow >= c.arow) {
                    return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
                }
            }
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
	            switch (board[x+i][width - 1 - x]) {
	            case NONE:
	                numInRow = 0;
	                break;
	            case ONE:
	            case TWO:
                    if (lastP == Player.NONE || lastP == board[x+i][width - 1 - x]) numInRow++;
                    else numInRow = 1;
	                break;
	            }
	            lastP = board[x+i][width - 1 - x];
	            
                // FINAL STATE
                if (numInRow >= c.arow) {
                    return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
                }
	        }
	    }
	    
	    for (int i = 0; i <= width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            
            // Diag /
            for (int x = 0; x < ((i < widthHeightDiff) ? height : width) - i; x++) {
                switch (board[x][width - 1 - x - i]) {
                case NONE:
                    numInRow = 0;
                    break;
                case ONE:
                case TWO:
                    if (lastP == Player.NONE || lastP == board[x][width - 1 - x - i]) numInRow++;
                    else numInRow = 1;
                    break;
                }
                lastP = board[x][width - 1 - x - i];
                
                // FINAL STATE
                if (numInRow >= c.arow) {
                    return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
                }
            }
        }
     
	    // SOON
	    return 0;
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
