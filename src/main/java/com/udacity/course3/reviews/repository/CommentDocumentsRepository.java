package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.nosql.CommentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDocumentsRepository extends MongoRepository<CommentDocument, String> {
    List<CommentDocument> findAllByReviewId(Integer reviewId);
}
