package com.udacity.course3.reviews.entity.nosql;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document("review")
public class ReviewDocument {
    @Id
    private Integer id;
    private Integer productId;
    private String owner;
    private String title;
    private String review;
    private List<CommentDocument> comments;
    private LocalDateTime created;

    public ReviewDocument() {
        this.created = LocalDateTime.now();
        this.comments = comments = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getIOwner() {
        return owner;
    }

    public void setOwner(String author) {
        this.owner = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<CommentDocument> getComments() {
        return comments;
    }
}
