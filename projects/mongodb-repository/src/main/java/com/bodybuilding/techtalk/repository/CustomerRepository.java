/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.bodybuilding.techtalk.domain.Customer;

/**
 * @author martin
 *
 */
public interface CustomerRepository extends Repository<Customer, Long> {
	
	
	void save(Customer customer);
	
	List<Customer> findAll();
	
	void deleteAll();
	
	List<Customer> findByLastname(String lastname);

}
