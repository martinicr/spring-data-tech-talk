/**
 * 
 */
package com.bodybuilding.techtalk.dao;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bodybuilding.techtalk.dao.CustomerDAO;
import com.bodybuilding.techtalk.domain.Address;
import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class CustomerDAOTest {
	
	@Autowired
	CustomerDAO customerDAO;
	
	@Before
	public void setUp(){
		customerDAO.removeAll();
	}

	@Test
	public void testFindAllCustomers() {
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		customerDAO.save(c1);
		customerDAO.save(c2);
		
		List<Customer> customers = customerDAO.findAll();
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers.size(), is(2));
	}
	
	@Test
	public void voidTestFindById(){
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		customerDAO.save(c1);
		
		Customer found = customerDAO.findById(c1.getId());
		
		assertThat(found, is(not(nullValue())));
		assertThat(found.getFirstname(), is("John"));
		assertThat(found.getLastname(), is("Doe"));
	}
	
	@Test
	public void testUpdateCustomer(){
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		customerDAO.save(c1);
		
		c1.setEmailAddress(new EmailAddress("new@email.com"));
		Customer updated = customerDAO.update(c1);
		
		assertThat(updated, is(not(nullValue())));
		assertThat(updated.getEmailAddress().toString(), is("new@email.com"));
	}
	
	@Test
	public void testFindByJSONQuery(){
		
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
		c2.setAddresses(usaAddresses);
		c2.setEmailAddress(new EmailAddress("maria@jones.com"));
		
		Customer c3 = new Customer("Jack", "Sparrow");
		c3.setAddresses(ukAddresses);
		c3.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		customerDAO.save(c1);
		customerDAO.save(c2);
		customerDAO.save(c3);
		
		String query  = "{\"addresses.country\" : \"USA\"}";
		List<Customer> customers = customerDAO.findByQuery(query);
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers.size(), is(2));
	}

}
