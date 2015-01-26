package narow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class App {
	public static void main(String[] args) throws IOException {
		PlayerState player = new PlayerState();
		String inLine = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println(player.playerName);
		
		// Parse player info - this is always sent first, always format `player1: aa player2: bb`
		if (input.readLine().split(" ")[1].equals(player.playerName)) {
		    player.playerNum = Player.ONE;
		} else { 
		    player.playerNum = Player.TWO;
		}
		
		// Parse config info
		player.config = new Config(input.readLine());
		
		// Pass to player
		while (!inLine.equals("-1")) {
			inLine = input.readLine();
			player.makeMove(inLine);
		}
	}
}
