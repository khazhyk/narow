/**
 * CS4341 - Project 1 Games
 * Professor Neil Heffernan
 * 
 * Lillian Walker
 * Khazhismel Kumykov
 * 
 */
package narow;

public class Move {
    int column;
    int action;
    long score;
    
    Move(int column, int action) {
        this.column = column; this.action = action;
    }
    
    @Override
    public String toString() {
    	return column + " " + action;
    }
}
