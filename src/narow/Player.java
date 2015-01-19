package narow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Player {
	
	Config config;
	String playerName = Long.toHexString(new java.security.SecureRandom().nextLong());
	Random random = new Random();
	
	void makeMove(String move) throws IOException {
		
		List<String> ls=Arrays.asList(move.split(" "));
		if(ls.size()==2){
			System.out.println(random.nextInt(config.width) + " 1");
		}
		else if(ls.size()==1){
			System.out.println("game over!!!");
		}
		else if(ls.size()==5){          //ls contains game info
			config = new Config(move);
			System.out.println("0 1");  //first move
		}
		else if(ls.size()==4){		//player1: aa player2: bb
			//TODO combine this information with game information to decide who is the first player
		}
		else
			System.out.println("not what I want");
		
	}
}
