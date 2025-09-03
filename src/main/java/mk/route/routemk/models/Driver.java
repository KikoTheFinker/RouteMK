package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "drivers")
    private List<Trip> trips = new ArrayList<>();

    public Driver(Account account, TransportOrganizer tranOrg, int yearsExperience) {
        this.account = account;
        this.tranOrg = tranOrg;
        this.yearsExperience = yearsExperience;
    }

    @Override
    public String toString() {
        return String.format("Driver: %s %s, for %s", account.getName(), account.getSurname(), tranOrg.getCompanyName());
    }

    public TransportOrganizer getTranOrg() {
        return tranOrg;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Driver() {
    }
}
