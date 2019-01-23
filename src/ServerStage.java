

import java.io.*;
import java.net.*;
import java.util.*;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerStage extends Stage {
	
	private ServerSocket serverSocket;
	
	// Server pane
	private ServerPane serverPane = new ServerPane();
	
	// Greenhouse simulation
	private GreenhouseSimulation gs = new GreenhouseSimulation();
	
	// Count client number
	private int clientNo = 1;
	
	public ServerStage() {
		// Set the stage
		Scene scene = new Scene(serverPane, 500, 280);
		setScene(scene);
		setTitle("Server");
		setResizable(false);
		show();
		
		try {
			// Create a server 
			serverSocket = new ServerSocket(21500);
			// Show message of server creation
			serverPane.appendTA("Greenhouse Simulator Server started at " + new Date() + "\n");
			
			// Start a new thread for greenhouse simulation
			new Thread(gs).start();
			
			// Start a new thread for connecting to client
			new Thread(() -> {
				while (true) {
					try {
						
						// Wait a client to connect
						Socket socket = serverSocket.accept();
						// Show the information of the connected client
						InetAddress inetAddress = socket.getInetAddress();
						serverPane.appendTA("Client " + clientNo + " Host name: " + inetAddress.getHostName() + "\n"
		            		+ "Client " + clientNo +  " IP address: " + inetAddress.getHostAddress() + "\n");
						// Start a new thread for receiving data
						btApplyHandler task1 = new btApplyHandler(socket);
						new Thread(task1).start();
						// Start a new thread for sending data
						btRequestHandler task2 = new btRequestHandler(socket);
						new Thread(task2).start();
						
						// Increase client number 
						clientNo++;
						
					} catch (IOException ex) {
						System.err.println("Exception when waiting for client");
						ex.printStackTrace();
					}
				}	
			}).start();
			
			// Start a new thread for showing the simulative results
			new Thread(() -> {
				while (true) {
					try {
						// Showing simulative temperature and humidity 
						serverPane.appendTA("Temperature is " + gs.getTemp() + "\u00b0C\n");
						serverPane.appendTA("Humidity is " + gs.getHum() + "%\n");
						
						// Sleep 5s
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						System.err.println("Exception when showing the simulative results");
						ex.printStackTrace();
					}
				}
			}).start();
			
		} catch (IOException ex) {
			System.err.println("Exception in the server thread");
			ex.printStackTrace();
		}
	}

	// Runnable class for task1
	class btApplyHandler implements Runnable {
		private Socket socket;
		
		public btApplyHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			try {
				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				
				while (true) {

					// Read data from client
					int inputTemperature = inputFromClient.readInt();
					int inputHumidity = inputFromClient.readInt();
					// Set client temperature and humidity
					gs.setClientTemp(inputTemperature);
					gs.setClientHum(inputHumidity);
					// Show message in the text area
					serverPane.appendTA("Client set temperature: " + inputTemperature + "\u00b0C\n");
					serverPane.appendTA("Client set Humidity: " +inputHumidity + "%\n");
				}
				
			} catch (IOException ex) {
				serverPane.appendTA("Client disconnected\n");
			} finally {
				try {
					// Close socket
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		}
		
	}
	
	// Runnable class for task2
	class btRequestHandler implements Runnable {
		private Socket socket;
		
		public btRequestHandler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			
			try {
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
				
				while (true) {
					try {
						
						// Send data to the stream every 2s 
						outputToClient.flush();
						outputToClient.writeInt(gs.getTemp());
						outputToClient.writeInt(gs.getHum());
						outputToClient.writeBoolean(gs.isWindowOpen());
						outputToClient.writeBoolean(gs.isDehumidifierOpen());
						
						Thread.sleep(2000);
					} catch (InterruptedException ex) {
						System.err.println("Exception when sending data to client");
						ex.printStackTrace();
					} 
				}
				
			} catch (IOException ex) {
				
			} finally {
				try {
					// Close socket
					socket.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		}
		
	}
	
}
