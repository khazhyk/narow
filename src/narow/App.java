package narow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class App {
	public static void main(String[] args) throws IOException {
		Player player = new Player();
		String inLine = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println(player.playerName);
		
		while (!inLine.equals("-1")) {
			inLine = input.readLine();
			player.makeMove(inLine);
		}
	}
}
