package org.virtus.sense.ble.models;

public class Reading {
	
	public String id;
	public String pass;
	public long timestamp;
	public Metric[] metrics;

	public Reading() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id;
	}

}
