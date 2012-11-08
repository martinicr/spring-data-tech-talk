/**
 * 
 */
package com.bodybuilding.techtalk.repository.custom;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
public class CustomerRepositoryCustomImpl implements MyCustom {

	@Autowired
	private MongoOperations operations;

	@Override
	public Customer findOne(Long id) {
		Query query = query(where("id").is(id));
		return operations.findOne(query, Customer.class);
	}

	@Override
	public Customer save(Customer customer) {
		operations.save(customer);
		return customer;
	}

	@Override
	public void deleteAll() {
		operations.dropCollection(Customer.class);
	}

	@Override
	public Customer findByEmailAddress(EmailAddress emailAddress) {
		Query query = query(where("emailAddress").is(emailAddress));
		return operations.findOne(query, Customer.class);
	}

	@Override
	public void printSomeMessage() {
		System.out.println("This is my super message");
	}

}
