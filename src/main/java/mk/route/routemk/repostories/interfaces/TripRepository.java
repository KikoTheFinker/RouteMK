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
}
