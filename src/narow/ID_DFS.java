/**
 * CS4341 - Project 1 Games
 * Professor Neil Heffernan
 * 
 * Lillian Walker
 * Khazhismel Kumykov
 * 
 */
package narow;

import narow.formats.Action;
import narow.formats.Move;
import narow.formats.Player;
import narow.heuristics.Heuristic;

/**
 * Iterative Deepening Depth-First Search, will return early if it finds a winning move, otherwise will search forever.
 * Throw this in a thread and kill it when you time out.
 * 
 */
public class ID_DFS {

    PlayerState ps;
    
    public ID_DFS(PlayerState playerState) {
        this.ps = playerState;
    }
    
    Move currentBestMove;
    
    public Move iterativeDeepeningBestMove(Heuristic h, BoardState bs, boolean canPopUs, boolean canPopThem) {
        for (int i = 1; ; i+= 2) {
            currentBestMove = findBestMove(h, bs, i, true, canPopUs, canPopThem); 
            if (currentBestMove.score >= Integer.MAX_VALUE) return currentBestMove; 
        }
    }
    
/**
 * finds the best move of the moves at the top of the tree of options    
 * @param h heuristic to be used
 * @param bs current board state
 * @param depth how deep to check the tree
 * @param isMaxLevel Boolean: True if its our turn (Max) False if its their turn (Min)
 * @param canPopUs Can we still pop a token
 * @param canPopThem Can they still pop a token
 * @return bestMove available
 */

    public Move findBestMove(Heuristic h, BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem) {
    	Move bestMove = null;
    	long bestScore = isMaxLevel ? Long.MIN_VALUE : Long.MAX_VALUE;
    	
    	int playerToMove = isMaxLevel ? Player.US : Player.THEM;
    	boolean canPop = isMaxLevel ? canPopUs : canPopThem;
    	long alpha = Long.MIN_VALUE;
    	long beta = Long.MAX_VALUE;
    	
    	for (int i = 0; i < bs.width; i++) {
            if (bs.board[0][i] == Player.NONE) {
                long next = calcValue(h, bs.nextBoard(i, Action.Place, playerToMove),
                        depth - 1, !isMaxLevel, canPopUs, canPopThem);
                if (isMaxLevel ? (next >= bestScore) : (next <= bestScore)) {
                    bestScore = next;
                    bestMove = new Move(i, Action.Place);
                    bestMove.score = bestScore;
                }
                if (isMaxLevel ? (bestScore >= beta) : (bestScore <= alpha)) {
                    return bestMove;
                }
                if (isMaxLevel && (bestScore > alpha)) {
                    alpha = bestScore;
                } else if (!isMaxLevel && bestScore < beta) {
                    beta = bestScore;
                }
            }
            if (canPop && bs.board[bs.height - 1][i] == playerToMove) {
                long next = calcValue(h, bs.nextBoard(i, Action.PopOut, playerToMove),
                        depth - 1, !isMaxLevel, isMaxLevel ? false : canPopUs, isMaxLevel ? canPopThem : false, alpha, beta);
                if (isMaxLevel ? (next >= bestScore) : (next <= bestScore)) {
                    bestScore = next;
                    bestMove = new Move(i, Action.PopOut);
                }
                if (isMaxLevel ? (bestScore >= beta) : (bestScore <= alpha)) {
                    return bestMove;
                }
                if (isMaxLevel && (bestScore > alpha)) {
                    alpha = bestScore;
                } else if (!isMaxLevel && bestScore < beta) {
                    beta = bestScore;
                }
            }
        }
    	//System.out.println(bestScore);
		return bestMove;
    }
    
    public long calcValue(Heuristic h, BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem) {
        return calcValue(h, bs, depth, isMaxLevel, canPopUs, canPopThem, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    /**
     * Calculates score to given depth with provided heuristic. Has additional heuristic where it favors losing in more moves,
     * in the case that all moves are losing. Against a perfect player, this will have no effect, but against a player
     * that can make mistake, this gives us more breathing room.
     */
    public long calcValue(Heuristic h, BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem, long alpha, long beta) {
        //int hval = bs.genHVal(ps.config, isMaxLevel);
        long hval = h.calculate(bs);
        if (depth == 0 || hval == Integer.MAX_VALUE) {
            return hval + depth;
        } else if (hval == Integer.MIN_VALUE) {
            return hval - depth; // Favor losing later
        }
        
        long bestScore = isMaxLevel ? Long.MIN_VALUE : Long.MAX_VALUE;
        
        int playerToMove = isMaxLevel ? Player.US : Player.THEM; // We're always MAX
        boolean canPop = isMaxLevel ? canPopUs : canPopThem;
        
        for (int i = 0; i < bs.width; i++) {
            if (bs.board[0][i] == Player.NONE) {
                long next = calcValue(h, bs.nextBoard(i, Action.Place, playerToMove),
                        depth - 1, !isMaxLevel, canPopUs, canPopThem, alpha, beta);
                if (isMaxLevel ? (next >= bestScore) : (next <= bestScore)) {
                    bestScore = next;
                }
                
                if (isMaxLevel ? (bestScore >= beta) : (bestScore <= alpha)) {
                    return bestScore;
                }
                if (isMaxLevel && (bestScore > alpha)) {
                    alpha = bestScore;
                } else if (!isMaxLevel && bestScore < beta) {
                    beta = bestScore;
                }
            }
            if (canPop && bs.board[bs.height - 1][i] == playerToMove) {
                long next = calcValue(h, bs.nextBoard(i, Action.PopOut, playerToMove),
                        depth - 1, !isMaxLevel, isMaxLevel ? false : canPopUs, isMaxLevel ? canPopThem : false, alpha, beta);
                
                if (isMaxLevel ? (next >= bestScore) : (next <= bestScore)) {
                    bestScore = next;
                }
                
                
                if (isMaxLevel ? (bestScore >= beta) : (bestScore <= alpha)) {
                    return bestScore;
                }
                if (isMaxLevel && (bestScore > alpha)) {
                    alpha = bestScore;
                } else if (!isMaxLevel && bestScore < beta) {
                    beta = bestScore;
                }
            }
        }
        
        return bestScore;
    }
}
