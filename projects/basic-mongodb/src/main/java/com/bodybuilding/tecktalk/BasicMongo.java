/**
 * 
 */
package com.bodybuilding.tecktalk;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bodybuilding.tecktalk.domain.Address;
import com.bodybuilding.tecktalk.domain.Customer;
import com.bodybuilding.tecktalk.domain.EmailAddress;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * @author martin
 *
 */
public class BasicMongo {
	
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	
	Mongo mongo;
	DB db;
	DBCollection collection;
	
	public BasicMongo(){
		
	}
	
	public void connect() throws Exception{
		
		mongo = new Mongo(HOST, PORT);
		db = mongo.getDB("customersDB");
		collection = db.getCollection("customers");
	}

	
	public List<Customer> findAllCustomers(){
		
		DBCursor cursor =  collection.find();
		List<Customer> customers = new ArrayList<Customer>();
		
		while(cursor.hasNext()){
			
			//Customer c = (Customer)cursor.next();
			DBObject o = cursor.next();
			System.out.println(o.get("firstname"));
		}
		
		cursor.close();
		return customers;
		
	}

	public void save(Customer customer) throws Exception {
		
		DBObject customerDoc = new BasicDBObject();
		customerDoc.put("firstname", customer.getFirstname());
		customerDoc.put("lastname", customer.getLastname());
		
		Set<Address> addresses = customer.getAddresses();
		BasicDBList addressesDoc = new BasicDBList();
		for(Address a : addresses){
			
			DBObject addressDoc = new BasicDBObject();
			addressDoc.put("street", a.getStreet());
			addressDoc.put("city", a.getCity());
			addressDoc.put("country", a.getCountry());
			
			addressesDoc.add(addressDoc);
		}
				
		EmailAddress emailAddress = customer.getEmailAddress();
		DBObject emailAddressDoc = new BasicDBObject();
		emailAddressDoc.put("email", emailAddress.toString());
		
		customerDoc.put("addresses", addressesDoc);
		customerDoc.put("email", emailAddressDoc);
		
		
		//collection.save(customerDoc);
		collection.insert(customerDoc);
	}
	
}
