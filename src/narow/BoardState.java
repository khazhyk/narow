package narow;

import java.util.Arrays;

public class BoardState{
	
	int[][] board;
	int height;
	int width;
	int hvalue;
	
	public BoardState(int height, int width){
		this.board = new int[height][width];
		this.height = height;
		this.width = width;
		this.hvalue = -1;
	}
	
	public BoardState(int[][] nboard){
		this.board = nboard.clone();
		for (int i = 0; i < nboard.length; i++)
			this.board[i] = nboard[i].clone();
		//this.board = nboard.clone();
		this.height = board.length;
		this.width = board[0].length;
		this.hvalue = -1;
	}
	
	public int[][] updateBoard(int column, int action, Boolean player){
		//System.out.print(this.board[this.height][column]);
//		if (this.board[this.height-1][column] == 0)
//			this.board[this.height-1][column] = player;
		for (int i=0; i<this.board[0].length-1; i++){
			if (this.board[i+1][column] !=0){
				if (player) {this.board[i][column] = 2;} 
				else {this.board[i][column] = 1;};
					break;}
			if (i == this.board[0].length-2)
				if (player) {this.board[i][column] = 2;} 
				else {this.board[i][column] = 1;};
		}
		return this.board;
		
	}
	
	public BoardState nextBoard(int column, int action, Boolean player){
		BoardState nextB = new BoardState(this.board);
		int[] test = this.board[height-1];
		int[] test2 = this.board[column];
		//System.err.print(nextB.board[this.height-1][column]);
		//System.err.println(this.height);
		if (this.board[this.height-1][column] == 0){
			//System.err.print("yes");
			if (player) {nextB.board[this.height-1][column] = 2;} 
			else {nextB.board[this.height-1][column] = 1;}
		}
		for (int i=0; i<this.height-1; i++){
			if (this.board[i+1][column] !=0){
				//System.err.print("yes");
				if (player) {nextB.board[i][column] = 2;} 
				else {nextB.board[i][column] = 1;}
				break;}
		}
		System.err.print(player);
		System.err.println(Arrays.deepToString(nextB.board));
		return nextB;
	}
	
	public Integer calcHValue(){
		this.hvalue = 1; // replace with actual hvalue based on h-function
		return this.hvalue;
	}

}
