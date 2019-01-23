

import javafx.application.Application;
import javafx.stage.Stage;

public class GHClient extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage = new ClientStage();
	}
	
	// Launch an javafx application for client
	public static void main(String[] args) {
		launch(args);
	}
	
}
