package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Integer driverId;

    @ManyToOne
    @JoinColumn(name = "transport_organizer_id", referencedColumnName = "transport_organizer_id")
    private TransportOrganizer tranOrg;

    @Column(name = "years_experience")
    private int yearsExperience;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    public Driver(Account account, TransportOrganizer tranOrg, int yearsExperience) {
        this.account = account;
        this.tranOrg = tranOrg;
        this.yearsExperience = yearsExperience;
    }
    public TransportOrganizer getTranOrg() {
        return tranOrg;
    }
    public Driver() {
    }
}
