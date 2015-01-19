package narow;

import java.io.IOException;
import java.util.Random;

class Player {
	
	Config config;
	String playerName = Long.toHexString(new java.security.SecureRandom().nextLong());
	Random random = new Random();
	int ourNumber;
	
	void makeMove(String move) throws IOException {
		
		String[] ls =move.split(" ");
		if(ls.length==2){
			System.out.println(random.nextInt(config.width) + " 1");
		}
		else if(ls.length==1){
			System.out.println("game over!!!");
		}
		else if(ls.length==5){          //ls contains game info
			config = new Config(move);
			if (config.player == ourNumber) {
				System.out.println("0 1");  //first move
			}
		}
		else if(ls.length==4){		//player1: aa player2: bb
			if (ls[1].equals(playerName)) {
				ourNumber = 1;
			} else {
				ourNumber = 2;
			}
		}
		else
			System.out.println("not what I want");
		
	}
}
