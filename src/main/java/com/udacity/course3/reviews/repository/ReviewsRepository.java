package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.sql.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByProduct_id(Integer id);
}
