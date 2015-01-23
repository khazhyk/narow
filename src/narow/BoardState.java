package narow;

public class BoardState{
	PlaceState[][] board;
	int height;
	int width;
	
	/**
	 * Creates a blank board of given width and height
	 * @param height
	 * @param width
	 */
	public BoardState(int height, int width){
		this.board = new PlaceState[height][width];
		this.height = height;
		this.width = width;
	}
	
	/**
	 * Initializes board to the given 2d array. The array should be the same size for all entries
	 * @param nboard
	 */
	public BoardState(PlaceState[][] nboard){
		this.board = nboard.clone();
		for (int i = 0; i < nboard.length; i++)
			this.board[i] = nboard[i].clone();

		this.height = board.length;
		this.width = board[0].length;
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
		if (this.board[this.height-1][column] == PlaceState.Empty){
			//System.err.print("yes");
			if (player) {nextB.board[this.height-1][column] = PlaceState.Player1;} 
			else {nextB.board[this.height-1][column] = PlaceState.Player2;}
		}
		for (int i=0; i<this.height-1; i++){
			if (this.board[i+1][column] !=PlaceState.Empty){
				//System.err.print("yes");
				if (player) {nextB.board[i][column] = PlaceState.Player1;} 
				else {nextB.board[i][column] =PlaceState.Player2;}
				break;}
		}
		return nextB;
	}
}
