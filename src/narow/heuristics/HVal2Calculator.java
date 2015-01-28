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
import narow.state.Config;
import narow.state.Player;

public class HVal2Calculator implements Heuristic {

    Config c;
    public HVal2Calculator(Config c) { this.c = c; }
    
    @Override
    public int calculate(BoardState bs) {
        int openruns = 0;
        //open horizontal runs
        for (int h = 0; h<bs.height; h++){
        for (int w = 0; w<bs.width-c.arow+1; w++){
            Boolean runIsClear = true;
            Boolean terminal = true;
            int lastPlayer = bs.board[h][w];
            for (int x = 0; x<c.arow; x++){
                if (bs.board[h][w+x] != Player.US && bs.board[h][w+x] !=0){
                    runIsClear = false;
                }   
                if (bs.board[h][w+x]!=lastPlayer || lastPlayer==Player.NONE){
                	lastPlayer = bs.board[h][w+x];
                	terminal = false;
                }
                			
            }
            if (runIsClear)
                openruns++;
            if (terminal){
            	if (lastPlayer == 1)
            		return Integer.MAX_VALUE;
            	if (lastPlayer == 2)
            		return Integer.MIN_VALUE;
            }
        }
        }

        // open horizontal runs
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
                		return Integer.MAX_VALUE;
                	if (lastPlayer == 2)
                		return Integer.MIN_VALUE;
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
                		return Integer.MAX_VALUE;
                	if (lastPlayer == 2)
                		return Integer.MIN_VALUE;
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
                		return Integer.MAX_VALUE;
                	if (lastPlayer == 2)
                		return Integer.MIN_VALUE;
                }
            }
        }

        return openruns;
    }

}
