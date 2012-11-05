/**
 * 
 */
package com.bodybuilding.techtalk.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.bodybuilding.techtalk.domain.Customer;

/**
 * @author martin
 *
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void save(Customer customer) {
		mongoTemplate.insert(customer);
	}
	
	@Override
	public List<Customer> findAll() {
		return mongoTemplate.findAll(Customer.class);
	}
	
	@Override
	public void removeAll() {
		mongoTemplate.dropCollection(Customer.class);
		//mongoTemplate.dropCollection("collection-name");
	}

	@Override
	public Customer findById(BigInteger id) {
		return mongoTemplate.findById(id, Customer.class);
	}

	@Override
	public Customer update(Customer customer) {
		Update update = new Update().set("firstname", customer.getFirstname());
		update.set("lastname", customer.getLastname());
		update.set("emailAddress", customer.getEmailAddress());
		update.set("addresses", customer.getAddresses());
		Query query = new Query(Criteria.where("_id").is(customer.getId()));
		
		return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Customer.class);
	}

}
