package org.virtus.sense.ble;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class BLEPoller {
	
	private int pollingInterval;
	private String endpoint;
	
	private Timer timer;

	private List<BLEListenner> listenners;
		
	public BLEPoller(int pollingInterval, String endpoint) {
		this.pollingInterval = pollingInterval;
		this.endpoint = endpoint;
		this.listenners = new ArrayList<BLEListenner>();
		this.timer = new Timer();
	}
	
	public void addListener(BLEListenner listenner) {
		this.listenners.add(listenner);
	}
	
	public void start() {
		this.timer.scheduleAtFixedRate(
				new BLEPollingService(listenners, endpoint), 
				(long) 0, 
				(long) pollingInterval);
	}
	
	public void close() {
		this.timer.cancel();
	}

}
