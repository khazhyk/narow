package narow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StateNode {
	
	BoardState current;
	Boolean player; // min (1) or max (0)
	int minimaxscore;
	ArrayList <StateNode> nextState;
	
	public StateNode(BoardState state, Boolean player) {
		this.current = state;
		this.player = player;
		this.nextState = new ArrayList<StateNode>();
	}
	
	public StateNode(int value) {
		this.minimaxscore = value;
		this.nextState = new ArrayList<StateNode>();
	}
	
	//generate tree of certain depth
	public void makeMoveTree(int level){
		
		//make all "width" possible moves and determine if they are legal
		for (int w = 0; w < this.current.width; w++){
			if (this.current.board[0][w] == 0){
				nextState.add(new StateNode(current.nextBoard(w, 1, player), !player));
				System.out.println(Arrays.deepToString(current.nextBoard(w, 1, player).board));
			}
		}
			
		if (level > 1)
			for (int i = 0; i < nextState.size(); i++)
				nextState.get(i).makeMoveTree(level-1);
	}
	
	public int calcMiniMax(){
		if (!nextState.isEmpty())
			if (player){
				int maxVal = nextState.get(0).calcMiniMax();
				StateNode bestMove = nextState.get(0);
				this.setMMScore(maxVal);
				for (int i = 0; i < nextState.size(); i++){
					if (nextState.get(i).calcMiniMax() > maxVal){
						bestMove = nextState.get(i);
						maxVal = bestMove.calcMiniMax();
						this.setMMScore(maxVal);
					}
				}
			}
			else{	// player2 (min or 1)
				int minVal = nextState.get(0).calcMiniMax();
				StateNode bestMove = nextState.get(0);
				this.setMMScore(minVal);
				for (int i = 0; i < nextState.size(); i++){
					if (nextState.get(i).calcMiniMax() < minVal){
						bestMove = nextState.get(i);
						minVal = bestMove.calcMiniMax();
						this.setMMScore(minVal);
					}
				}
				
			}
		return this.minimaxscore;
	}
	
	public void setMMScore(int value){
		this.minimaxscore = value;
	}
	
	// when backing up the tree
	public void setMinimax(){
		if (player){
			StateNode bestMove = nextState.get(0);
			int maxVal = bestMove.minimaxscore;
			for (int i = 0; i < nextState.size(); i++){
				if (nextState.get(i).minimaxscore > maxVal){
					bestMove = nextState.get(i);
					maxVal = bestMove.minimaxscore;
				}
			}
		}
		else{	// player2 (min or 1)
			StateNode bestMove = nextState.get(0);
			int minVal = bestMove.minimaxscore;
			for (int i = 0; i < nextState.size(); i++){
				if (nextState.get(i).minimaxscore < minVal){
					bestMove = nextState.get(i);
					minVal = bestMove.minimaxscore;
				}
			}
		}
	}
	
}
