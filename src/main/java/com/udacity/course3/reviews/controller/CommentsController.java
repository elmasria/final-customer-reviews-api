package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.nosql.CommentDocument;
import com.udacity.course3.reviews.entity.sql.Comment;
import com.udacity.course3.reviews.entity.sql.Review;
import com.udacity.course3.reviews.repository.CommentDocumentsRepository;
import com.udacity.course3.reviews.repository.CommentsRepository;
import com.udacity.course3.reviews.repository.ReviewDocumentsRepository;
import com.udacity.course3.reviews.repository.ReviewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final ReviewsRepository reviewsRepository;
    private final CommentsRepository commentsRepository;
    private final ReviewDocumentsRepository reviewDocumentsRepository;
    private final CommentDocumentsRepository commentDocumentsRepository;

    public CommentsController(ReviewsRepository reviewsRepository, CommentsRepository commentsRepository, ReviewDocumentsRepository reviewDocumentsRepository, CommentDocumentsRepository commentDocumentsRepository) {
        this.reviewsRepository = reviewsRepository;
        this.commentsRepository = commentsRepository;
        this.reviewDocumentsRepository = reviewDocumentsRepository;
        this.commentDocumentsRepository = commentDocumentsRepository;
    }

    /**
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody @Valid Comment comment) {
        Optional<Review> optionalReview = reviewsRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            comment.setReview(review);
            comment = commentsRepository.save(comment);

            CommentDocument commentDocument = new CommentDocument();
            commentDocument.setId(comment.getId());
            commentDocument.setComment(comment.getComment());
            commentDocument.setOwner(comment.getOwnerName());
            commentDocument.setReviewId(comment.getReview().getId());

            commentDocumentsRepository.save(commentDocument);

            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<CommentDocument>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        List<CommentDocument> reviewsList = commentDocumentsRepository.findAllByReviewId(reviewId);
        if (reviewsList != null) {
            return new ResponseEntity<>(reviewsList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}