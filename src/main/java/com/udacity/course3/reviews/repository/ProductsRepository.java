package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.sql.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer> {
}
