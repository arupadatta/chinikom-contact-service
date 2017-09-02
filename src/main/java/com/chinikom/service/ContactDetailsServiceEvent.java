package com.chinikom.service;

import org.springframework.context.ApplicationEvent;

/**
 * This is an optional class used in publishing application events. This can be
 * used to inject events into the Spring Boot audit management endpoint.
 */
public class ContactDetailsServiceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ContactDetailsServiceEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return "My ContactService Event";
	}
}