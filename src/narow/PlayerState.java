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
	
	Thread moveThread = new Thread();
	
	List<BoardState> bestPath;
	
	/**
	 * Update the board
	 * @param column
	 * @param action
	 * @param isPlayerOne True if player 1 is acting, false otherwise
	 */
	void updateBoardThem(int column, int action) {
		if (action == Action.PopOut) theyUsedPopout = true;
	    bs = bs.nextBoard(column, action, Player.THEM);
	}
	
	
	/**
	 * We need to calculate until time runs out, then send the move
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
    void makeMove() throws IOException {
		Runnable searchForMove = new Runnable() {
		    @Override
		    public void run() {
		        search.iterativeDeepeningBestMove(bs, !weUsedPopout, !theyUsedPopout);
		    }
		};
		
        Thread thread = new Thread(searchForMove);
        thread.start();
		try {
            thread.join(1000*config.timelimit - 100); // 100ms breathing room
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
		
		thread.interrupt();
		thread.stop();
		
		Move move = search.currentBestMove;
		System.out.println(move);
		if (move.action == Action.PopOut) weUsedPopout = true;
		bs = bs.nextBoard(move.column, move.action, Player.US);
	}
}
