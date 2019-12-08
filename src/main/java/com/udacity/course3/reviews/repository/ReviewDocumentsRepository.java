package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.nosql.ReviewDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDocumentsRepository extends MongoRepository<ReviewDocument, String> {
    List<ReviewDocument> findAllByProductId(Integer productId);
}

