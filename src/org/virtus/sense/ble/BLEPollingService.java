package org.virtus.sense.ble;

import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import org.virtus.sense.ble.models.Reading;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class BLEPollingService extends TimerTask {
	
	private List<BLEListenner> listenners;
	private String endpoint;
	
	
	public BLEPollingService(List<BLEListenner> listenners, String endpoint) {
		this.listenners = listenners;
		this.endpoint = endpoint;
	}

	@Override
	public void run() {
		
		System.out.println("requesting " + endpoint);
		
		Unirest
			.get(endpoint)
			.asObjectAsync(Reading[].class, new Callback<Reading[]>() {

			    public void failed(UnirestException e) {
			        System.out.println("The request has failed");
			    }

			    public void completed(HttpResponse<Reading[]> response) {
			    	Reading[] reads = response.getBody();			    	
			    	listenners.forEach(l -> l.received(reads));
			    }

			    public void cancelled() {
			        System.out.println("The request has been cancelled");
			    }

			});
	}
	
	static {		
		// add object mapper
		Unirest.setObjectMapper(new ObjectMapper() {
		    private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
		                = new com.fasterxml.jackson.databind.ObjectMapper();

		    public <T> T readValue(String value, Class<T> valueType) {
		        try {
		            return jacksonObjectMapper.readValue(value, valueType);
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        }
		    }

		    public String writeValue(Object value) {
		        try {
		            return jacksonObjectMapper.writeValueAsString(value);
		        } catch (JsonProcessingException e) {
		            throw new RuntimeException(e);
		        }
		    }
		});				
	}

}
