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

/**
 * This evaluation function counts the number of 1,2,3,4,...,n in a rows that we have, and they have, and weighs them.
 * Heavily favors longer combinations, and gives a heavy penalty for giving the opponent new moves. Takes into account 
 * who's turn it is when weighing, so it will assume the opponent will play in their own favor.
 *
 */
public class CountNARowsCalculator implements Heuristic {

    Config c;
    
    public CountNARowsCalculator(Config c) {this.c = c;};
    
    @Override
    public int calculate(BoardState bs) {
        final int[][] narow = countNARows(bs);
        
        boolean isOurTurn = bs.playerToMove == Player.US;
        
        if (narow[0][c.arow - 1] > 0 ^ narow[1][c.arow - 1] > 0) { // Exactly one winner
            return ((narow[0][c.arow - 1] > 0) ? Integer.MAX_VALUE : Integer.MIN_VALUE);
        } else if (narow[0][c.arow - 1] > 0 && narow[1][c.arow - 1] > 0) { // Zero on tie
            return 0;
        }
       
        int guess = 0;
        
        for (int i = c.arow - 1; i > 0; i--) {
            int ourMult = isOurTurn ? i*i*i : i*i*i*i*i; // Overflows at i=74, hopefully you never ask us to find 74-in-a-row :)
            int theirMult = isOurTurn ? i*i*i*i*i : i*i*i;
            
            guess += ourMult * narow[0][i-1] * ((isOurTurn) ? 2 : 1);
            guess -= theirMult * narow[1][i-1] * ((!isOurTurn) ? 2 : 1);
        }
        
        return guess;
    }
    
    /**
     * This counts the number of 1,2,3,... n in a rows for both players (index 0 is US, index 1 is THEM)
     * @param bs
     * @return
     */
    public int[][] countNARows(BoardState bs) {
        // horizontal, vertical, \, /
        int numInRow = 0;
        
        int[][] narow = new int[][] {
                new int[c.arow],
                new int[c.arow]
        };
        
        int lastP = Player.NONE;
        
        // Horizontal Test
        for (int x = 0; x < bs.height; x++) {
            lastP = Player.NONE; // Beginning of a row
            numInRow = 0;
            for (int y = 0; y < bs.width; y++) {
                int piece = bs.board[x][y];
                switch (piece) {
                    case Player.NONE:
                        if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
                        numInRow = 0;
                        break;
                    case Player.US:
                    case Player.THEM:
                        if (lastP == Player.NONE || lastP == piece) numInRow++;
                        else {
                            narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                            numInRow = 1;
                        }
                        break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
        }
        
        // Vertical Test
        for (int y = 0; y < bs.width; y++) {
            lastP = Player.NONE; // Beginning of a col
            numInRow = 0;
            vert:
            for (int x = bs.height - 1; x >= 0; x--) { // go from bottom to top
                int piece = bs.board[x][y];
                switch (piece) {
                    case Player.NONE:
                        if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                        lastP = Player.NONE;
                        break vert; // You can't have a piece on top of nothing. :)
                    case Player.US:
                    case Player.THEM:
                        if (lastP == Player.NONE || lastP == piece) numInRow++;
                        else {
                            narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                            numInRow = 1;
                        }
                        break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
        }
        
        // Diagonal \ Test
        /*
         * 0,0 0,1 0,2 0,3
         * 1,0 1,1 1,2 1,3
         * 2,0 2,1 2,2 2,3
         * 
         * for n = 2, test starting at 1,0; 0,0; 0,1; 0,2
         * 
         * Start at x=(bs.height - n + 1), go until 0,0, then go to y=(bs.width - n + 1)
         */
        
        // Going up to 0,0
        for (int i = bs.height - c.arow; i> 0; i--) {
            lastP = Player.NONE;
            numInRow = 0;
            for (int x = 0; x < bs.height - i; x++) {
                int piece = bs.board[x+i][x];
                switch (piece) {
                case Player.NONE:
                    if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                    numInRow = 0;
                    break;
                case Player.US:
                case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                        narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                        numInRow = 1;
                    };
                    break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
        }
        
        // Going Right from 0,0
        for (int i = 0; i <= bs.width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            
            // Diag \
            for (int x = 0; x < (bs.height) && x + i < bs.width; x++) {
                int piece = bs.board[x][x+i];
                switch (piece) {
                case Player.NONE:
                    if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                    numInRow = 0;
                    break;
                case Player.US:
                case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                        narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                        numInRow = 1;
                    }
                    break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
        }
        
        // Diagonal / Test
        
        /*
         * 0,0 0,1 0,2 0,3
         * 1,0 1,1 1,2 1,3
         * 2,0 2,1 2,2 2,3
         * 
         * for n = 2, test starting at 1,3; 0,3; 0,2; 0,1
         * 
         * start at y = bs.height - n + 1, x = bs.width - 1, 
         */
        for (int i = bs.height - c.arow; i> 0; i--) {
            lastP = Player.NONE;
            numInRow = 0;
            for (int x = 0; x < bs.height - i; x++) {
                int piece = bs.board[x+i][bs.width - 1 - x];
                switch (piece) {
                case Player.NONE:
                    if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                    numInRow = 0;
                    break;
                case Player.US:
                case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                        narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                        numInRow = 1;
                    }
                    break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
        }
        
        for (int i = 0; i <= bs.width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            
            // Diag /
            for (int x = 0;  x < (bs.height) && bs.width - 1 - x - i >= 0; x++) {
                int piece = bs.board[x][bs.width - 1 - x - i];
                switch (piece) {
                case Player.NONE:
                    if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                    numInRow = 0;
                    break;
                case Player.US:
                case Player.THEM:
                    if (lastP == Player.NONE || lastP == piece) numInRow++;
                    else {
                        narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++;
                        numInRow = 1;
                    }
                    break;
                }
                lastP = piece;
            }
            if (lastP != Player.NONE) narow[lastP == Player.US ? 0 : 1][Math.min(c.arow -1, numInRow - 1)]++; 
        }
        
        return narow;
    }

}
