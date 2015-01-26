package narow;

import java.io.IOException;
import java.util.Random;

class PlayerState {
	
	Config config;
	String playerName = "lwalker_kkumykov_" + Long.toHexString(new java.security.SecureRandom().nextLong());
	Random random = new Random();
	int playerNum; // Are we player 1 or player 2 ?
	
	
	
	void makeMove(String move) throws IOException {
		
		String[] ls =move.split(" ");
		System.out.println(random.nextInt(config.width) + " 1");
	}
}
