package narow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateNode {
	
	BoardState current;
	Boolean player; // min (1) or max (0)
	int minimaxscore;
	ArrayList <StateNode> nextState;
	Config c;
	int nextMove;
	Action nextAction;
	
	public StateNode(BoardState state, Boolean player) {
		this.current = state;
		this.player = player;
		this.nextState = new ArrayList<StateNode>();
	}
	
	public StateNode(int value) {
		this.minimaxscore = value;
		this.nextState = new ArrayList<StateNode>();
	}
	
	
	/**
	 * Find the best move to make using a given number of levels to search. Saves bestmove to the object
	 * @param levels
	 * @return minimaxscore
	 */
	public Integer Minimax(int level, Boolean pop1, Boolean pop2){
		if (level == 1){//If node is at the lowest level of searching, just return hvalue
			//System.err.println("next: "+current.hval());
			return current.hval();
		}
		int max = -1;//lower value than heuristic will return (first move will replace)
		int min = 5;//higher value than heuristic will return (first move will replace)
		//System.err.println("debug "+this.current.width);
		for (int w = 0; w < this.current.width; w++) {// try dropping a token in each column
			//System.err.print("(" + level + "," + w + ") ");
			if (this.player) {// for player 1, find max of the next nodes
				// Find the minimax value of the next statenode if the token is
				// dropped in column w
				if (current.board[0][w] == Player.NONE) {//if we can place in this column, try that
					int next = new StateNode(this.current.nextBoard(w,
							Action.Place, this.player), !player).Minimax(
							level - 1, pop1, pop2);
					// if value of "next" is better than the last best found
					// move, this is new max
					if (next > max) {
						max = next;
						this.nextMove = w;
						this.minimaxscore = max;
						this.nextAction = Action.Place;
					}
				}
				if (this.current.board[current.height - 1][w] == Player.ONE&& pop1) {//if we can pop
					int next = new StateNode(this.current.nextBoard(w,
							Action.PopOut, this.player), !player).Minimax(
							level - 1, !pop1, pop2);
					//if this results in better move, do it
					if (next > max) {
						max = next;
						this.nextMove = w;
						this.minimaxscore = max;
						this.nextAction = Action.PopOut;
					}
				}
			} else {// if player 2 find min of next nodes
				if (current.board[0][w] == Player.NONE) {// if we can place, try it
					int next = new StateNode(this.current.nextBoard(w,
							Action.Place, this.player), !player).Minimax(
							level - 1, pop1, pop2);
					// if this is a better move, save as best
					if (next < min) {
						min = next;
						this.nextMove = w;
						this.minimaxscore = min;
						this.nextAction = Action.Place;
					}
				}
				if (this.current.board[current.height - 1][w] == Player.TWO
						&& pop2) {// if we can pop try it
					int next = new StateNode(this.current.nextBoard(w,
							Action.PopOut, this.player), !player).Minimax(
							level - 1, pop1, !pop2);
					// if this is a better move, take it
					if (next < min) {
						min = next;
						this.nextMove = w;
						this.minimaxscore = min;
						this.nextAction = Action.PopOut;
					}
				}
			}
		}
		// return the minimax score of the node
		return minimaxscore;
	}

	/**
	 * Implement Minimax with AB pruning
	 * 
	 * @param levels
	 * @param alpha
	 *            (value of best choice so far for max) (for top node, -inf)
	 * @param beta
	 *            (value of best choice so far for min) (for top node, inf)
	 * @return minimaxscore
	 */
	
	public Integer MinimaxAB(int level, Boolean pop1, Boolean pop2, int alpha, int beta){
		if (level == 1){//If node is at the lowest level of searching, just return hvalue
			//System.err.println("next: "+current.hval());
			return current.hval();
		}
		int max = -1;//lower value than heuristic will return (first move will replace)
		int min = 5;//higher value than heuristic will return (first move will replace)
		//System.err.println("debug "+this.current.width);
		for (int w = 0; w < this.current.width; w++) {// try dropping a token in each column
			//System.err.print("(" + level + "," + w + ") ");
			if (this.player) {// for player 1, find max of the next nodes
				// Find the minimax value of the next statenode if the token is
				// dropped in column w
				if (current.board[0][w] == Player.NONE) {//if we can place in this column, try that
					int next = new StateNode(this.current.nextBoard(w,
							Action.Place, this.player), !player).MinimaxAB(
							level - 1, pop1, pop2, alpha, beta);
					// if value of "next" is better than the last best found
					// move, this is new max
					if (next > max) {
						max = next;
						this.nextMove = w;
						this.minimaxscore = max;
						this.nextAction = Action.Place;
					}
					if (max >= beta){
						//System.err.println("pruning");
						return max;
					}
					if (max > alpha){
						alpha = max;
					}
				}
				if (this.current.board[current.height - 1][w] == Player.ONE&& pop1) {//if we can pop
					int next = new StateNode(this.current.nextBoard(w,
							Action.PopOut, this.player), !player).MinimaxAB(
							level - 1, !pop1, pop2, alpha, beta);
					//if this results in better move, do it
					if (next > max) {
						max = next;
						this.nextMove = w;
						this.minimaxscore = max;
						this.nextAction = Action.PopOut;
					}
					if (max >= beta){
						//System.err.println("pruning");
						return max;
					}
					if (max > alpha){
						alpha = max;
					}
				}
			} else {// if player 2 find min of next nodes
				if (current.board[0][w] == Player.NONE) {// if we can place, try it
					int next = new StateNode(this.current.nextBoard(w,
							Action.Place, this.player), !player).MinimaxAB(
							level - 1, pop1, pop2, alpha, beta);
					// if this is a better move, save as best
					if (next < min) {
						min = next;
						this.nextMove = w;
						this.minimaxscore = min;
						this.nextAction = Action.Place;
					}
					if (min <= alpha){
						//System.err.println("pruning");
						return max;
					}
					if (min < beta){
						beta = min;
					}
				}
				if (this.current.board[current.height - 1][w] == Player.TWO
						&& pop2) {// if we can pop try it
					int next = new StateNode(this.current.nextBoard(w,
							Action.PopOut, this.player), !player).MinimaxAB(
							level - 1, pop1, !pop2, alpha, beta);
					// if this is a better move, take it
					if (next < min) {
						min = next;
						this.nextMove = w;
						this.minimaxscore = min;
						this.nextAction = Action.PopOut;
					}
					if (min <= alpha){
						//System.err.println("pruning");
						return max;
					}
					if (min < beta){
						beta = min;
					}
				}
			}
		}
		// return the minimax score of the node
		return minimaxscore;
	}
	
	
	
	/*public Integer MinimaxAB(int level, int alpha, int beta){
		if (level == 1){//If node is at the lowest level of searching, just return hvalue
			System.err.println("next: "+current.hval());
			return current.hval();
		}
		int max = 0;
		int min = 5;
		for (int w = 0; w<this.current.width; w++){//try dropping a token in each column
			System.err.print("("+level+","+w+") "+alpha+" "+beta);
			if (this.player){//for player 1, find max of the next nodes
				//Find the minimax value of the next statenode if the token is dropped in column w
				int next = new StateNode(this.current.nextBoard(w, Action.Place, this.player), !player).MinimaxAB(level-1, alpha, beta);
				//if value of "next" is better than the last best found move, this is new max
				if (next > max){
					System.out.println("better move");
					max = next;
					this.nextMove = w;
					this.minimaxscore = max;
				}
				if (max >= beta){
					//System.err.println("pruning");
					return max;
				}
				if (max > alpha){
					alpha = max;
				}
			}
			else{//if player 2 find min of next nodes
				int next = new StateNode(this.current.nextBoard(w, Action.Place, this.player), !player).MinimaxAB(level-1, alpha, beta);
				if (next < min){
					//System.out.println("better move");
					min = next;
					this.nextMove = w;
					this.minimaxscore = min;
				}
				if (min <= alpha){
					//System.err.println("pruning");
					return max;
				}
				if (min < beta){
					beta = min;
				}
			}
		}
		//return the minimax score of the node
		//System.err.println("done "+minimaxscore);
		return minimaxscore;
	}*/
	
	
	public void setMMScore(int value){
		this.minimaxscore = value;
	}
	
}
