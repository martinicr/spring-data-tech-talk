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

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bodybuilding.tecktalk.domain.Address;
import com.bodybuilding.tecktalk.domain.Customer;
import com.bodybuilding.tecktalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
public class BasicMongoTest {
	
	BasicMongo mongo;
	
	@Before
	public void setUp() throws Exception{
		mongo = new BasicMongo();
		mongo.connect();
	}
	
	@After
	public void tearDown(){
		mongo.removeAll();
		mongo.disconnect();
	}
	
	@Test
	public void testFindAllCustomers() throws Exception{
	
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
	
	@Test
	public void testFindCustomerByName() throws Exception{
		
		
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
		
		List<Customer> customers = mongo.findCustomerByName("Maria");
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers, is(not(Collections.EMPTY_LIST)));
		assertThat(customers.size(), is(1));
		assertThat(customers.get(0).getFirstname(), is("Maria"));
		assertThat(customers.get(0).getLastname(), is("Doe"));
	}
	
	@Test
	public void testFindByLocation() throws Exception{
		
		Set<Address> usaAddresses = new HashSet<Address>();
		Set<Address> ukAddresses = new HashSet<Address>();
		Address usaAddress = new Address("1st Street", "Miami", "USA");
		Address ukAddress = new Address("Some Port", "Liverpool", "UK");
		usaAddresses.add(usaAddress);		
		ukAddresses.add(ukAddress);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(usaAddresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Jones");
		c2.setAddresses(ukAddresses);
		c2.setEmailAddress(new EmailAddress("maria@jones.com"));
		
		Customer c3 = new Customer("Jack", "Sparrow");
		c3.setAddresses(ukAddresses);
		c3.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		mongo.save(c1);
		mongo.save(c2);
		mongo.save(c3);
		
		List<Customer> customers = mongo.findCustomersByLocation(null, "Liverpool", "UK");
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers, is(not(Collections.EMPTY_LIST)));
		assertThat(customers.size(), is(2));
		assertThat(customers.get(0).getFirstname(), is("Jack"));
		assertThat(customers.get(0).getLastname(), is("Sparrow"));
		assertThat(customers.get(1).getFirstname(), is("Maria"));
		assertThat(customers.get(1).getLastname(), is("Jones"));
	}
	
	@Test
	public void testRemoveCustomer() throws Exception{
		
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
		
		Customer toDelete = mongo.findCustomerByName("John").get(0);
		
		mongo.deleteCustomer(toDelete);
		
		List<Customer> customers = mongo.findAllCustomers();
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers, is(not(Collections.EMPTY_LIST)));
		assertThat(customers.size(), is(1));
		assertThat(customers.get(0).getFirstname(), is("Maria"));
		assertThat(customers.get(0).getLastname(), is("Doe"));
	}
	
	@Test
	public void testUpdateCustomer() throws Exception{
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		mongo.save(c1);
		
		Customer before = mongo.findCustomerByName("John").get(0);
		
		assertThat(before, is(not(nullValue())));
		assertThat(before.getEmailAddress().toString(), is("john@doe.com"));
		
		before.setEmailAddress(new EmailAddress("new@email.com"));
		mongo.update(before);
		
		Customer after = mongo.findCustomerByName("John").get(0);
		
		assertThat(after, is(not(nullValue())));
		assertThat(after.getEmailAddress().toString(), is("new@email.com"));
	}

}
