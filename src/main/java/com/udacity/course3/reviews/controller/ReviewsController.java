package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.nosql.ReviewDocument;
import com.udacity.course3.reviews.entity.sql.Product;
import com.udacity.course3.reviews.entity.sql.Review;
import com.udacity.course3.reviews.repository.ProductsRepository;
import com.udacity.course3.reviews.repository.ReviewDocumentsRepository;
import com.udacity.course3.reviews.repository.ReviewsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    private final ReviewsRepository reviewsRepository;
    private final ProductsRepository productsRepository;
    private final ReviewDocumentsRepository reviewDocumentsRepository;

    public ReviewsController(ReviewsRepository reviewsRepository, ProductsRepository productsRepository, ReviewDocumentsRepository reviewDocumentsRepository) {
        this.reviewsRepository = reviewsRepository;
        this.productsRepository = productsRepository;
        this.reviewDocumentsRepository = reviewDocumentsRepository;
    }

    /**
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Review> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody @Valid Review review) {
        Optional<Product> optionalProduct = productsRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            review.setProduct(product);
            review = reviewsRepository.save(review);

            ReviewDocument reviewDocument = new ReviewDocument();
            reviewDocument.setOwner(review.getOwnerName());
            reviewDocument.setId(review.getId());
            reviewDocument.setProductId(review.getProduct().getId());
            reviewDocument.setReview(review.getReview());
            reviewDocument.setTitle(review.getTitle());

            reviewDocumentsRepository.save(reviewDocument);

            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<ReviewDocument>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        List<ReviewDocument> reviews = reviewDocumentsRepository.findAllByProductId(productId);
        if (reviews != null){
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}