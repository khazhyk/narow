package narow;

import static org.junit.Assert.assertEquals;
import narow.heuristics.HVal2Calculator;
import narow.heuristics.Heuristic;
import narow.state.Config;

import org.junit.Test;


public class HVal2Test {

    @Test
    public void testGame() {
    	
    	BoardState board = new BoardState(
    			"0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "0 0 0 0"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(10, h.calculate(board));
    }
    
    @Test
    public void testGame2() {
    	
    	BoardState board = new BoardState(
    			"0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "0 1 0 0"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(10, h.calculate(board));
    }
    
    @Test
    public void testGame3() {
    	
    	BoardState board = new BoardState(
    			"0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "0 2 0 0"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(8, h.calculate(board));
    }
    
    @Test
    public void testGame4() {
    	
    	BoardState board = new BoardState(
    			"0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "1 1 1 1"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(Integer.MAX_VALUE, h.calculate(board));
    }
    
    @Test
    public void testGame5() {
    	
    	BoardState board = new BoardState(
    			"0 0 0 0",
                "0 0 0 0",
                "0 0 0 0",
                "2 2 2 2"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(Integer.MIN_VALUE, h.calculate(board));
    }
    
    @Test
    public void testGame6() {
    	
    	BoardState board = new BoardState(
    			"0 0 0 2",
                "0 0 2 0",
                "0 2 0 0",
                "2 1 2 2"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(Integer.MIN_VALUE, h.calculate(board));
    }
    
    @Test
    public void testGame7() {
    	
    	BoardState board = new BoardState(
    			"2 0 0 0",
                "2 0 0 0",
                "2 0 0 0",
                "2 1 2 2"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(Integer.MIN_VALUE, h.calculate(board));
    }
    
    @Test
    public void testGame8() {
    	
    	BoardState board = new BoardState(
    			"2 0 0 0",
                "0 2 0 0",
                "0 0 2 0",
                "2 2 1 2"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(Integer.MIN_VALUE, h.calculate(board));
    }
    
    @Test
    public void testGame9() {
    	
    	BoardState board = new BoardState(
    			"2 1 0 0",
                "2 1 0 0",
                "2 1 2 0",
                "2 1 1 2"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(0, h.calculate(board));
    }
    
    @Test
    public void testGame10() {
    	
    	BoardState board = new BoardState(
    			"2 1 0 2",
                "2 1 0 2",
                "2 1 2 2",
                "2 1 1 2"
                );
    	
        Config c = new Config("4 4 4 1 15");
        
        Heuristic h = new HVal2Calculator(c);
        
        assertEquals(Integer.MIN_VALUE, h.calculate(board));
    }
    
}
