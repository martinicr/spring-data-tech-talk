/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.bodybuilding.techtalk.domain.Product;

/**
 * @author martin
 *
 */
public interface ProductCrudRepository extends CrudRepository<Product, Long> {

	Page<Product> findByDescriptionContaining(String string, Pageable pageable);

}
