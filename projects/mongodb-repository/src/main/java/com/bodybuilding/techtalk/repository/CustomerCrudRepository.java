/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
public interface CustomerCrudRepository extends CrudRepository<Customer, Long> {

	Customer findByEmailAddress(EmailAddress emailAddress);

	List<Customer> findByLastnameLike(String regex);

	List<Customer> findByAddresses_Country(String string);

}
