

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class ServerPane extends BorderPane{
	// Text area showing the message
	private TextArea ta;
	
	public ServerPane() {
		// Put text area into pane
		ta = new TextArea();
		ta.setPrefSize(500, 280);
		ta.setEditable(false);
		setPadding(new Insets(5));
		setCenter(ta);
	}
	
	// Set text area
	public void appendTA(String text) {
		ta.appendText(text);
	}
	
}
