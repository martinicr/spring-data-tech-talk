/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import org.springframework.data.repository.Repository;

import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
public interface CustomerRepositoryCustom extends Repository<Customer, Long>, MyCustom {
	

}
