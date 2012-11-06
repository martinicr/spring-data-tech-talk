/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bodybuilding.techtalk.domain.Customer;

/**
 * @author martin
 *
 */
public interface CustomerPagingAndSortingRepository extends
		PagingAndSortingRepository<Customer, Long> {

}
