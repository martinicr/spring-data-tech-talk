/**
 * 
 */
package com.bodybuilding.techtalk.repository;

//import static com.oreilly.springdata.mongodb.core.CoreMatchers.named;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bodybuilding.techtalk.domain.Product;

//import com.oreilly.springdata.mongodb.core.Product;

/**
 * @author martin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class ProductCrudRepositoryTest {
	
	@Autowired
	ProductCrudRepository repository;
	
	@Before
	public void setUp(){
		repository.deleteAll();
	}

	@Test
	public void lookupProductsByDescription() {

		repository.save(new Product("Camera bag", new BigDecimal(49.99), "8mp water resistant camera. Available in black and blue"));
		
		Pageable pageable = new PageRequest(0, 1, Direction.DESC, "name");
		Page<Product> page = repository.findByDescriptionContaining("black", pageable);

		List<Product> products = page.getContent();
		
		assertThat(products, hasSize(1));
		assertThat(products.get(0).getName(), is("Camera bag"));
	}

}
