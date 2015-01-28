package narow;

import narow.heuristics.Heuristic;
import narow.state.Action;
import narow.state.Player;

public class ID_DFS {

    PlayerState ps;
    
    //BoardState bestMove;
    
    //Stack<BoardState> movesToCheck;
    
    public ID_DFS(PlayerState playerState) {
        this.ps = playerState;
    }
    
    Move currentBestMove;
    boolean abort = false;
    
    public Move iterativeDeepeningBestMove(Heuristic h, BoardState bs, boolean canPopUs, boolean canPopThem) {
        for (int i = 1; ; i+= 2) {
            currentBestMove = findBestMove(h, bs, i, true, canPopUs, canPopThem); 
            if (currentBestMove.score == Integer.MAX_VALUE) return currentBestMove;
            if (abort) return currentBestMove;
        }
    }
    
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
    
    public long calcValue(Heuristic h, BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem, long alpha, long beta) {
        //int hval = bs.genHVal(ps.config, isMaxLevel);
        long hval = h.calculate(bs);
        if (depth == 0 || hval == Integer.MAX_VALUE) {
            return hval + depth;
        } else if (hval == Integer.MIN_VALUE) {
            return hval - depth;
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
