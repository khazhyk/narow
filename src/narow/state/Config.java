package narow.state;

public class Config {
	public int width, height, arow, timelimit, playerGoesFirst;
	
	public Config(String whasd) {
		String[] cas = whasd.split(" ");
		height = Integer.parseInt(cas[0]);
		width = Integer.parseInt(cas[1]);
		arow = Integer.parseInt(cas[2]);
		playerGoesFirst = Integer.parseInt(cas[3]);
		timelimit = Integer.parseInt(cas[4]);
	}
}