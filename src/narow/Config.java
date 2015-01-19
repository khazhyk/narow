package narow;

class Config {
	int width, height, arow, player, timelimit;
	
	Config(String whasd) {
		String[] cas = whasd.split(" ");
		height = Integer.parseInt(cas[0]);
		width = Integer.parseInt(cas[1]);
		arow = Integer.parseInt(cas[2]);
		player = Integer.parseInt(cas[3]);
		timelimit = Integer.parseInt(cas[4]);
	}
}