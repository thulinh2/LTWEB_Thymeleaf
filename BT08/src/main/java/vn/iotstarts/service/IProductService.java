package vn.iotstarts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstarts.entity.Product;

public interface IProductService {
    Object findAll();
    Optional<Product> findById(Long id);
   // void save(Product product);
    void delete(Product product);
	<S extends Product> S save(S entity);
	Optional<Product> findByProductName(String name);
	Page<Product> findAll(Pageable pageable);
	List<Product> findAll(Sort sort);
	List<Product> findAllById(Iterable<Long> ids);
	<S extends Product> Optional<S> findOne(Example<S> example);
	long count();
	void deleteById(Long id);
	List<Product> findByProductNameContaining(String name);
	Page<Product> findByProductNameContaining(String name, Pageable pageable);

}
