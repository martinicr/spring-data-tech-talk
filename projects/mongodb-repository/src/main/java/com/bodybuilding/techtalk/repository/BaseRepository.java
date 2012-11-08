/**
 * 
 */
package com.bodybuilding.techtalk.repository;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * @author martin
 *
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

	
	Iterable<T> findAll(Pageable pageable);
	
	<S extends T> S save(S entity);
	
}
