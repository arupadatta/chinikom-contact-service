package com.chinikom.service;

import org.springframework.context.ApplicationEvent;

import com.chinikom.domain.ContactDetails;

/**
 * This is an optional class used in publishing application events. This can be
 * used to inject events into the Spring Boot audit management endpoint.
 */
public class ServiceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ContactDetails eventContact;
	String eventType;

	public ServiceEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return "My CustomerService Event";
	}

	public ContactDetails getEventContact() {
		return eventContact;
	}

	public void setEventContact(ContactDetails eventContact) {
		this.eventContact = eventContact;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public ServiceEvent(Object source, ContactDetails eventContact,
			String eventType) {
		super(source);
		this.eventContact = eventContact;
		this.eventType = eventType;
	}

}
