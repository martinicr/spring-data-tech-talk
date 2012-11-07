/**
 * 
 */
package com.bodybuilding.techtalk.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.Assert;

/**
 * @author martin
 *
 */
@Document
public class Product {
	
	@Id
	private BigInteger id;
	private String name;
	private String description;
	private BigDecimal price;
	
	public Product(){ }
	
	private Map<String, String> attributes = new HashMap<String, String>();

	public Product(String name, BigDecimal price) {
		this(name, price, null);
	}

	@PersistenceConstructor
	public Product(String name, BigDecimal price, String description) {

		//Assert.hasText(name, "Name must not be null or empty!");
		//Assert.isTrue(BigDecimal.ZERO.compareTo(price) < 0, "Price must be greater than zero!");

		this.name = name;
		this.price = price;
		this.description = description;
	}

	public void setAttribute(String name, String value) {

		//Assert.hasText(name);

		if (value == null) {
			this.attributes.remove(value);
		} else {
			this.attributes.put(name, value);
		}
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, String> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public BigDecimal getPrice() {
		return price;
	}	

}
