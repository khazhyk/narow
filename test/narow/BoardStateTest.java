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
                "0 0 1 1 2 1 2",
                "0 0 2 1 2 1 2"
                );
        
        
        Config c = new Config("6 7 4 1 15");
        
        assertEquals(Integer.MIN_VALUE, board.traverse(c));
        assertEquals(Integer.MIN_VALUE, board2.traverse(c));
    }
    
    @Test
    public void testhval(){
    	 BoardState board = new BoardState(
    			 "0 0 0 0 0 0 0",
                 "0 0 0 0 0 0 0",
                 "0 0 0 0 0 0 0",
                 "0 0 0 0 0 0 0",
                 "0 0 2 2 0 0 0",
                 "0 0 2 1 1 1 1"
                 );
    	 assertEquals((int) board.hval(), 2);
    	 BoardState board2 = new BoardState(
    			 "0 0 0 0 0 0 0",
                 "0 0 0 0 0 0 0",
                 "0 0 0 0 0 0 0",
                 "0 0 2 0 0 0 0",
                 "0 0 2 2 0 0 0",
                 "0 0 2 1 1 1 1"
                 );
    	 assertEquals((int) board2.hval(), 3);
    }
    
    @Test
    public void testPopout(){
    	BoardState board = new BoardState(
   			 	"0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 2 0 0 0 0",
                "0 0 2 2 0 0 0",
                "0 0 2 1 1 1 1"
                );
    	BoardState boardresult = new BoardState(
   			 	"0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 0 0 0 0 0",
                "0 0 2 0 0 0 0",
                "0 0 2 0 0 0 0",
                "0 0 2 2 1 1 1"
                );
    	/*for (int i = 0; i<board.width; i++){
    		for (int j = 0; j<board.height; j++){
    			System.err.print(board.nextBoard(3, Action.PopOut, false).board[i][j]+"  ");
    		}
    		System.err.println();
    	}*/
    	/*board.nextBoard(3, Action.PopOut, false);
    	System.out.println(board.board[5][6]);
    	System.out.println(board.height+"   "+ board.width);
*/
    	System.out.println(board.board[5][3]);
    	assertEquals(board.nextBoard(3, Action.PopOut, false).board[5][3], Player.TWO);
    }
}
