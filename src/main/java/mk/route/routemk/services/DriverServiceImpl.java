package mk.route.routemk.services;

import mk.route.routemk.models.Driver;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.repostories.interfaces.DriverRepository;
import mk.route.routemk.services.interfaces.DriverService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> findByTranOrg(TransportOrganizer tranOrg) {
        return driverRepository.findByTranOrg(tranOrg);
    }


    @Override
    public Driver save(Driver entity) {
        return driverRepository.save(entity);
    }

    @Override
    public void delete(Driver entity) {
        driverRepository.delete(entity);
    }

    @Override
    public void deleteById(Integer id) {
        driverRepository.deleteById(id);
    }

    @Override
    public Driver findById(Integer id) {
        return driverRepository.findById(id).orElse(null);
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver findOneByPredicate(Specification<Driver> filter) {
        return driverRepository.findOne(filter).orElse(null);
    }

    @Override
    public List<Driver> findAllByPredicate(Specification<Driver> filter) {
        return driverRepository.findAll(filter);
    }

    @Override
    public Page<Driver> findAllPageableByPredicate(Specification<Driver> filter, Pageable pageable) {
        return driverRepository.findAll(filter, pageable);
    }
}
