package narow;

public class ID_DFS {

    PlayerState ps;
    
    //BoardState bestMove;
    
    //Stack<BoardState> movesToCheck;
    
    public ID_DFS(PlayerState playerState) {
        this.ps = playerState;
    }
    
    Move currentBestMove;
    boolean abort = false;
    
    public Move iterativeDeepeningBestMove(BoardState bs, boolean canPopUs, boolean canPopThem) {
        for (int i = 1; ; i++) {
            currentBestMove = findBestMove(bs, i, true, canPopUs, canPopThem);
            if (currentBestMove.score == Integer.MAX_VALUE) return currentBestMove;
            if (abort) return currentBestMove;
        }
    }
    
    public Move findBestMove(BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem) {
    	Move bestMove = null;
    	int bestScore = isMaxLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    	
    	int playerToMove = isMaxLevel ? Player.US : Player.THEM;
    	boolean canPop = isMaxLevel ? canPopUs : canPopThem;
    	int alpha = Integer.MIN_VALUE;
    	int beta = Integer.MAX_VALUE;
    	
    	for (int i = 0; i < bs.width; i++) {
            if (bs.board[0][i] == Player.NONE) {
                int next = calcValue(bs.nextBoard(i, Action.Place, playerToMove),
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
                int next = calcValue(bs.nextBoard(i, Action.PopOut, playerToMove),
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
    	
		return bestMove;
    }
    
    public int calcValue(BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem) {
        return calcValue(bs, depth, isMaxLevel, canPopUs, canPopThem, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public int calcValue(BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem, int alpha, int beta) {
        int hval = bs.genHVal(ps.config, isMaxLevel);
        if (depth == 0 || hval == Integer.MAX_VALUE || hval == Integer.MIN_VALUE) {
            return hval;
        }
        
        int bestScore = isMaxLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        
        int playerToMove = isMaxLevel ? Player.US : Player.THEM; // We're always MAX
        boolean canPop = isMaxLevel ? canPopUs : canPopThem;
        
        for (int i = 0; i < bs.width; i++) {
            if (bs.board[0][i] == Player.NONE) {
                int next = calcValue(bs.nextBoard(i, Action.Place, playerToMove),
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
                int next = calcValue(bs.nextBoard(i, Action.PopOut, playerToMove),
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
