package com.chinikom.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinikom.domain.ContactDetails;

public interface ChinikomContactRepository extends
		PagingAndSortingRepository<ContactDetails, Long> {
	ContactDetails findContactByPhoneNo(String phoneNo);

	@Override
	Page<ContactDetails> findAll(Pageable pageable);

}
