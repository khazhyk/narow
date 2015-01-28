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
 * This eval function checks for the number of "open" n-in-a-row slots available to US.
 * It also implements terminal checking inline so we only have to loop once through the board.
 *
 */
public class PossibleNARowsCalculator implements Heuristic {

    Config c;
    
    public PossibleNARowsCalculator(Config c) { this.c = c;}
    
    int availNInRow,
            usActualNInRow,
            themActualNInRow,
            lastP,
            numInRow,
            numActualInRow;
    
    public int calculate(BoardState bs) {
        // Initialize
        availNInRow = 0;
        usActualNInRow = 0;
        themActualNInRow = 0;
        lastP = 0;
        numInRow = 0;
        numActualInRow = 0;
        
        // Horizontal
        for (int x = 0; x < bs.height; x++) {
            lastP = Player.NONE;
            numInRow = 0;
            numActualInRow = 0;
            for (int y = 0; y < bs.width; y++) {
                doCalc(bs, x, y);
            }
        }
        
        // Vertical Test
        for (int y = 0; y < bs.width; y++) {
            lastP = Player.NONE; // Beginning of a col
            numInRow = 0;
            numActualInRow = 0;
            for (int x = bs.height - 1; x >= 0; x--) { // go from bottom to top
                doCalc(bs, x, y);
            }
        }
        
        // Diagonal \ Test
        
        // Going up to 0,0
        for (int i = bs.height - c.arow; i> 0; i--) {
            lastP = Player.NONE;
            numInRow = 0;
            numActualInRow = 0;
            for (int x = 0; x < bs.height - i; x++) {
                doCalc(bs,x+i,x);
            } 
        }
        // Going Right from 0,0
        for (int i = 0; i <= bs.width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            numActualInRow = 0;
            for (int x = 0; x < (bs.height) && x + i < bs.width; x++) {
                doCalc(bs,x,x+i);
            } 
        }
        
        // Diagonal / Test
        // Going up from (h-n, w)
        for (int i = bs.height - c.arow; i> 0; i--) {
            lastP = Player.NONE;
            numInRow = 0;
            numActualInRow = 0;
            for (int x = 0; x < bs.height - i; x++) {
                doCalc(bs,x+i, bs.width - 1 - x);
            } 
        }
        // Going left from (0, w)
        for (int i = 0; i <= bs.width - c.arow; i++) {
            lastP = Player.NONE;
            numInRow = 0;
            numActualInRow = 0;
            for (int x = 0;  x < (bs.height) && bs.width - 1 - x - i >= 0; x++) {
                doCalc(bs,x, bs.width - 1 - x - i);
            } 
        }
        
        if (usActualNInRow > 0 && themActualNInRow > 0) {
            return 0; // Tie terminal test
        } else if (usActualNInRow > 0) {
            return Integer.MAX_VALUE; // We win terminal test
        } else if (themActualNInRow > 0) {
            return Integer.MIN_VALUE; // We lose terminal test
        }
        return availNInRow;
    }

    private void doCalc(BoardState bs, int x, int y) {
        int piece = bs.board[x][y];   
        switch (piece) {
        case Player.NONE:
            numInRow++;
            numActualInRow = 0;
            break;
        case Player.US:
            if (lastP == Player.NONE || lastP == Player.US) numInRow++;
            break;
        case Player.THEM:
            numInRow = 0; // They're blocking us
            break;
        }
        if (piece != Player.NONE) {
            if (lastP == piece) numActualInRow++;
            else numActualInRow = 1;
        }
        
        lastP = piece;
        
        if (numInRow >= c.arow) availNInRow++;
        
        // Terminal test, check for n in a rows
        if (numActualInRow >= c.arow) {
            switch (piece) {
            case Player.US:
                usActualNInRow++;
                break;
            case Player.THEM:
                themActualNInRow++;
                break;
            }
        }
    }
}
