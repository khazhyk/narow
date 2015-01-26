package narow;

public class Move {
    int column;
    int action;
    int score;
    
    Move(int column, int action) {
        this.column = column; this.action = action;
    }
    
    @Override
    public String toString() {
    	return column + " " + action;
    }
}
