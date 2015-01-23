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
	    // horizontal, vertical, /, \
	    int numInRow = 0;
	    Player lastP = Player.NONE;
	    
	    for (int x = 0; x < height; x++) {
	        for (int y = 0; y < width; y++) {
	            switch (board[x][y]) {
    	            case NONE:
    	                numInRow = 0;
    	                break;
    	            case ONE:
    	            case TWO:
    	                if (lastP == Player.NONE || lastP == board[x][y]) numInRow++;
                        else numInRow = 0;
    	                break;
	            }
	            lastP = board[x][y];
	            
	            // FINAL STATE
	            if (numInRow >= c.arow) {
	                return (lastP == Player.ONE) ? Integer.MAX_VALUE : Integer.MIN_VALUE; 
	            }
	        }
	    }
	    return 0;
	}
	
	
	
	public boolean isGameOver() {
	    return false;
	}
}
