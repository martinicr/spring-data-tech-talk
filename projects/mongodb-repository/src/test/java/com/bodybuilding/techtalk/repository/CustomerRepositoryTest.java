/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bodybuilding.techtalk.domain.Address;
import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;

/**
 * @author martin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class CustomerRepositoryTest {
	
	
	@Autowired
	CustomerRepository repository;
	
	@Before
	public void setUp(){
		
		repository.deleteAll();
	}

	@Test
	public void testFindAll() {
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		repository.save(c1);
		repository.save(c2);
		
		List<Customer> customers = repository.findAll();
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers.size(), is(2));
	}
	
	@Test
	public void testFindByLastname(){
	
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Jones");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		repository.save(c1);
		repository.save(c2);
		
		List<Customer> customers = repository.findByLastname("Doe");
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers.size(), is(1));
		assertThat(customers.get(0).getLastname(), is("Doe"));
	}

}
