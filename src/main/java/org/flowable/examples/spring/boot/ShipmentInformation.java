package org.flowable.examples.spring.boot;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author Filip Hrisafov
 */
public class ShipmentInformation {

	protected LocalDate shipmentDate;

	protected Period deliveryPeriod;

	public LocalDate getShipmentDate() {
		return shipmentDate;
	}

	public void setShipmentDate(LocalDate shipmentDate) {
		this.shipmentDate = shipmentDate;
	}

	public Period getDeliveryPeriod() {
		return deliveryPeriod;
	}

	public void setDeliveryPeriod(Period deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
}
