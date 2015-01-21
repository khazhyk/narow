package narow;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class StateTest {
	
	@Test
	public void testmake() {
		BoardState teststate = new BoardState(6,7);
		//System.out.println(Arrays.deepToString(teststate.board));
		teststate.updateBoard(3,1,new Boolean(true));
		//System.out.println(Arrays.deepToString(teststate.board));
		teststate.updateBoard(3,1,new Boolean(false));
		//System.out.println(Arrays.deepToString(teststate.board));
	}
	
	StateNode testSN1 = new StateNode(2);
	StateNode testSN2 = new StateNode(1);
	StateNode testSN3 = new StateNode(3);
	StateNode testSN6 = new StateNode(4);
	StateNode testSN7 = new StateNode(5);
	StateNode testSN4 = new StateNode(new BoardState(6,7), new Boolean(true));
	StateNode testSN5 = new StateNode(new BoardState(6,7), new Boolean(false));
	StateNode testSN8 = new StateNode(new BoardState(6,7), new Boolean(true));
	
	@Test
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
		//System.out.println(testSN5.nextState);
		testSN5.calcMiniMax();
		org.junit.Assert.assertEquals(testSN4.calcMiniMax(), 4); 
		//System.out.println("test done");
		//System.out.println(testSN4.minimaxscore);
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
	}
	
	BoardState testboard1 = new BoardState(2,2);
	StateNode testState1 = new StateNode(testboard1, new Boolean(true));
	
	@Test 
	public void testnextBoard(){
		testboard1.nextBoard(1, 1, new Boolean(true));
		System.out.println(Arrays.deepToString(testboard1.board));
		testboard1.nextBoard(1, 1, new Boolean(false));
		System.out.println(Arrays.deepToString(testboard1.board));
	}
	
//	@Test
//	public void testTree1(){
//		testState1.makeMoveTree(1);
//		assertEquals(testState1.nextState.size(),2);
//	}
//	
//	@Test
//	public void testTree2(){
//		System.out.println("testTree2");
//		testState1.makeMoveTree(2);
//		assertEquals(testState1.nextState.size(),2);
//		assertEquals(testState1.nextState.get(0).nextState.size(),2);
//	}

}
