package mk.route.routemk.models;

import jakarta.persistence.*;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @ManyToOne
    @JoinColumn(name = "from_location_id", referencedColumnName = "location_id")
    private Location source;

    @ManyToOne
    @JoinColumn(name = "to_location_id", referencedColumnName = "location_id")
    private Location destination;

    @ManyToOne
    @JoinColumn(name = "transport_organizer_id", referencedColumnName = "transport_organizer_id")
    private TransportOrganizer tranOrg;

    public Route(Location source, Location destination, TransportOrganizer tranOrg) {
        this.source = source;
        this.destination = destination;
        this.tranOrg = tranOrg;
    }

    public TransportOrganizer getTranOrg() {
        return tranOrg;
    }

    public Route() {

    }
}
