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
	public void TestUpdateCustomer(){
		
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

}
