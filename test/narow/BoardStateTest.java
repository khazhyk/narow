package narow;

import static org.junit.Assert.*;
import org.junit.Test;


public class BoardStateTest {

    @Test
    public void testHorizontalWin() {
        BoardState board = new BoardState(
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 2 2 0 0 0",
                "0 0 2 1 1 1 1"
                );
        
        Config c = new Config("6 7 4 1 15");
        
        assertEquals(Integer.MAX_VALUE, board.traverse(c));
    }
    
    @Test
    public void testVertWin() {
        BoardState board = new BoardState(
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 1 0 0 0",
                "0 0 0 1 2 0 0",
                "0 0 0 1 2 0 0",
                "0 0 0 1 2 0 0"
                );
        
        Config c = new Config("6 7 4 1 15");
        
        assertEquals(Integer.MAX_VALUE, board.traverse(c));
    }
    
    @Test
    public void testDiagWin() {
        BoardState board = new BoardState(
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 2 0 0 0 0",
                "0 0 1 2 1 0 0",
                "0 0 1 1 2 2 0",
                "0 0 1 1 2 2 0"
                );
        
        BoardState board2 = new BoardState(
                "0 0 0 2 0 0 0",
                "0 0 0 1 2 1 0",
                "0 0 0 2 1 2 1",
                "0 0 0 1 2 1 2",
                "0 0 0 1 2 1 2",
                "0 0 0 1 2 1 2"
                );
        
        
        Config c = new Config("6 7 4 1 15");
        
        assertEquals(Integer.MIN_VALUE, board.traverse(c));
        assertEquals(Integer.MIN_VALUE, board2.traverse(c));
    }
    
    @Test
    public void testDiag2Win() {
        BoardState board = new BoardState(
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 1",
                "0 0 0 0 1 1 2",
                "0 0 0 0 1 1 2",
                "0 0 0 1 2 2 2"
                );
        
        BoardState board2 = new BoardState(
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 1 0 0 0",
                "0 0 1 2 0 0 0",
                "0 1 2 2 0 0 0",
                "1 2 1 1 2 1 2"
                );
        
        BoardState board3 = new BoardState(
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 0 1 0 0",
                "0 0 0 1 2 0 0",
                "0 0 1 1 2 0 0",
                "0 1 2 2 2 1 0"
                );
        
        Config c = new Config("6 7 4 1 15");
        
        assertEquals(Integer.MAX_VALUE, board.traverse(c));
        assertEquals(Integer.MAX_VALUE, board2.traverse(c));
        assertEquals(Integer.MAX_VALUE, board3.traverse(c));
    }
    
    @Test
    public void testSmallerBoardWin() {
        BoardState board = new BoardState(
                "0 0 0 0",
                "0 0 0 0",
                "0 0 0 0"
                );
        
        BoardState board2 = new BoardState(
                "0 0 0 1",
                "0 1 1 2",
                "0 1 2 2"
                );
        
        BoardState board3 = new BoardState(
                "0 0 0 0",
                "0 2 2 0",
                "1 1 1 0"
                );
        
        Config c = new Config("3 4 3 1 15");
        
        assertEquals(0, board.traverse(c));
        assertEquals(Integer.MAX_VALUE, board2.traverse(c));
        assertEquals(Integer.MAX_VALUE, board3.traverse(c));
    }
    
    @Test
    public void testTallWin() {
    	BoardState board = new BoardState(
                "0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "0 0 0 0"
                );
    	BoardState board2 = new BoardState(
                "0 0 0 0",
                "1 0 0 0",
                "2 1 2 1",
                "1 2 1 2",
                "1 2 1 2"
                );
    	BoardState board3 = new BoardState(
                "0 0 0 0",
                "0 0 0 0",
                "0 1 0 0",
                "0 2 1 2",
                "0 1 2 1"
                );
        
        
        Config c = new Config("5 4 3 1 15");
        
        assertEquals(0, board.traverse(c));
        assertEquals(Integer.MAX_VALUE, board2.traverse(c));
        assertEquals(Integer.MAX_VALUE, board3.traverse(c));
    }
}
