package mk.route.routemk.models.views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "company_performance_view")
public class CompanyPerformanceView {

    @Id
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "routes_operated")
    private Long routesOperated;

    @Column(name = "trips_organized")
    private Long tripsOrganized;

    @Column(name = "total_tickets_sold")
    private Long totalTicketsSold;

    @Column(name = "total_revenue")
    private Double totalRevenue;

    @Column(name = "avg_ticket_price")
    private Double avgTicketPrice;

    @Column(name = "unique_customers")
    private Long uniqueCustomers;

    @Column(name = "avg_rating")
    private Double avgRating;

    // Constructors
    public CompanyPerformanceView() {
    }

    public CompanyPerformanceView(String companyName, Long routesOperated, Long tripsOrganized,
                                  Long totalTicketsSold, Double totalRevenue, Double avgTicketPrice,
                                  Long uniqueCustomers, Double avgRating) {
        this.companyName = companyName;
        this.routesOperated = routesOperated;
        this.tripsOrganized = tripsOrganized;
        this.totalTicketsSold = totalTicketsSold;
        this.totalRevenue = totalRevenue;
        this.avgTicketPrice = avgTicketPrice;
        this.uniqueCustomers = uniqueCustomers;
        this.avgRating = avgRating;
    }

    // Getters and Setters
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getRoutesOperated() {
        return routesOperated;
    }

    public void setRoutesOperated(Long routesOperated) {
        this.routesOperated = routesOperated;
    }

    public Long getTripsOrganized() {
        return tripsOrganized;
    }

    public void setTripsOrganized(Long tripsOrganized) {
        this.tripsOrganized = tripsOrganized;
    }

    public Long getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(Long totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Double getAvgTicketPrice() {
        return avgTicketPrice;
    }

    public void setAvgTicketPrice(Double avgTicketPrice) {
        this.avgTicketPrice = avgTicketPrice;
    }

    public Long getUniqueCustomers() {
        return uniqueCustomers;
    }

    public void setUniqueCustomers(Long uniqueCustomers) {
        this.uniqueCustomers = uniqueCustomers;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}