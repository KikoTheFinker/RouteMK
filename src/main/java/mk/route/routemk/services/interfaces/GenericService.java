package mk.route.routemk.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GenericService<T, ID> {
    T save(T entity);

    void delete(T entity);

    T findById(ID id);

    List<T> findAll();

    T findOneByPredicate(Specification<T> filter);

    List<T> findAllByPredicate(Specification<T> filter);

    Page<T> findAllPageableByPredicate(Specification<T> filter, Pageable pageable);
}
