package narow;

import static org.junit.Assert.assertEquals;
import narow.heuristics.CountNARowsCalculator;
import narow.heuristics.Heuristic;
import narow.state.Action;
import narow.state.Config;
import narow.state.Player;

import org.junit.Test;


public class BoardStateTest {

    @Test
    public void testGame() {
        BoardState board = new BoardState(2,2);
        Config c = new Config("2 2 2 1 15");
        
        Heuristic h = new CountNARowsCalculator(c);
        
        board = board.nextBoard(0, Action.Place, Player.US);
        board = board.nextBoard(0, Action.Place, Player.THEM);
        board = board.nextBoard(1, Action.Place, Player.US);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board));
    }
    
    
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
        Heuristic h = new CountNARowsCalculator(c);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board));
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
        Heuristic h = new CountNARowsCalculator(c);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board));
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
        Heuristic h = new CountNARowsCalculator(c);
        
        assertEquals(Integer.MIN_VALUE, h.calculate(board));
        assertEquals(Integer.MIN_VALUE, h.calculate(board2));
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
        Heuristic h = new CountNARowsCalculator(c);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board));
        assertEquals(Integer.MAX_VALUE, h.calculate(board2));
        assertEquals(Integer.MAX_VALUE, h.calculate(board3));
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
        Heuristic h = new CountNARowsCalculator(c);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board2));
        assertEquals(Integer.MAX_VALUE, h.calculate(board3));
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
        Heuristic h = new CountNARowsCalculator(c);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board2));
        assertEquals(Integer.MAX_VALUE, h.calculate(board3));
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
        CountNARowsCalculator h = new CountNARowsCalculator(c);
        
        int[][]narow = h.countNARows(board3);
        
        assertEquals(1, narow[0][2]); // 1 three in a rows
        assertEquals(0, narow[1][2]);
        assertEquals(1, narow[0][1]); 
        assertEquals(1, narow[1][1]); // 2 has 2 two in a rows, but 1 of them isn't long enough to become a 3 in a row, so is ignored
        assertEquals(9, narow[0][0]); 
        assertEquals(8, narow[1][0]); 
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

    	System.out.println(board.board[5][3]);
    	assertEquals(board.nextBoard(3, Action.PopOut, Player.US).board[5][3], Player.THEM);
    }
    
    @Test
    public void testExpHVal(){
    	BoardState board = new BoardState(
   			 	"0 0 0 0 0",
                "0 0 0 0 0",
                "0 0 0 0 0",
                "0 1 1 2 0"
                );
    	
    	Config c = new Config("4 5 4 1 15");
        assertEquals(12, board.genHVal2(c,2));
    }
}
