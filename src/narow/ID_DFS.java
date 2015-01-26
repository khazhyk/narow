package narow;

public class ID_DFS {

    PlayerState ps;
    
    //BoardState bestMove;
    
    //Stack<BoardState> movesToCheck;
    
    public ID_DFS(PlayerState playerState) {
        this.ps = playerState;
    }
    
    public int calcValue(BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem) {
        return calcValue(bs, depth, isMaxLevel, canPopUs, canPopThem, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    public int calcValue(BoardState bs, int depth, boolean isMaxLevel, boolean canPopUs, boolean canPopThem, int alpha, int beta) {
        int hval = bs.genHVal(ps.config);
        if (depth == 0 || hval == Integer.MAX_VALUE || hval == Integer.MIN_VALUE) {
            return hval;
        }
        
        Move bestMove;
        int bestScore = isMaxLevel ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        
        int playerToMove = isMaxLevel ? Player.US : Player.THEM; // We're always MAX
        boolean canPop = isMaxLevel ? canPopUs : canPopThem;
        
        for (int i = 0; i < bs.width; i++) {
            if (bs.board[0][i] == Player.NONE) {
                int next = calcValue(bs.nextBoard(i, Action.Place, playerToMove),
                        depth - 1, !isMaxLevel, canPopUs, canPopThem, alpha, beta);
                
                if (isMaxLevel ? (next > bestScore) : (next < bestScore)) {
                    bestScore = next;
                    bestMove = new Move(i, Action.Place);
                }
                if (isMaxLevel ? (bestScore >= beta) : (bestScore <= alpha)) {
                    return bestScore;
                }
                if (isMaxLevel && (bestScore > alpha)) {
                    alpha = bestScore;
                } else if (bestScore < beta) {
                    beta = bestScore;
                }
            }
            if (canPop && bs.board[bs.height - 1][i] == playerToMove) {
                int next = calcValue(bs.nextBoard(i, Action.PopOut, playerToMove),
                        depth - 1, !isMaxLevel, isMaxLevel ? false : canPopUs, isMaxLevel ? canPopThem : false, alpha, beta);
                
                if (isMaxLevel ? (next > bestScore) : (next < bestScore)) {
                    bestScore = next;
                    bestMove = new Move(i, Action.PopOut);
                }
                if (isMaxLevel ? (bestScore >= beta) : (bestScore <= alpha)) {
                    return bestScore;
                }
                if (isMaxLevel && (bestScore > alpha)) {
                    alpha = bestScore;
                } else if (bestScore < beta) {
                    beta = bestScore;
                }
            }
        }
        
        return bestScore;
    }
}
