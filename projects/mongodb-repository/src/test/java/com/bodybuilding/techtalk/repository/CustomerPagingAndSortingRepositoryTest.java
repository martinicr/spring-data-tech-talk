/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
public class CustomerPagingAndSortingRepositoryTest {
	
	@Autowired
	CustomerPagingAndSortingRepository repository;
	
	@Before
	public void setUp(){
		repository.deleteAll();
	}

	@Test
	public void testPaging() {
		
		saveCustomers();
		
		Page<Customer> page1 = repository.findAll(new PageRequest(0, 5));
		Page<Customer> page2 = repository.findAll(new PageRequest(1, 5));
		
		assertThat(page1, is(not(nullValue())));
		assertThat(page1.isFirstPage(), is(true));
		assertThat(page1.getNumberOfElements(), is(5));
		assertThat(page1.getSize(), is(5));
		assertThat(page2, is(not(nullValue())));
		assertThat(page2.getNumberOfElements(), is(3));
		assertThat(page2.getSize(), is(5));
	}
	
	@Test
	public void testSorting(){
		
		saveCustomers();
		
		Iterable<Customer> customers = repository.findAll(new Sort(Direction.ASC, "lastname"));
		Iterator<Customer> itr = customers.iterator();
		
		//TODO: Compare "customers" against an expected result  
		while(itr.hasNext()){
			System.out.println(itr.next().getLastname());
		}
	}
	
	private void saveCustomers(){
		
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
		
		Customer c4 = new Customer("Brad", "Pitt");
		c4.setAddresses(ukAddresses);
		c4.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		Customer c5 = new Customer("Kevin", "Bacon");
		c5.setAddresses(ukAddresses);
		c5.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		Customer c6 = new Customer("Jennifer", "Aniston");
		c6.setAddresses(ukAddresses);
		c6.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		Customer c7 = new Customer("Will", "Ferrel");
		c7.setAddresses(ukAddresses);
		c7.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		Customer c8 = new Customer("Salma", "Hayek");
		c8.setAddresses(ukAddresses);
		c8.setEmailAddress(new EmailAddress("jack@sparrow.com"));
		
		
		List<Customer> customersToSave = new ArrayList<Customer>();
		customersToSave.add(c1);
		customersToSave.add(c2);
		customersToSave.add(c3);
		customersToSave.add(c4);
		customersToSave.add(c5);
		customersToSave.add(c6);
		customersToSave.add(c7);
		customersToSave.add(c8);
		
		repository.save(customersToSave);
	}

}
