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
        
        assertEquals(Integer.MAX_VALUE, board.genHVal(c));
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
        
        assertEquals(Integer.MAX_VALUE, board.genHVal(c));
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
        
        assertEquals(Integer.MIN_VALUE, board.genHVal(c));
        assertEquals(Integer.MIN_VALUE, board2.genHVal(c));
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
        
        assertEquals(Integer.MAX_VALUE, board.genHVal(c));
        assertEquals(Integer.MAX_VALUE, board2.genHVal(c));
        assertEquals(Integer.MAX_VALUE, board3.genHVal(c));
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
        
        assertEquals(0, board.genHVal(c));
        assertEquals(Integer.MAX_VALUE, board2.genHVal(c));
        assertEquals(Integer.MAX_VALUE, board3.genHVal(c));
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
        
        assertEquals(0, board.genHVal(c));
        assertEquals(Integer.MAX_VALUE, board2.genHVal(c));
        assertEquals(Integer.MAX_VALUE, board3.genHVal(c));
    }
    
    @Test
    public void testTraverse() {
    	BoardState board3 = new BoardState(
                "0 0 0 0",
                "0 0 0 0",
                "0 1 0 0",
                "0 2 1 2",
                "0 1 2 1"
                );
        
        Config c = new Config("5 4 3 1 15");
        
        int[][]narow = board3.traverse(c);
        
        assertEquals(1, narow[0][2]); // 1 three in a row
        assertEquals(0, narow[1][2]);
        assertEquals(1, narow[0][1]); 
        assertEquals(1, narow[1][1]); // 2 has 2 two in a rows, but 1 of them isn't long enough to become a 3 in a row, so is ignored
        assertEquals(9, narow[0][0]); 
        assertEquals(8, narow[1][0]); 
    }
}
