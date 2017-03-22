package org.virtus.sense.ble;

import org.virtus.sense.ble.models.Reading;

public interface BLEListenner {
	
	void received(Reading[] readings);
}
