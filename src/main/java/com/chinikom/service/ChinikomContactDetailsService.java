package com.chinikom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chinikom.dao.jpa.ChinikomContactRepository;
import com.chinikom.domain.ContactDetails;

/*
 * Service class to do CRUD for User and Address through JPS Repository
 */
@Service
public class ChinikomContactDetailsService {

	private static final Logger log = LoggerFactory
			.getLogger(ChinikomContactDetailsService.class);

	@Autowired
	private ChinikomContactRepository contactRepository;

	@Autowired
	CounterService counterService;

	@Autowired
	GaugeService gaugeService;

	public ChinikomContactDetailsService() {
	}

	public ContactDetails createContact(ContactDetails contact) {
		if (contact.getEmailId() != null && contact.getPhoneNo() != null) {
			log.info("Customer Date of Birth :" + contact.getEmailId() + ", "
					+ contact.getPhoneNo());
		} else {
			log.info("Customer Date of Birth is null :");
		}

		return contactRepository.save(contact);
	}

	public ContactDetails getContact(long id) {
		return contactRepository.findOne(id);
	}

	public void updateContact(ContactDetails contact) {
		contactRepository.save(contact);
	}

	public void deleteContact(Long id) {
		contactRepository.delete(id);
	}

	public Page<ContactDetails> getAllContactsByPage(Integer page, Integer size) {
		Page<ContactDetails> pageOfCustomers = contactRepository
				.findAll(new PageRequest(page, size));
		// example of adding to the /metrics
		if (size > 50) {
			counterService.increment("com.rollingstone.getAll.largePayload");
		}
		return pageOfCustomers;
	}

	public List<ContactDetails> getAllContacts() {
		Iterable<ContactDetails> pageOfContacts = contactRepository.findAll();
		List<ContactDetails> contacts = new ArrayList<ContactDetails>();
		for (ContactDetails contact : pageOfContacts) {
			contacts.add(contact);
		}
		log.info("In Real Service getAllAddresses size :" + contacts.size());

		return contacts;
	}
}
