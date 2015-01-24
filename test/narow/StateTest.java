package narow;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class StateTest {
	
	@Test
	public void testmake() {
		BoardState teststate = new BoardState(6,7);
		org.junit.Assert.assertEquals(teststate.height, 6);
		org.junit.Assert.assertEquals(teststate.width, 7); 
		//System.out.println(Arrays.deepToString(teststate.board));
		//teststate.nextBoard(3,1,new Boolean(true));
		//System.out.println(Arrays.deepToString(teststate.board));
		//teststate.nextBoard(3,1,new Boolean(false));
		//System.out.println(Arrays.deepToString(teststate.board));
		Player[][] testboardarray = new Player[6][7];
		BoardState teststate2 = new BoardState(testboardarray);
		org.junit.Assert.assertEquals(teststate2.height, 6);
		org.junit.Assert.assertEquals(teststate2.width, 7); 
	}
	
	StateNode testSN1 = new StateNode(2);
	StateNode testSN2 = new StateNode(1);
	StateNode testSN3 = new StateNode(3);
	StateNode testSN6 = new StateNode(4);
	StateNode testSN7 = new StateNode(5);
	StateNode testSN4 = new StateNode(new BoardState(6,7), new Boolean(true));
	StateNode testSN5 = new StateNode(new BoardState(6,7), new Boolean(false));
	StateNode testSN8 = new StateNode(new BoardState(6,7), new Boolean(true));
	
	/*@Test
	public void testminimax1(){
		org.junit.Assert.assertEquals(testSN1.calcMiniMax(), 2); 
	}
	
	@Test
	public void testminimax2(){
		testSN4.nextState.add(testSN1);
		testSN4.nextState.add(testSN2);
		testSN4.nextState.add(testSN3);
		org.junit.Assert.assertEquals(testSN4.calcMiniMax(), 3); 
	}
	
	@Test
	public void testminimax3(){
		testSN5.nextState.add(testSN1);
		testSN5.nextState.add(testSN2);
		testSN5.nextState.add(testSN3);
		org.junit.Assert.assertEquals(testSN5.calcMiniMax(), 1);
	}
	
	@Test
	public void testminimax4(){
		testSN4.nextState.add(testSN1);
		testSN4.nextState.add(testSN2);
		testSN5.nextState.add(testSN6);
		testSN5.nextState.add(testSN7);
		testSN4.nextState.add(testSN5);
		testSN5.calcMiniMax();
		org.junit.Assert.assertEquals(testSN4.calcMiniMax(), 4); 
	}
	
	@Test
	public void testminimax5(){
		testSN4.nextState.add(testSN6);
		testSN4.nextState.add(testSN2);
		testSN8.nextState.add(testSN1);
		testSN8.nextState.add(testSN3);
		testSN5.nextState.add(testSN4);
		testSN5.nextState.add(testSN8);
		org.junit.Assert.assertEquals(testSN5.calcMiniMax(), 3); 
	}*/
	
	BoardState testboard1 = new BoardState(3,3);
	StateNode testState1 = new StateNode(testboard1, new Boolean(true));
	
	
	
	@Test 
	public void testnextBoard(){
		//System.out.println(Arrays.deepToString(testboard1.board));
		//BoardState outboard1 = testboard1.nextBoard(1, 1, new Boolean(true));
		//System.out.println(Arrays.deepToString(outboard1.board));
		//outboard1 = testboard1.nextBoard(1, 1, new Boolean(false));
		//System.out.println(Arrays.deepToString(testboard1.board));
	}
	
/*	@Test
	public void textmakeboardtree(){
		testState1.makeMoveTree(2);
	}*/
	
	BoardState testboard2 = new BoardState(
			"0 0 0 0 0",
            "0 0 0 0 0",
            "0 0 0 0 0",
            "0 0 0 0 0"
            );
	StateNode testState2 = new StateNode(testboard2, new Boolean(true));
	
	@Test
	public void testminimax(){
		testState2.Minimax(5);
		assertEquals(testState2.nextMove, 2);
	}

}
