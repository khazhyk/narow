package narow.heuristics;

import narow.BoardState;

public interface Heuristic {
    public int calculate(BoardState bs);
}
