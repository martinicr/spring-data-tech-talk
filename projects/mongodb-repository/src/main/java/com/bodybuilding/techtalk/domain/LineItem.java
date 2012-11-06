/**
 * 
 */
package com.bodybuilding.techtalk.domain;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.util.Assert;

/**
 * @author martin
 *
 */
public class LineItem {

	@Id
	private BigInteger id;
	
	@DBRef
	private Product product;
	private BigDecimal price;
	private int amount;

	public LineItem(Product product) {
		this(product, 1);
	}

	/**
	 * Creates a new {@link LineItem} for the given {@link Product} and amount.
	 * 
	 * @param product must not be {@literal null}.
	 * @param amount
	 */
	public LineItem(Product product, int amount) {

		//Assert.notNull(product, "The given Product must not be null!");
		//Assert.isTrue(amount > 0, "The amount of Products to be bought must be greater than 0!");

		this.product = product;
		this.amount = amount;
		this.price = product.getPrice();
	}

	public LineItem() {

	}

	public Product getProduct() {
		return product;
	}

	public int getAmount() {
		return amount;
	}

	public BigDecimal getUnitPrice() {
		return price;
	}

	public BigDecimal getTotal() {
		return price.multiply(BigDecimal.valueOf(amount));
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
	
}
