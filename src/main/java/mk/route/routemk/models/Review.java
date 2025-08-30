package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "review")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "rating")
    private Integer rating;

    public Review(Integer reviewId, Account account, Ticket ticket, String description, Integer rating) {
        this.reviewId = reviewId;
        this.account = account;
        this.ticket = ticket;
        this.description = description;
        this.rating = rating;
    }

    public Review() {

    }

    public Integer getReviewId() {
        return reviewId;
    }

    public Account getAccount() {
        return account;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRating() {
        return rating;
    }
}