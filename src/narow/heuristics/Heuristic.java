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

public interface Heuristic {
    /**
     * 
     * @param bs
     * @return MUST return Integer.MAX_VALUE for win state, Integer.MIN_VALUE for lose state. Otherwise, return a score.
     */
    public int calculate(BoardState bs);
}
