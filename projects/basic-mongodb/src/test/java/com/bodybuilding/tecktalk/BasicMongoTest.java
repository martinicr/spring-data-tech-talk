/**
 * 
 */
package com.bodybuilding.tecktalk;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.junit.Test;

import com.bodybuilding.tecktalk.domain.Address;
import com.bodybuilding.tecktalk.domain.Customer;
import com.bodybuilding.tecktalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
public class BasicMongoTest {
	
	
	
	
	@Test
	public void testFindAllCustomers() throws Exception{
	
		BasicMongo mongo = new BasicMongo();
		mongo.connect();
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		
		mongo.save(c1);
		mongo.save(c2);
		
		
		List<Customer> customers = mongo.findAllCustomers();
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers, is(not(Collections.EMPTY_LIST)));
		assertThat(customers.size(), is(2));
	}

}
