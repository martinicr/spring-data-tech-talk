/**
 * 
 */
package com.bodybuilding.techtalk.repository.custom;

import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
public interface MyCustom {
	
	Customer findOne(Long id);
	
	Customer save(Customer customer);

	Customer findByEmailAddress(EmailAddress emailAddress);

	void deleteAll();
	
	void printSomeMessage();

}
