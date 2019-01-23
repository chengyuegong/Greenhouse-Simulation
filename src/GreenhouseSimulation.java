

public class GreenhouseSimulation implements Runnable{
	
	// Simulative parameters
	private int temperature;
	private int humidity;	
	private boolean isWindowOpen;
	private boolean isDehumidifierOpen;
	
	// Temperature and humidity set by client
	private int clientSetTemperature = 25;
	private int clientSetHumidity = 50;
	
	// Initialize greenhouse simulation
	public GreenhouseSimulation() {
		temperature = 25;
		humidity = 50;
		isWindowOpen = false;
		isDehumidifierOpen = false;
	}
	
	@Override
	public void run(){
		while (true) {
			try {
				simulate();
				
				// Sleep 5s
				Thread.sleep(5000);
			} catch (InterruptedException ex) {
				System.err.println("Exception in greenhouse simulation thread");
				ex.printStackTrace();
			}
		}
	}
	
	// Simulate greenhouse 
	private void simulate() {
		if (isWindowOpen()) {
			temperature--;
		} else if (!isWindowOpen()) {
			temperature++;
		}
		
		if (isDehumidifierOpen()) {
			humidity--;
		} else if (!isDehumidifierOpen()) {
			humidity++;
		}
		
		if (temperature < clientSetTemperature) {
			setWindowClosed();
		} else if (temperature > clientSetTemperature) {
			setWindowOpen();
		}
		
		if (humidity < clientSetHumidity) {
			setDehumidifierClosed();
		} else if (humidity > clientSetHumidity) {
			setDehumidifierOpen();
		}
	}
	
	// Private methods (set states of window and dehumidifier)
	private void setWindowOpen() {
		isWindowOpen = true;
	}
	
	private void setWindowClosed() {
		isWindowOpen = false;
	}
	
	private void setDehumidifierOpen() {
		isDehumidifierOpen = true;
	}
	
	private void setDehumidifierClosed() {
		isDehumidifierOpen = false;
	}
	
	// Public methods (set temperature and humidity from client)
	public void setClientTemp(int temperature) {
		this.clientSetTemperature = temperature;
	}

	public void setClientHum(int humidity) {
		this.clientSetHumidity = humidity;
	}
	
	// Public methods (get data for btRequest)
	public int getTemp() {
		return temperature;
	}
	
	public int getHum() {
		return humidity;
	}
	
	public boolean isWindowOpen() {
		return isWindowOpen;		
	}
	
	public boolean isDehumidifierOpen() {
		return isDehumidifierOpen;
	}

}
