package narow;

import static org.junit.Assert.*;

import org.junit.Test;

public class ID_DFSTest {

    @Test
    public void test2InARow() {
        BoardState board = new BoardState(2,2);
        
        PlayerState p = new PlayerState();
        p.config = new Config("2 2 2 1 15");
        
        ID_DFS id = new ID_DFS(p);
        
        assertEquals(Integer.MAX_VALUE, id.calcValue(board, Integer.MAX_VALUE, true, true, true));
    }
    
    @Test
    public void test2InARow3by3() {
        BoardState board = new BoardState(3,3);
        
        PlayerState p = new PlayerState();
        p.config = new Config("3 3 2 1 15");
        
        ID_DFS id = new ID_DFS(p);
        
        assertEquals(Integer.MAX_VALUE, id.calcValue(board, Integer.MAX_VALUE, true, true, true));
    }
    
    @Test
    public void testCenterBestMove() {
    	BoardState board = new BoardState(6,7);
    	board.playerToMove = Player.US;
        
        PlayerState p = new PlayerState();
        p.config = new Config("6 7 4 1 15");
        
        ID_DFS id = new ID_DFS(p);
        
        assertEquals(3, id.findBestMove(board, 5, true, true, true).column);
    }
    
    @Test
    public void testBestMove() {
    	BoardState board = new BoardState(
    			"0 0 0 0 0 0 0",
    			"0 0 0 0 0 0 0",
    			"0 0 0 0 0 0 0",
    			"0 0 0 0 0 0 0",
    			"0 2 2 2 0 0 0",
    			"0 1 1 1 0 0 0");
    	board.playerToMove = Player.US;
        
        PlayerState p = new PlayerState();
        p.config = new Config("6 7 4 1 15");
        
        ID_DFS id = new ID_DFS(p);
        
        assertEquals(0, id.findBestMove(board, 1, true, true, true).column);
    }
    
    @Test
    public void testBest2Move() {
    	BoardState bs = new BoardState(
    			"9 9 9 9 9 9 9",
    			"9 9 9 9 1 9 9",
    			"9 9 9 9 2 9 9",
    			"9 9 9 1 2 9 9",
    			"9 9 9 2 1 9 2",
    			"1 1 9 1 1 9 2");
    	
    	BoardState bsb = new BoardState(
    			"9 9 9 9 9 9 9",
    			"9 9 9 9 1 9 9",
    			"9 9 9 9 2 9 9",
    			"9 9 9 1 2 9 9",
    			"1 9 9 2 1 9 2",
    			"1 1 9 1 1 9 2");
    	
    	BoardState bsg = new BoardState(
    			"9 9 9 9 9 9 9",
    			"9 9 9 9 1 9 9",
    			"9 9 9 9 2 9 9",
    			"9 9 9 1 2 9 9",
    			"9 9 9 2 1 9 2",
    			"1 1 1 1 1 9 2");
    	PlayerState p = new PlayerState();
    	p.config = new Config("6 7 4 1 15");
    	
    	ID_DFS id = new ID_DFS(p);
        
    	assertNotEquals(Integer.MAX_VALUE, bsb.genHVal(p.config, false));
    	assertEquals(Integer.MAX_VALUE, bsg.genHVal(p.config, false));
        assertEquals(2, id.findBestMove(bs, 9, true, true, true).column);
    }
    
    @Test
    public void test3InARow4by4() {
        BoardState board = new BoardState(4,4);
        
        PlayerState p = new PlayerState();
        p.config = new Config("4 4 3 1 15");
        
        ID_DFS id = new ID_DFS(p);
        
        assertEquals(Integer.MAX_VALUE, id.calcValue(board, Integer.MAX_VALUE, true, true, true));
    }
}
