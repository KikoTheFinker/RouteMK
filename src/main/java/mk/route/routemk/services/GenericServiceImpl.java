package mk.route.routemk.services;

import mk.route.routemk.exceptions.EntityNotFoundException;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.GenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    protected final GenericRepository<T, ID> genericRepository;

    public GenericServiceImpl(GenericRepository<T, ID> genericRepository) {
        this.genericRepository = genericRepository;
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
    public void deleteById(ID id) {
        genericRepository.deleteById(id);
    }

    @Override
    public T findById(ID id) {
        return genericRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found", id));
    }

    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }


    @Override
    public T findOneByPredicate(Specification<T> filter) {
        return genericRepository.findOne(filter).orElseThrow(() -> new EntityNotFoundException("Entity not found", filter));
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
