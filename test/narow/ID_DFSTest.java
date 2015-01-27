package narow;

import static org.junit.Assert.assertEquals;
import narow.heuristics.CountNARowsCalculator;
import narow.heuristics.Heuristic;
import narow.state.Action;
import narow.state.Config;
import narow.state.Player;

import org.junit.Test;

public class ID_DFSTest {

    @Test
    public void test2InARow() {
        BoardState board = new BoardState(2,2);
        
        PlayerState p = new PlayerState();
        p.config = new Config("2 2 2 1 15");
        
        ID_DFS id = new ID_DFS(p);
        final Heuristic h = new CountNARowsCalculator(p.config);
        
        assertEquals(Integer.MAX_VALUE, id.calcValue(h, board, Integer.MAX_VALUE, true, true, true));
    }
    
    @Test
    public void test2InARow3by3() {
        BoardState board = new BoardState(3,3);
        
        PlayerState p = new PlayerState();
        p.config = new Config("3 3 2 1 15");
        
        ID_DFS id = new ID_DFS(p);
        final Heuristic h = new CountNARowsCalculator(p.config);
        
        assertEquals(Integer.MAX_VALUE, id.calcValue(h, board, Integer.MAX_VALUE, true, true, true));
    }
    
    @Test
    public void testCenterBestMove() {
    	final BoardState board = new BoardState(6,7);
    	board.playerToMove = Player.US;
        
        final PlayerState p = new PlayerState();
        p.config = new Config("6 7 4 1 1");
        
        final ID_DFS id = new ID_DFS(p);
        final Heuristic h = new CountNARowsCalculator(p.config);
        
        Runnable searchForMove = new Runnable() {
            @Override
            public void run() {
                id.iterativeDeepeningBestMove(h, board, true, true);
            }
        };

        Thread thread = new Thread(searchForMove);
        thread.start();
        
        try {
            thread.join(1000*p.config.timelimit);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        
        assert(thread.getState() == Thread.State.TERMINATED);
        
        Move move = id.currentBestMove;
        assertEquals(3, move.column);
    }

    @Test
    public void testBest2Move() {
    	final BoardState bs = new BoardState(
    			"9 9 9 9 9 9 9",
    			"9 9 9 9 1 9 9",
    			"9 9 9 9 2 9 9",
    			"9 9 9 1 2 9 9",
    			"9 9 9 2 1 9 2",
    			"1 1 9 1 1 9 2");
    	PlayerState p = new PlayerState();
    	p.config = new Config("6 7 4 1 15");
    	
    	final ID_DFS id = new ID_DFS(p);
    	final Heuristic h = new CountNARowsCalculator(p.config);
        
    	//assertNotEquals(Integer.MAX_VALUE, bsb.genHVal(p.config, false));
    	//assertEquals(Integer.MAX_VALUE, bsg.genHVal(p.config, false));
        assertEquals(2, id.findBestMove(h, bs, 3, true, true, true).column);

        Runnable searchForMove = new Runnable() {
            @Override
            public void run() {
                id.iterativeDeepeningBestMove(h, bs, true, true);
            }
        };
        Thread thread = new Thread(searchForMove);
        thread.start();
        
    	try {
            thread.join(1000*p.config.timelimit);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
    	
    	assert(thread.getState() == Thread.State.TERMINATED);
        
        Move move = id.currentBestMove;
        assertEquals(2, move.column);
    }
    
    @Test
    public void test3InARow4by4() {
        BoardState board = new BoardState(4,4);
        
        PlayerState p = new PlayerState();
        p.config = new Config("4 4 3 1 15");
        
        ID_DFS id = new ID_DFS(p);
        final Heuristic h = new CountNARowsCalculator(p.config);
        
        assertEquals(Integer.MAX_VALUE, id.calcValue(h, board, Integer.MAX_VALUE, true, true, true));
    }
    
    @Test
    public void testinvalidmove() {
        final BoardState bs = new BoardState(
                "9 9 9 9 9 9 9",
                "9 9 9 9 9 9 9",
                "9 9 9 9 9 9 1",
                "9 9 9 1 9 2 2",
                "9 9 2 2 9 1 2",
                "9 9 2 1 9 1 2");
        PlayerState p = new PlayerState();
        p.config = new Config("6 7 4 1 3");
        
        final ID_DFS id = new ID_DFS(p);
        final Heuristic h = new CountNARowsCalculator(p.config);
        
        Runnable searchForMove = new Runnable() {
            @Override
            public void run() {
                id.iterativeDeepeningBestMove(h, bs, false, true);
            }
        };
        Thread thread = new Thread(searchForMove);
        thread.start();
        
        try {
            thread.join(1000*p.config.timelimit - 100);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        
        assert(thread.getState() == Thread.State.TERMINATED);
        
        Move move = id.currentBestMove;
        assertEquals(Action.Place, move.action);
    }
    
    @Test
    public void testBlock() {
        final BoardState bs = new BoardState(
                "9 9 9 9 9 9 9",
                "9 9 9 9 9 9 9",
                "9 9 9 9 9 9 9",
                "9 9 9 9 9 9 9",
                "9 2 2 2 9 1 9",
                "9 1 2 2 2 1 1"
                );
        PlayerState p = new PlayerState();
        p.config = new Config("6 7 4 1 3");
        
        final ID_DFS id = new ID_DFS(p);
        final Heuristic h = new CountNARowsCalculator(p.config);
        
        Move move3 = id.findBestMove(h, bs, 3, true, true, true);
        assertEquals(Action.Place, move3.action);
        assertEquals(4, move3.column);
        
        assertEquals(Integer.MIN_VALUE, id.calcValue(h, bs, 5, true, true, true)); // RIP :(
        assertEquals(Integer.MIN_VALUE, id.calcValue(h, bs, 7, true, true, true));
        
    }
}
