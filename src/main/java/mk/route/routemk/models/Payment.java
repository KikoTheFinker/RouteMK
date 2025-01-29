package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer paymentId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @Column(name = "date")
    private LocalDate paymentDate;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "n_tickets")
    private Integer numTickets;

    public Payment(Account account, LocalDate paymentDate, Double totalPrice, Integer numTickets) {
        this.account = account;
        this.paymentDate = paymentDate;
        this.totalPrice = totalPrice;
        this.numTickets = numTickets;
    }

    public Payment() {
    }
}
