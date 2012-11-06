/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bodybuilding.techtalk.domain.Order;

/**
 * @author martin
 *
 */
public interface OrderPagingAndSortingRepository extends PagingAndSortingRepository<Order, Long> {

}
