package mk.route.routemk.models.views;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Table(name = "top_selling_routes_view")
public class TopSellingRouteView {

    @Id
    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "from_location_name")
    private String fromLocationName;

    @Column(name = "to_location_name")
    private String toLocationName;

    @Column(name = "transport_organizer_name")
    private String transportOrganizerName;

    @Column(name = "total_tickets_sold")
    private Integer totalTicketsSold;

    @Column(name = "total_revenue")
    private BigDecimal totalRevenue;

    @Column(name = "avg_ticket_price")
    private BigDecimal avgTicketPrice;

    // Constructor
    public TopSellingRouteView() {
    }

    // Getters only (read-only entity)
    public Integer getRouteId() {
        return routeId;
    }

    public String getFromLocationName() {
        return fromLocationName;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public String getTransportOrganizerName() {
        return transportOrganizerName;
    }

    public Integer getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public BigDecimal getAvgTicketPrice() {
        return avgTicketPrice;
    }

    // Optional: toString for debugging
    @Override
    public String toString() {
        return "TopSellingRouteView{" +
                "routeId=" + routeId +
                ", fromLocationName='" + fromLocationName + '\'' +
                ", toLocationName='" + toLocationName + '\'' +
                ", transportOrganizerName='" + transportOrganizerName + '\'' +
                ", totalTicketsSold=" + totalTicketsSold +
                ", totalRevenue=" + totalRevenue +
                ", avgTicketPrice=" + avgTicketPrice +
                '}';
    }
}
