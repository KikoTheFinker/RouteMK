package mk.route.routemk.services;

import mk.route.routemk.models.Driver;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.repostories.interfaces.DriverRepository;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.DriverService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl extends GenericServiceImpl<Driver, Integer> implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(GenericRepository<Driver, Integer> genericRepository, DriverRepository driverRepository) {
        super(genericRepository);
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> findByTranOrg(TransportOrganizer tranOrg) {
        return driverRepository.findByTranOrg(tranOrg);
    }

}
