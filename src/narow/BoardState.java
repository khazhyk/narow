package narow;

import java.util.Arrays;

public class BoardState{
	public enum PlaceState {
		Empty,
		Player1,
		Player2
	}
	PlaceState[][] board;
	int height;
	int width;
	int hvalue;
	
	public BoardState(int height, int width){
		this.board = new PlaceState[height][width];
		this.height = height;
		this.width = width;
		this.hvalue = -1;
	}
	
	public BoardState(PlaceState[][] nboard){
		this.board = nboard.clone();
		for (int i = 0; i < nboard.length; i++)
			this.board[i] = nboard[i].clone();
		//this.board = nboard.clone();
		this.height = board.length;
		this.width = board[0].length;
		this.hvalue = -1;
	}
	
	public PlaceState[][] updateBoard(int column, int action, PlaceState player){
		//System.out.print(this.board[this.height][column]);
//		if (this.board[this.height-1][column] == 0)
//			this.board[this.height-1][column] = player;
		for (int i=0; i<this.board[0].length-1; i++){
			if (this.board[i+1][column] != PlaceState.Empty){
				if (player == PlaceState.Player1) {this.board[i][column] = PlaceState.Player1;} 
				else {this.board[i][column] = PlaceState.Player2;};
					break;}
			if (i == this.board[0].length-2)
				if (player == PlaceState.Player1) {this.board[i][column] = PlaceState.Player1;} 
				else {this.board[i][column] = PlaceState.Player2;};
		}
		return this.board;
		
	}
	
	public BoardState nextBoard(int column, int action, PlaceState player){
		BoardState nextB = new BoardState(this.board);
		//System.err.print(nextB.board[this.height-1][column]);
		//System.err.println(this.height);
		if (this.board[this.height-1][column] == PlaceState.Empty){
			//System.err.print("yes");
			if (player == PlaceState.Player1) {nextB.board[this.height-1][column] = PlaceState.Player1;} 
			else {nextB.board[this.height-1][column] = PlaceState.Player2;}
		}
		for (int i=0; i<this.height-1; i++){
			if (this.board[i+1][column] !=PlaceState.Empty){
				//System.err.print("yes");
				if (player == PlaceState.Player1) {nextB.board[i][column] = PlaceState.Player1;} 
				else {nextB.board[i][column] =PlaceState.Player2;}
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
