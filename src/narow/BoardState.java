package narow;

public class BoardState{
	
	int[][] board;
	int height;
	int width;
	
	public BoardState(int height, int width){
		this.board = new int[height][width];
		this.height = height;
		this.width = width;
	}
	
	public BoardState(int[][] board){
		this.board = new int[height][width];
		this.height = board.length;
		this.width = board[0].length;
	}
	
	public int[][] updateBoard(int column, int action, Boolean player){
		//System.out.print(this.board[this.height][column]);
//		if (this.board[this.height-1][column] == 0)
//			this.board[this.height-1][column] = player;
		for (int i=0; i<this.height-1; i++){
			if (this.board[i+1][column] !=0){
				if (player) {this.board[i][column] = 2;} 
				else {this.board[i][column] = 1;};
					break;}
			if (i == this.height-2)
				if (player) {this.board[i][column] = 2;} 
				else {this.board[i][column] = 1;};
		}
		return this.board;
		
	}
	
	public BoardState nextBoard(int column, int action, Boolean player){
		//System.out.print(this.board[this.height][column]);
//		if (this.board[this.height-1][column] == 0)
//			this.board[this.height-1][column] = player;
		for (int i=0; i<this.height-1; i++){
			if (this.board[i+1][column] !=0){
				if (player) {this.board[this.height-1][column] = 2;} 
				else {this.board[this.height-1][column] = 1;};
				break;}
			if (i == this.height-2)
				if (player) {this.board[this.height-1][column] = 2;} 
				else {this.board[this.height-1][column] = 1;};
		}
		return this;
		
	}

}
