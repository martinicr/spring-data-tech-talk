/**
 * 
 */
package com.bodybuilding.techtalk.dao;

import java.math.BigInteger;
import java.util.List;

import com.bodybuilding.techtalk.domain.Customer;

/**
 * @author martin
 *
 */
public interface CustomerDAO {

	void save(Customer customer);
	
	List<Customer> findAll();

	void removeAll();

	Customer findById(BigInteger id);

	Customer update(Customer customer);

}
