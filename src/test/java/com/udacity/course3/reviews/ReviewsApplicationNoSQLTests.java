package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.nosql.CommentDocument;
import com.udacity.course3.reviews.entity.nosql.ReviewDocument;
import com.udacity.course3.reviews.entity.sql.Comment;
import com.udacity.course3.reviews.entity.sql.Product;
import com.udacity.course3.reviews.entity.sql.Review;
import com.udacity.course3.reviews.repository.CommentDocumentsRepository;
import com.udacity.course3.reviews.repository.ReviewDocumentsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class ReviewsApplicationNoSQLTests {

    @Autowired
    private ReviewDocumentsRepository reviewDocumentsRepository;

    @Autowired
    private CommentDocumentsRepository commentDocumentsRepository;


    private ReviewDocument addTestReview(Integer id) {
        ReviewDocument reviewDocument = new ReviewDocument();
        reviewDocument.setId(id);
        reviewDocument.setOwner("Ahmad");
        reviewDocument.setProductId(1256);
        reviewDocument.setReview("this is my review");
        reviewDocument.setTitle("Review Title");
        reviewDocument = reviewDocumentsRepository.save(reviewDocument);
        return reviewDocument;
    };

    private CommentDocument addTestComment(Integer id, ReviewDocument review) {
        CommentDocument commentDocument = new CommentDocument();
        commentDocument.setId(id);
        commentDocument.setComment("my comments");
        commentDocument.setOwner("Ahmad");
        commentDocument.setReviewId(review.getId());
        commentDocument = commentDocumentsRepository.save(commentDocument);
        return commentDocument;
    }


    @Test
    public void contextLoads() {
        assertThat(reviewDocumentsRepository).isNotNull();
        assertThat(commentDocumentsRepository).isNotNull();
    }


    @Test
    public void addNewReview() throws Exception {
        ReviewDocument newReview = addTestReview(6);
        assertThat(newReview.getId()).isGreaterThan(0);
        assertThat(newReview.getTitle()).isEqualTo("Review Title");
        assertThat(newReview.getReview()).isEqualTo("this is my review");
        assertThat(newReview.getIOwner()).isEqualTo("Ahmad");
    }

    @Test
    public void getListReview() throws Exception {
        ReviewDocument newReview = addTestReview(9);
        List<ReviewDocument> reviews = reviewDocumentsRepository.findAllByProductId(1256);
        assertThat(reviews.size()).isGreaterThan(0);
    }


    @Test
    public void addNewComment() throws Exception {
        ReviewDocument newReview = addTestReview(60);
        CommentDocument savedComment = addTestComment(15,newReview);
        assertThat(savedComment.getId()).isGreaterThan(0);
        assertThat(savedComment.getComment()).isEqualTo("my comments");
        assertThat(savedComment.getOwner()).isEqualTo("Ahmad");
    }

    @Test
    public void getComments() throws Exception {
        ReviewDocument newReview = addTestReview(50);
        addTestComment(2100, newReview);
        List<CommentDocument> comments = commentDocumentsRepository.findAllByReviewId(newReview.getId());
        assertThat(comments.size()).isGreaterThan(0);
    }

}
