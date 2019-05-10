package Runner;

import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import view.MainFrame;


public class Main extends Application {
	private static  String[] info;

	public Main() {	}

	@Override
	public void start(Stage primaryStage) {
		final Logger log = (Logger) LogManager.getLogger(Main.class.getName());
		log.info("work start here!");
		MainFrame frame = new MainFrame();
		frame.drawWindow();

	}

	public static void main(final String[] args) {
		info =  args;
		launch(args);
	}
}



//public class Main{
//
//	public static void main(final String[] args) {
//		final Logger log = LogManager.getLogger(Main.class);
//		log.info("work start here!");
//		view.MainFrame frame = new view.MainFrame();
//		frame.drawWindow();
//	}
//}
