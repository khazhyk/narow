/**
 * CS4341 - Project 1 Games
 * Professor Neil Heffernan
 * 
 * Lillian Walker
 * Khazhismel Kumykov
 * 
 */
package narow.state;

public class Move {
    public int column;
    public int action;
    public long score;
    
    public Move(int column, int action) {
        this.column = column; this.action = action;
    }
    
    @Override
    public String toString() {
    	return column + " " + action;
    }
}
