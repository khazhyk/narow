package narow;

import java.io.IOException;
import java.util.List;
import java.util.Random;

class PlayerState {
	
	Config config;
	String playerName = "lwalker_kkumykov_" + Long.toHexString(new java.security.SecureRandom().nextLong());
	Random random = new Random();
	boolean arePlayerOne;
	
	boolean weUsedPopout;
	boolean theyUsedPopout;
	
	BoardState bs;
	
	ID_DFS search = new ID_DFS(this);
	
	List<BoardState> bestPath;
	
	/**
	 * Update the board
	 * @param column
	 * @param action
	 * @param isPlayerOne True if player 1 is acting, false otherwise
	 */
	void updateBoard(int column, int action, int player) {
		if (action == Action.PopOut) theyUsedPopout = true;
	    bs = bs.nextBoard(column, action, player);
	}
	
	
	/**
	 * We need to calculate until time runs out, then send the move
	 * @throws IOException
	 */
	void makeMove() throws IOException {
		Move move = search.findBestMove(bs, 4, true, !weUsedPopout, !theyUsedPopout);
		
		if (move.action == Action.PopOut) weUsedPopout = true;
		System.out.println(move);
	}
}
