/**
 * CS4341 - Project 1 Games
 * Professor Neil Heffernan
 * 
 * Lillian Walker
 * Khazhismel Kumykov
 * 
 */
package narow.heuristics;

import narow.BoardState;
import narow.formats.Config;
import narow.formats.Player;

public class HVal2Calculator implements Heuristic {

    Config c;
    public HVal2Calculator(Config c) { this.c = c; }
    
    
    /**
     * calculate the heuristic for the board bs based on the number of available runs in the board. 
     * Also a terminal check
     */
    @Override
    public int calculate(BoardState bs) {
        int openruns = 0;
        int player1Wins = 0;
        int player2Wins = 0;
        
        //count open horizontal runs
        for (int h = 0; h<bs.height; h++){
        for (int w = 0; w<bs.width-c.arow+1; w++){
            Boolean runIsClear = true;
            Boolean terminal = true;
            int lastPlayer = bs.board[h][w];
            for (int x = 0; x<c.arow; x++){
            	//if the other player has a token, dont count as open
                if (bs.board[h][w+x] != Player.US && bs.board[h][w+x] !=0){
                    runIsClear = false;
                }   
                // if the player changes during the run of arow, it must not be terminal
                if (bs.board[h][w+x]!=lastPlayer || lastPlayer==Player.NONE){
                	lastPlayer = bs.board[h][w+x];
                	terminal = false;
                }
                			
            }
            //if flag is true, add a point
            if (runIsClear)
                openruns++;
            // if terminal, give a point to the winner
            if (terminal){
            	if (lastPlayer == 1)
            		player1Wins++;
            	if (lastPlayer == 2)
            		player2Wins++;
            }
        }
        }

        // open vertical runs
        for (int h = 0; h < bs.height - c.arow + 1; h++) {
            for (int w = 0; w < bs.width; w++) {
                Boolean runIsClear = true;
                Boolean terminal = true;
                int lastPlayer = bs.board[h][w];
                for (int x = 0; x < c.arow; x++) {
                    if (bs.board[h + x][w] != Player.US && bs.board[h + x][w] != 0) {
                        runIsClear = false;
                    }
                    if (bs.board[h + x][w]!=lastPlayer|| lastPlayer==Player.NONE){
                    	lastPlayer = bs.board[h + x][w];
                    	terminal = false;
                    }
                }
                if (runIsClear)
                    openruns++;
                if (terminal){
                	if (lastPlayer == 1)
                		player1Wins++;
                	if (lastPlayer == 2)
                		player2Wins++;
                }
            }
        }

        // opendiagonals (left to right)
        for (int w = 0; w < bs.width - c.arow + 1; w++) {
            for (int h = 0; h < bs.height - c.arow + 1; h++) {
                Boolean runIsClear = true;
                Boolean terminal = true;
                int lastPlayer = bs.board[h][w];
                for (int x = 0; x < c.arow; x++) {
                    if (bs.board[h + x][w + x] != Player.US
                            && bs.board[h + x][w + x] != 0) {
                        runIsClear = false;
                    }
                    if (bs.board[h + x][w + x]!=lastPlayer|| lastPlayer==Player.NONE){
                    	lastPlayer = bs.board[h + x][w + x];
                    	terminal = false;
                    }
                }
                if (runIsClear)
                    openruns++;
                if (terminal){
                	if (lastPlayer == 1)
                		player1Wins++;
                	if (lastPlayer == 2)
                		player2Wins++;
                }
            }
        }

        // opendiagonals (right to left)
        for (int w = c.arow-1; w < bs.width; w++) {
            for (int h = 0; h < bs.height - c.arow + 1; h++) {
                Boolean runIsClear = true;
                Boolean terminal = true;
                int lastPlayer = bs.board[h][w];
                for (int x = 0; x < c.arow; x++) {
                    if (bs.board[h + x][w - x] != Player.US
                            && bs.board[h + x][w - x] != 0) {
                        runIsClear = false;
                    }
                    if (bs.board[h + x][w - x]!=lastPlayer|| lastPlayer==Player.NONE){
                    	lastPlayer = bs.board[h + x][w - x];
                    	terminal = false;
                    }
                }
                if (runIsClear)
                    openruns++;
                if (terminal){
                	if (lastPlayer == 1)
                		player1Wins++;
                	if (lastPlayer == 2)
                		player2Wins++;
                }
            }
        }
// if theres a tie, nobody wins
        if (player1Wins == player2Wins && player1Wins > 0)
        	return 0;
        // if player one has more points, they win, return max value because this is very desirable
        if (player1Wins > player2Wins)
        	return Integer.MAX_VALUE;
     // if player two has more points, they win, return min value because this is very not desirable
        if (player1Wins < player2Wins)
        	return Integer.MIN_VALUE;
        // if theres no win, just return the number of open runs
        return openruns;
    }

}
