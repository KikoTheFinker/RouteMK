package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    private Integer ticketId;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "gets_on_location_id", referencedColumnName = "location_id")
    private Location pickupLocation;

    @ManyToOne
    @JoinColumn(name = "gets_off_location_id", referencedColumnName = "location_id")
    private Location leavesAtLocation;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    private Payment paymentId;

    @Column(name = "date_purchased")
    private LocalDate datePurchased;

    @Column(name = "time_purchased")
    private LocalTime timePurchased;

    @Column(name = "price")
    private Double price;

    @Column(name = "seat")
    private String seat;

    public Trip getTrip() {
        return trip;
    }

    public Ticket(Trip trip,
                  Location pickupLocation,
                  Location leavesAtLocation,
                  Account account,
                  Payment paymentId,
                  LocalDate datePurchased,
                  LocalTime timePurchased,
                  Double price,
                  String seat) {
        this.trip = trip;
        this.pickupLocation = pickupLocation;
        this.leavesAtLocation = leavesAtLocation;
        this.account = account;
        this.paymentId = paymentId;
        this.datePurchased = datePurchased;
        this.timePurchased = timePurchased;
        this.price = price;
        this.seat = seat;
    }

    public Ticket() {
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getLeavesAtLocation() {
        return leavesAtLocation;
    }

    public Account getAccount() {
        return account;
    }

    public Payment getPaymentId() {
        return paymentId;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public LocalTime getTimePurchased() {
        return timePurchased;
    }

    public Double getPrice() {
        return price;
    }

    public String getSeat() {
        return seat;
    }
}