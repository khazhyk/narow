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
	public Integer Minimax(int level){
		if (level == 1){//If node is at the lowest level of searching, just return hvalue
			return current.hval();
		}
		int max = 0;
		int min = 5;
		for (int w = 0; w<this.current.width; w++){//try dropping a token in each column
			if (this.player){//for player 1, find max of the next nodes
				//Find the minimax value of the next statenode if the token is dropped in column w
				int next = new StateNode(this.current.nextBoard(w, Action.Place, this.player), !player).Minimax(level-1);
				//if value of "next" is better than the last best found move, this is new max
				if (next > max){
					max = next;
					this.nextMove = w;
					this.minimaxscore = max;
				}
			}
			else{//if player 2 find min of next nodes
				int next = new StateNode(this.current.nextBoard(w, Action.Place, this.player), !player).Minimax(level-1);
				if (next < min){
					min = next;
					this.nextMove = w;
					this.minimaxscore = min;
				}
			}
		}
		//return the minimax score of the node
		return minimaxscore;
	}
	
	/**
	 * Implement Minimax with AB pruning
	 * @param levels
	 * @param alpha (value of best choice so far for max) (for top node, -inf)
	 * @param beta (value of best choice so far for min) (for top node, inf)
	 * @return minimaxscore 
	 */
	public Integer MinimaxAB(int level, int alpha, int beta){
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
					//System.out.println("better move");
					max = next;
					this.nextMove = w;
					this.minimaxscore = max;
				}
				if (max >= beta){
					System.err.println("pruning");
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
					System.err.println("pruning");
					return max;
				}
				if (min < beta){
					beta = min;
				}
			}
		}
		//return the minimax score of the node
		System.err.println("done "+minimaxscore);
		return minimaxscore;
	}
	
	
	public void setMMScore(int value){
		this.minimaxscore = value;
	}
	
}
