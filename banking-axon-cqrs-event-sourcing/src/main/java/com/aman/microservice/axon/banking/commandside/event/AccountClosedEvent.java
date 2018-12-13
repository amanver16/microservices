package com.aman.microservice.axon.banking.commandside.event;

public class AccountClosedEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;

	public AccountClosedEvent(String id) {
		super(id);
	}
}