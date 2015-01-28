/**
 * CS4341 - Project 1 Games
 * Professor Neil Heffernan
 * 
 * Lillian Walker
 * Khazhismel Kumykov
 * 
 */
package narow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import narow.formats.Config;
import narow.formats.Player;
import narow.heuristics.CountNARowsCalculator;
import narow.heuristics.CountNARowsCalculator2;
import narow.heuristics.HVal2Calculator;
import narow.heuristics.PossibleNARowsCalculator;


public class App {
	public static void main(String[] args) throws IOException {
		PlayerState player = new PlayerState();
		String inLine = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		int heuristicToUse = 0;
		
		if (args.length > 0) {
            try {
            heuristicToUse = Integer.parseInt(args[0]);
            } finally {}
        }
		
		System.out.println(player.playerName);
		
		// Parse player info - this is always sent first, always format `player1: aa player2: bb`
	    player.arePlayerOne = input.readLine().split(" ")[1].equals(player.playerName);
		
	    
	    
		// Parse config info
		player.config = new Config(input.readLine());
		
        
        switch (heuristicToUse) {
        default: // By default, use the Calculator that performed best in tests.
        case 0:
            player.h = new CountNARowsCalculator(player.config);
            break;
        case 1:
            player.h = new PossibleNARowsCalculator(player.config);
            break;
        case 2:
            player.h = new HVal2Calculator(player.config);
            break;
        case 3:
            player.h = new CountNARowsCalculator2(player.config);
            break;
        }
        
		
		player.bs = new BoardState(player.config.height, player.config.width);
		
		if (player.config.playerGoesFirst == (player.arePlayerOne ? 1 : 2)) {
			player.bs.playerToMove = Player.US;
		    player.makeMove();
		}
		
		// Pass to player
		String[] lineParts;
		for (;;) {
			inLine = input.readLine();
			lineParts = inLine.split(" ");
			if (lineParts.length == 2) {
			    player.updateBoardThem(Integer.parseInt(lineParts[0]), Integer.parseInt(lineParts[1]));
			    player.makeMove();
			} else {
			    // int result = Integer.parseInt(lineParts[0]);
			    System.exit(0); // I think this should kill our threads too
			    break;
			}
			
		}
	}
}
