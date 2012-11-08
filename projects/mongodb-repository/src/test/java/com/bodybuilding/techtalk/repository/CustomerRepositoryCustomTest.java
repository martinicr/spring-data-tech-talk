/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class CustomerRepositoryCustomTest {
	
	@Autowired
	@Qualifier("custom")
	CustomerRepositoryCustom custom;
	
	Customer c1;
	
	@Before
	public void setUp(){
		custom.deleteAll();
		
		Set<Address> usaAddresses = new HashSet<Address>();
		Address usaAddress = new Address("1st Street", "Miami", "USA");
		usaAddresses.add(usaAddress);
		
		c1 = new Customer("John", "Jonathan");
		c1.setAddresses(usaAddresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		custom.save(c1);
	}

	@Test
	public void testFindByEmailCustom() {

		Customer newCustomer = custom.findByEmailAddress(new EmailAddress("john@doe.com"));
				
		assertThat(newCustomer, is(not(nullValue())));
		assertThat(newCustomer.getFirstname(), is("John"));
		assertThat(newCustomer.getLastname(), is("Jonathan"));
	}

}
