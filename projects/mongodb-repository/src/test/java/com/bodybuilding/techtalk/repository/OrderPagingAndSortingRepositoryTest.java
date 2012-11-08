/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bodybuilding.techtalk.domain.Address;
import com.bodybuilding.techtalk.domain.Customer;
import com.bodybuilding.techtalk.domain.EmailAddress;
import com.bodybuilding.techtalk.domain.LineItem;
import com.bodybuilding.techtalk.domain.Order;
import com.bodybuilding.techtalk.domain.Product;

/**
 * @author martin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class OrderPagingAndSortingRepositoryTest {
	
	@Autowired
	CustomerRepository customer;
	
	@Autowired
	ProductCrudRepository product;
	
	@Autowired
	OrderPagingAndSortingRepository orderRepository;
	
	Address address;
	Customer c1, c2, c3;
	Product p1, p2;
	
	@Before
	public void setUp(){
		
		customer.deleteAll();
		product.deleteAll();
		orderRepository.deleteAll();
		
		address = new Address("1st Street", "Miami", "USA");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(address);
		
		c1 = new Customer("John", "Doe");
		c1.setAddresses(addresses);
		c1.setEmailAddress(new EmailAddress("john@doe.com"));
		
		c2 = new Customer("Maria", "Doe");
		c2.setAddresses(addresses);
		c2.setEmailAddress(new EmailAddress("maria@doe.com"));
		
		c3 = new Customer("Maria", "Doe");
		c3.setAddresses(addresses);
		c3.setEmailAddress(new EmailAddress("maria@doe.com"));		
		
		customer.save(c1);
		customer.save(c2);
		customer.save(c3);
		
		p1 = new Product("Vitamins", new BigDecimal(20), "Your source to vitamins");
		p2 = new Product("Whey Protein", new BigDecimal(30), "Whey Isolated protein");
		
		product.save(p1);
		product.save(p2);
	}
	
	@Test
	public void testReadOrder(){
		
		LineItem l1 = new LineItem(p1, 2);
		LineItem l2 = new LineItem(p2, 1);
		
		Order order = new Order(c1, address);
		order.add(l1);
		order.add(l2);
		
		orderRepository.save(order);
		
		Page<Order> page0 = orderRepository.findAll(new PageRequest(0, 10));
		
		List<Order> orders = page0.getContent();
		
		
		assertThat(orders.get(0).getCustomer(), is(not(nullValue())));
		assertThat(orders.get(0).getCustomer().getFirstname(), is("John"));
		assertThat(orders.get(0).getLineItems().size(), is(2));
	}

}
