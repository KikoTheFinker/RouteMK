package mk.route.routemk.repostories.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * GenericRepository that extends JpaRepository to provide basic CRUD operations and JpaSpecificationExecutor
 * to support queries using Specifications
 *
 * @param <T>  The entity type that this repository will handle.
 * @param <ID> The type of the primary key of the entity.
 */
@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
