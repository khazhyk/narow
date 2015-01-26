package narow;

class Config {
	int width, height, arow, timelimit, playerGoesFirst;
	
	Config(String whasd) {
		String[] cas = whasd.split(" ");
		height = Integer.parseInt(cas[0]);
		width = Integer.parseInt(cas[1]);
		arow = Integer.parseInt(cas[2]);
		playerGoesFirst = Integer.parseInt(cas[3]);
		timelimit = Integer.parseInt(cas[4]);
	}
}