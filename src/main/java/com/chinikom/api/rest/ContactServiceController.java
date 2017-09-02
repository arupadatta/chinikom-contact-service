package com.chinikom.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinikom.domain.ContactDetails;
import com.chinikom.exception.HTTP400Exception;
import com.chinikom.service.ChinikomContactDetailsService;
import com.chinikom.service.ServiceEvent;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */
@RestController
@RequestMapping(value = "/chinikom-contact-service/v1/contacts")
public class ContactServiceController extends AbstractRestController {

	@Autowired
	private ChinikomContactDetailsService contactDetailsService;

	@Autowired
	CounterService counterService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createContactDetails(@RequestBody ContactDetails contact,
			HttpServletRequest request, HttpServletResponse response) {
		ContactDetails createdContactDetail = this.contactDetailsService
				.createContact(contact);
		if (createdContactDetail != null) {
			counterService
					.increment("com.chinikom.contactdetails.created.success");
			eventPublisher.publishEvent(new ServiceEvent(this,
					createdContactDetail, "AddressCreated"));
		} else {
			counterService
					.increment("com.chinikom.contactdetails.created.failure");
		}
		response.setHeader("Location", request.getRequestURL().append("/")
				.append(createdContactDetail.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Page<ContactDetails> getAllContactDetailsByPage(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.contactDetailsService.getAllContactsByPage(page, size);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<ContactDetails> getAllAddressDtails(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.contactDetailsService.getAllContacts();
	}

	// @RequestMapping("/simple/{emailId}")
	// public UserLogin getSimpleUserLoginByEmail(
	// @PathVariable("emailId") String emailId) {
	// UserLogin userLogin = this.userLoginService
	// .getUserLoginByEmail(emailId);
	// checkResourceFound(userLogin);
	// return userLogin;
	// }
	//

	// @RequestMapping(value = "/byemail/{emailId}", method = RequestMethod.GET,
	// produces = {
	// "application/json", "application/xml" })
	// @ResponseStatus(HttpStatus.OK)
	// public @ResponseBody
	// AddressDetails getUserLoginByEmail(@PathVariable("emailId") String
	// emailId,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// UserLogin userLogin = this.addressDetailsService
	// .getUserLoginByEmail(emailId);
	// checkResourceFound(userLogin);
	// return userLogin;
	// }

	@RequestMapping("/simple/{id}")
	public ContactDetails getSimpleContactDeatilsById(
			@PathVariable("id") Long id) {
		ContactDetails contactDetails = this.contactDetailsService
				.getContact(id);
		checkResourceFound(contactDetails);
		return contactDetails;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	ContactDetails getContactDetailsById(@PathVariable("id") long id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ContactDetails contactDetails = this.contactDetailsService
				.getContact(id);
		checkResourceFound(contactDetails);
		return contactDetails;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateContactDetails(@PathVariable("id") Long id,
			@RequestBody ContactDetails contactDetails,
			HttpServletRequest request, HttpServletResponse response) {
		ContactDetails contactDtls = this.contactDetailsService.getContact(id);
		checkResourceFound(contactDtls);
		if (id != contactDtls.getId())
			throw new HTTP400Exception("ID doesn't match!");
		counterService.increment("com.chinikom.contactdetails.updated.success");

		this.contactDetailsService.updateContact(contactDtls);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteContactDetails(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.contactDetailsService.getContact(id));
		counterService.increment("com.chinikom.contactdetails.deleted.success");
		this.contactDetailsService.deleteContact(id);
	}
}
