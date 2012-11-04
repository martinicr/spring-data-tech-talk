/**
 * 
 */
package com.bodybuilding.tecktalk.domain;

import java.util.HashSet;
import java.util.Set;


/**
 * @author martin
 *
 */
public class Customer {
	
	private String id;
	private String firstname;
	private String lastname;
	private EmailAddress emailAddress;
	private Set<Address> addresses = new HashSet<Address>();
	

	public Customer(){ }
	
	public Customer(String firstname, String lastname){
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
}
