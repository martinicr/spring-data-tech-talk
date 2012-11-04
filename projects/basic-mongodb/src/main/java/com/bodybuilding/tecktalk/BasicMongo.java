/**
 * 
 */
package com.bodybuilding.tecktalk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

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
	private static final String DB = "store";
	private static final String COLL = "customers"; 
	
	Mongo mongo;
	DB db;
	DBCollection collection;
	
	public BasicMongo(){ }
	
	public void connect() throws Exception{
		
		mongo = new Mongo(HOST, PORT);
		db = mongo.getDB(DB);
		collection = db.getCollection(COLL);
	}
	
	public void disconnect(){
		mongo.close();
	}
	
	public List<Customer> findAllCustomers(){
		
		DBCursor cursor =  collection.find();
		List<Customer> customers = fromCursorToCustomers(cursor);
		return customers;
	}

	public void save(Customer customer) throws Exception {
		DBObject customerDoc = fromCustomerToDBObject(customer);
		collection.save(customerDoc);
		//collection.insert(customerDoc);
	}

	public void removeAll() {
		db.dropDatabase();
	}

	public List<Customer> findCustomerByName(String firstname) {
		
		DBObject query = new BasicDBObject();
		query.put("firstname", firstname);
		
		DBCursor cursor =  collection.find(query);
		return fromCursorToCustomers(cursor);
	}
	
	private static DBObject fromCustomerToDBObject(Customer customer) {
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
		emailAddressDoc.put("value", emailAddress.toString());
		
		customerDoc.put("addresses", addressesDoc);
		customerDoc.put("email", emailAddressDoc);
		
		return customerDoc;
	}	

	private List<Customer> fromCursorToCustomers(DBCursor cursor) {
		List<Customer> customers = new ArrayList<Customer>();
		
		while(cursor.hasNext()){
			DBObject o = cursor.next();
			customers.add(fromDBObjectToCustomer(o));
		}
		cursor.close();
		return customers;
	}
	
	private static Customer fromDBObjectToCustomer(DBObject obj) {
		
		Customer customer = new Customer();
		customer.setId((obj.get("_id").toString()));
		customer.setFirstname(obj.get("firstname").toString());
		customer.setLastname(obj.get("lastname").toString());
		
		DBObject emailDoc = (BasicDBObject)obj.get("email");
		EmailAddress email = new EmailAddress(emailDoc.get("value").toString());
		
		BasicDBList addressesObj = (BasicDBList)obj.get("addresses");
		BasicDBObject[] addrArray = addressesObj.toArray(new BasicDBObject[0]); 
			
		Set<Address> addresses = new HashSet<Address>();
		for(BasicDBObject dbObj : addrArray){
			
			Address addr = new Address();
			addr.setStreet(dbObj.get("street").toString());
			addr.setCity(dbObj.get("city").toString());
			addr.setCountry(dbObj.get("country").toString());
			
			addresses.add(addr);
		}
		
		customer.setEmailAddress(email);
		customer.setAddresses(addresses);
		
		return customer;
		
	}

	public List<Customer> findCustomersByLocation(String street, String city, String country) {
		
		//QueryBuilder: smarter way to do this
		DBObject location = new BasicDBObject();
		if(!StringUtils.isEmpty(street)){
			location.put("addresses.street", street);
		}
		if(!StringUtils.isEmpty(city)){
			location.put("addresses.city", city);
		}
		if(!StringUtils.isEmpty(country)){
			location.put("addresses.country", country);
		}
		
		BasicDBObject orderby = new BasicDBObject();
		orderby.put("lastname",-1);
		
		DBCursor cursor = collection.find(location).sort(orderby);
		
		return fromCursorToCustomers(cursor);
	}

	public void deleteCustomer(Customer customer) {
		
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", new ObjectId(customer.getId()));
		
		collection.remove(queryObj);
	}

	public void update(Customer customer) {
		
		DBObject queryObj = new BasicDBObject();
		queryObj.put("_id", new ObjectId(customer.getId()));
		
		collection.update(queryObj, fromCustomerToDBObject(customer));
	}
	
}
