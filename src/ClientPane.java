

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ClientPane extends GridPane {
	
	// Temperature and humidity of the pane
	private IntegerProperty temperature = new SimpleIntegerProperty(25);
	private IntegerProperty humidity = new SimpleIntegerProperty(50);
	
	// Two buttons of the pane
	private Button btApply;
	private Button btRequest;
	
	// Four reading texts of the pane
	private Text textReadingTemperature;
	private Text textReadingHumidity;
	private Text textReadingWindow;
	private Text textReadingDehumidifier;
	
	public ClientPane() {
		
		// Create pane for buttons
		VBox paneForButtons = new VBox(10);
		Button btReset = new Button("Reset");
		btReset.setPrefSize(80, 5);
		btRequest = new Button("Request");
		btRequest.setPrefSize(80, 5);
		btApply = new Button("Apply");
		btApply.setPrefSize(80, 5);
		paneForButtons.getChildren().addAll(btReset, btRequest, btApply);
		paneForButtons.setAlignment(Pos.CENTER);
		paneForButtons.setPadding(new Insets(5, 20, 20, 5));
		
		// Create pane for labels(sliders)
		VBox paneForLabels = new VBox(20);
		Slider slTemperature = new Slider();
		slTemperature.setShowTickLabels(true);
		slTemperature.setShowTickMarks(true);
		slTemperature.setMajorTickUnit(5);
		slTemperature.setMinorTickCount(5);
		slTemperature.setMin(10);
		slTemperature.setMax(40);
		slTemperature.setValue(temperature.getValue());
		slTemperature.setPrefWidth(250);
		slTemperature.setBlockIncrement(1);
		Slider slHumidity = new Slider();
		slHumidity.setShowTickLabels(true);
		slHumidity.setShowTickMarks(true);
		slHumidity.setValue(humidity.getValue());
		slHumidity.setPrefWidth(250);
		slHumidity.setBlockIncrement(1);
		Label lblTemperature = new Label("Temperature(\u00b0C)", slTemperature);
		lblTemperature.setContentDisplay(ContentDisplay.RIGHT);
		lblTemperature.setAlignment(Pos.CENTER);
		Label lblHumidity = new Label("Humidity(%)", slHumidity);
		lblHumidity.setContentDisplay(ContentDisplay.RIGHT);
		lblHumidity.setAlignment(Pos.CENTER);
		paneForLabels.getChildren().addAll(lblTemperature, lblHumidity);
		paneForLabels.setPadding(new Insets(20, 5, 5, 20));
		paneForLabels.setAlignment(Pos.CENTER);
		
		// Create pane for texts showing temperature and humidity
		VBox paneForTexts = new VBox(35);
		Text textTemperature = new Text("" + (int)slTemperature.getValue());
		Text textHumidity = new Text("" + (int)slHumidity.getValue());
		paneForTexts.getChildren().addAll(textTemperature, textHumidity);
		paneForTexts.setPadding(new Insets(20, 20, 5, 5));
		paneForTexts.setAlignment(Pos.CENTER);
		
		// Add listeners for sliders(simultaneously changing texts and values of IntegerProperty)
		slTemperature.valueProperty().addListener(ov -> {
			textTemperature.setText("" + (int)slTemperature.getValue());
			setTemp((int)slTemperature.getValue());
		});
		
		slHumidity.valueProperty().addListener(ov -> {
			textHumidity.setText("" + (int)slHumidity.getValue());
			setHum((int)slHumidity.getValue());
		});
		
		// Set on action for btReset
		btReset.setOnAction(e -> {
			setTemp(25);
			setHum(50);
			slTemperature.setValue(temperature.getValue());
			slHumidity.setValue(humidity.getValue());
		});
		
		// Create pane for Readings
		VBox paneForReadings = new VBox(10);
		Text textReadingTitle = new Text("Readings");
		textReadingTemperature = new Text("Temperature:");
		textReadingHumidity = new Text("Humidity:");
		textReadingWindow = new Text("Window:");
		textReadingDehumidifier = new Text("Dehumidifier:");
		paneForReadings.getChildren().addAll(textReadingTitle, textReadingTemperature, textReadingHumidity, textReadingWindow, textReadingDehumidifier);
		paneForReadings.setPadding(new Insets(5, 5, 20, 20));
		
		// Add 4 sub panes into the main pane(grid pane)
		add(paneForLabels, 0, 0);
		add(paneForReadings, 0, 1);
		add(paneForTexts, 1, 0);
		add(paneForButtons, 1, 1);
	}
	
	// Private methods of the class(set temperature and humidity of the client pane)
	private void setTemp(int temperature) {
		this.temperature.setValue(temperature);
	}
	
	private void setHum(int humidity) {
		this.humidity.setValue(humidity);
	}
	
	// Public methods of the class(get temperature and humidity of the client pane)
	public IntegerProperty getTemp() {
		return temperature;
	}
	
	public IntegerProperty getHum() {
		return humidity;
	}

	// Public methods of the class(get two buttons of the client pane)
	public Button getBtApply() {
		return btApply;
	}
	
	public Button getBtRequest() {
		return btRequest;
	}
	
	// Public methods of the class(set reading section of the client pane)
	public void setReadingTemperature(String text) {
		textReadingTemperature.setText(text);
	}
	
	public void setReadingHumidity(String text) {
		textReadingHumidity.setText(text);
	}
	
	public void setReadingWindow(String text) {
		textReadingWindow.setText(text);
	}
	
	public void setReadingDehumidifier(String text) {
		textReadingDehumidifier.setText(text);
	}	
}
