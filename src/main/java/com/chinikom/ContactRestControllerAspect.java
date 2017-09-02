package com.chinikom;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

//import org.apache.log4j.Logger;

@Aspect
@Component
public class ContactRestControllerAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CounterService counterService;

	@Before("execution(public * com.chinikom.api.rest.*Controller.*(..))")
	public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
		logger.info(":::::AOP Before for Contact REST call:::::" + pjp);
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.createContact*(..))")
	public void afterCallingCreateContact(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Create Contact REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.ContactController.createAddress");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllContactByPage*(..))")
	public void afterCallingGetAllContactByPage(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Contact getAllContactByPage REST call:::::"
				+ pjp);

		counterService
				.increment("com.chinikom.api.rest.ContactController.getAllContactByPage");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllContact*(..))")
	public void afterCallingGetAllContact(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Contacts getAllContact REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.ContactController.getAllContacts");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getContact*(..))")
	public void afterCallingGetContact(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Contacts getContact REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.ContactController.getContact");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.updateContact*(..))")
	public void afterCallingUpdateContact(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Contacts updateContact REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.ContactController.updateContact");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.deleteAddress*(..))")
	public void afterCallingDeleteAddress(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Addresses deleteAddress REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.AddressController.deleteAddress");
	}

	@AfterThrowing(pointcut = "execution(public * com.chinikom.api.rest.*Controller.*(..))", throwing = "e")
	public void afterCustomerThrowsException(NoSuchElementException e) {
		counterService.increment("counter.errors.Address.controller");
	}

}
