/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
public class CustomerCrudRepositoryTest {
	
	@Autowired
	CustomerCrudRepository crud;
	
	@Before
	public void setUp(){
		
		crud.deleteAll();
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
		
		crud.save(c1);
		crud.save(c2);
		
		/*Iterable<Customer> customers = crud.findAll();
		Iterator<Customer> iter = customers.iterator();
		*/
		
		assertThat(crud.count(), is(2l));
		//assertThat(customers, hasItems(c3));
	}
	
	@Test
	public void testRemoveCustomer(){
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		crud.save(c1);
		crud.save(c2);
		
		long sizeBefore = crud.count();
		
		crud.delete(c1);
		
		long sizeAfter = crud.count();
		
		assertThat(sizeBefore, is(2l));
		assertThat(sizeAfter, is(1l));
	}
	
	@Test
	public void testUpdateCustomer(){
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));		
		
		crud.save(c1);
		
		c1.setEmailAddress(new EmailAddress("new@email.com"));
		
		Customer updated = crud.save(c1);
		
		assertThat(updated, is(not(nullValue())));
		assertThat(updated.getEmailAddress().toString(), is("new@email.com"));
	}
	
	@Test
	public void testFindByEmailAddress(){
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		crud.save(c1);
		crud.save(c2);
		
		Customer found = crud.findByEmailAddress(new EmailAddress("maria@doe.com"));
		
		assertThat(found, is(not(nullValue())));
		assertThat(found.getEmailAddress().toString(), is("maria@doe.com"));
	}
	
	@Test
	public void testFindByQueryEmailAddress(){
		
		Address address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		Customer c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		 
		crud.save(c1);
		crud.save(c2);
		
		Customer found = crud.findByEmailAddress("maria@doe.com");
		
		assertThat(found, is(not(nullValue())));
		assertThat(found.getEmailAddress().toString(), is("maria@doe.com"));
	}

	@Test
	public void testFindByLastnameLike(){
		
		String regex = "Jo*";
		
		Set<Address> usaAddresses = new HashSet<Address>();
		Set<Address> ukAddresses = new HashSet<Address>();
		Address usaAddress = new Address("1st Street", "Miami", "USA");
		Address ukAddress = new Address("Some Port", "Liverpool", "UK");
		usaAddresses.add(usaAddress);		
		ukAddresses.add(ukAddress);
		
		Customer c1 = new Customer("John", "Jonathan");
		c1.setAddresses(usaAddresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Jones");
		c2.setAddresses(usaAddresses);
		c2.setEmailAddress(new EmailAddress("maria@jones.com"));
		
		Customer c3 = new Customer("Jack", "Sparrow");
		c3.setAddresses(ukAddresses);
		c3.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		crud.save(c1);
		crud.save(c2);
		crud.save(c3);
		
		List<Customer> customers = crud.findByLastnameLike(regex);
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers.size(), is(2));
	}
	
	@Test
	public void testFindByCountry(){
		
		Set<Address> usaAddresses = new HashSet<Address>();
		Set<Address> ukAddresses = new HashSet<Address>();
		Address usaAddress = new Address("1st Street", "Miami", "USA");
		Address ukAddress = new Address("Some Port", "Liverpool", "UK");
		usaAddresses.add(usaAddress);		
		ukAddresses.add(ukAddress);
		
		Customer c1 = new Customer("John", "Jonathan");
		c1.setAddresses(usaAddresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		Customer c2 = new Customer("Maria", "Jones");
		c2.setAddresses(usaAddresses);
		c2.setEmailAddress(new EmailAddress("maria@jones.com"));
		
		Customer c3 = new Customer("Jack", "Sparrow");
		c3.setAddresses(ukAddresses);
		c3.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		crud.save(c1);
		crud.save(c2);
		crud.save(c3);
		
		List<Customer> customers = crud.findByAddresses_Country("USA");
		
		assertThat(customers, is(not(nullValue())));
		assertThat(customers.size(), is(2));
	}
	
	
}
