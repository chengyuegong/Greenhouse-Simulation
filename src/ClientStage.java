

import java.io.*;
import java.net.*;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientStage extends Stage {
	
	// Client pane
	private ClientPane clientPane = new ClientPane();
	
	// Temperature and humidity of the client
	private IntegerProperty clientTem = new SimpleIntegerProperty(25);
	private IntegerProperty clientHum = new SimpleIntegerProperty(50);
	
	// Input and output stream of the client
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	// Socket of the client
	private Socket socket;
	
	// Four received messages from the server
	private int receivedTemperature;
	private int receivedHumidity;
	private boolean receivedIsWindowOpen;
	private boolean receivedIsDehumidifierOpen;
	
	public ClientStage() {
		// Set the stage
		Scene scene = new Scene(clientPane, 500, 280);
		setTitle("Greenhouse Simulator");
		setScene(scene);
		setResizable(false);
		show();
		
		// Bind client temperature and humidity with client pane's
		clientTem.bind(clientPane.getTemp());
		clientHum.bind(clientPane.getHum());
		
		// Start a new thread for connecting to the server and receiving the data
		new Thread(() -> {
			try {
				socket = new Socket("localhost", 21500);
				fromServer = new DataInputStream(socket.getInputStream());
				toServer = new DataOutputStream(socket.getOutputStream());
				while (true) {
					receivedTemperature = fromServer.readInt();
					receivedHumidity = fromServer.readInt();
					receivedIsWindowOpen = fromServer.readBoolean();
					receivedIsDehumidifierOpen = fromServer.readBoolean();
				}
			} catch (IOException ex) {
				System.err.println("Exception in client thread");
				ex.printStackTrace();
			}
		}).start();

		// Set on action for btApply(send the client temperature and humidity)
		clientPane.getBtApply().setOnAction(e -> {
				try {
					toServer.flush();
					toServer.writeInt(clientTem.getValue());
					toServer.writeInt(clientHum.getValue());
				} catch (IOException ex) {
					System.err.println("Exception when sending data");
					ex.printStackTrace();
				}		
		});
		
		// Set on action for btRequest(show the readings on client pane)
		clientPane.getBtRequest().setOnAction(e -> {
				
				clientPane.setReadingTemperature("Temperature: " + receivedTemperature + "\u00b0C");
				clientPane.setReadingHumidity("Humidity: " + receivedHumidity + "%");
				if (receivedIsWindowOpen == true)
					clientPane.setReadingWindow("Window: Open");
				else if (receivedIsWindowOpen == false)
					clientPane.setReadingWindow("Window: Closed");
				if (receivedIsDehumidifierOpen == true)
					clientPane.setReadingDehumidifier("Dehumidifier: Open");
				else if (receivedIsDehumidifierOpen == false)
					clientPane.setReadingDehumidifier("Dehumidifier: Closed");
				
		});
	}
	
}
