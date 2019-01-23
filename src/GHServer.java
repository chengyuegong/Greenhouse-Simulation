

import javafx.application.Application;
import javafx.stage.Stage;

public class GHServer extends Application{
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage = new ServerStage();
	}
	
	// Launch an javafx application for server
	public static void main(String[] args) {
		launch(args);
	}

}
