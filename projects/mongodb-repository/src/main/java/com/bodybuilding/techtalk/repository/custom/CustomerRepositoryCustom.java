/**
 * 
 */
package com.bodybuilding.techtalk.repository.custom;

import org.springframework.data.repository.CrudRepository;

import com.bodybuilding.techtalk.domain.Customer;

/**
 * @author martin
 *
 */
public interface CustomerRepositoryCustom extends CrudRepository<Customer, Long>, MyCustom {
	

}
