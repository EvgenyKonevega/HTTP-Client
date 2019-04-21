package Runner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import view.MainFrame;

public class Main{

	public static void main(final String[] args) {
		final Logger log = LogManager.getLogger(Main.class);
		log.info("work start here!");
		MainFrame frame = new MainFrame();
		frame.drawWindow();
	}
}
