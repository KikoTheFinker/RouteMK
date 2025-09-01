package mk.route.routemk.specifications;

import mk.route.routemk.models.Review;
import mk.route.routemk.models.Ticket;
import mk.route.routemk.models.Trip;
import mk.route.routemk.models.Route;
import mk.route.routemk.models.TransportOrganizer;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class ReviewSpecification {

    public static Specification<Review> hasTripId(Integer tripId) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (tripId == null) {
                return cb.conjunction();
            }

            Join<Review, Ticket> ticketJoin = root.join("ticket");
            Join<Ticket, Trip> tripJoin = ticketJoin.join("trip");

            return cb.equal(tripJoin.get("tripId"), tripId);
        };
    }

    public static Specification<Review> hasRouteId(Integer routeId) {
        return (Root<Review> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (routeId == null) {
                return cb.conjunction();
            }

            Join<Review, Ticket> ticketJoin = root.join("ticket");
            Join<Ticket, Trip> tripJoin = ticketJoin.join("trip");
            Join<Trip, Route> routeJoin = tripJoin.join("route");

            return cb.equal(routeJoin.get("routeId"), routeId);
        };
    }


}