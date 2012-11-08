/**
 * 
 */
package com.bodybuilding.techtalk.repository.custom;

import org.springframework.data.repository.Repository;

import com.bodybuilding.techtalk.domain.Customer;

/**
 * @author martin
 *
 */
public interface CustomerRepositoryCustom extends Repository<Customer, Long>, MyCustom {
	

}
