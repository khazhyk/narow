package narow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class App {
	public static void main(String[] args) throws IOException {
		PlayerState player = new PlayerState();
		String inLine = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println(player.playerName);
		
		// Parse player info - this is always sent first, always format `player1: aa player2: bb`
	    player.arePlayerOne = input.readLine().split(" ")[1].equals(player.playerName);
		
		
		// Parse config info
		player.config = new Config(input.readLine());
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
