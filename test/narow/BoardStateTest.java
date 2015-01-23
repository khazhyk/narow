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
                "1 1 1 1 2 0 0"
                );
        
        Config c = new Config("6 7 4 1 15");
        
        assertEquals(Integer.MAX_VALUE, board.traverse(c));
    }
}
