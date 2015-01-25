package narow;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class BoardStateBenchmark {

	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();
	
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0)
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
        
        assertEquals(Integer.MAX_VALUE, board.genHVal(c, Player.ONE));
    }
}
