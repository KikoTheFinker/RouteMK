package mk.route.routemk.services;

import mk.route.routemk.exceptions.EntityNotFoundException;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private final GenericRepository<T, ID> genericRepository;
    private final Class<T> entityType;

    public GenericServiceImpl(GenericRepository<T, ID> genericRepository, Class<T> entityType) {
        this.genericRepository = genericRepository;
        this.entityType = entityType;
    }

    @Override
    public T save(T entity) {
        return genericRepository.save(entity);
    }

    @Override
    public void delete(T entity) {
        genericRepository.delete(entity);
    }

    @Override
    public T findById(ID id) {
        return genericRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(entityType.getSimpleName(), id));
    }


    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public T findOneByPredicate(Specification<T> filter) {
        return genericRepository.findOne(filter).orElseThrow(() -> new EntityNotFoundException(entityType.getSimpleName(), filter));
    }

    @Override
    public List<T> findAllByPredicate(Specification<T> filter) {
        return genericRepository.findAll(filter);
    }

    @Override
    public Page<T> findAllPageableByPredicate(Specification<T> filter, Pageable pageable) {
        return genericRepository.findAll(filter, pageable);
    }
}
