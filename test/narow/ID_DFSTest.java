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
}
