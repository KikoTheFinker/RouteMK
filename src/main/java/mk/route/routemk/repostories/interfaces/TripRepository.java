package mk.route.routemk.repostories.interfaces;

import jakarta.transaction.Transactional;
import mk.route.routemk.models.Trip;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TripRepository extends GenericRepository<Trip, Integer>{
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO trip (transport_organizer_id, route_id, free_seats, date, status) " +
            "VALUES (:transportOrganizerId, :routeId, :freeSeats, :date, CAST(:status AS trip_status))", nativeQuery = true)
    void insertTrip(@Param("transportOrganizerId") int transportOrganizerId,
                    @Param("routeId") int routeId,
                    @Param("freeSeats") int freeSeats,
                    @Param("date") LocalDate date,
                    @Param("status") String status);
}
