package narow;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class BoardStateBenchmark {

	@Rule
	public TestRule benchmarkRun = new BenchmarkRule();
	
	List<BoardState> bs;
	BoardState board;
	Config c = new Config("6 7 4 1 15");
	@Before
	public void setUp() {
	   
        
	}
	
	@BenchmarkOptions(benchmarkRounds = 10000, warmupRounds = 50)
	@Test
    public void testHorizontalWin() {
        for (BoardState b : new BoardState(7, 6).allLegalMoves(Player.US)){
            b.genHVal(c);
        }
    }
}
