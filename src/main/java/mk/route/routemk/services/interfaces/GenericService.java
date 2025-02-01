package mk.route.routemk.services.interfaces;

import mk.route.routemk.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface GenericService<T, ID> {

    /**
     * Saves or updates an entity in the database.
     *
     * @param entity The entity to save
     * @return The saved entity
     */
    T save(T entity);

    /**
     * Deletes an entity from the database.
     *
     * @param entity The entity to delete
     */
    void delete(T entity);


    /**
     * Deletes an entity from the database by id
     * @param id The id of the entity to delete.
     */
    void deleteById(ID id);
    /**
     * Finds an entity by its ID.
     * Throws an exception if the entity is not found.
     *
     * @param id The ID of the entity
     * @return The found entity
     * @throws EntityNotFoundException if no entity is found
     */
    T findById(ID id);

    /**
     * Retrieves all entities from the database.
     *
     * @return A list of all entities
     */
    List<T> findAll();

    /**
     * Finds a single entity that matches a given specification.
     * Throws an exception if no matching entity is found.
     *
     * @param filter The specification used for filtering
     * @return The found entity
     * @throws EntityNotFoundException if no entity matches the specification
     */
    T findOneByPredicate(Specification<T> filter);

    /**
     * Finds all entities that match a given specification.
     * If the filter is null, it will return all entities without any filtering.
     *
     * @param filter The specification used for filtering. Can be null to retrieve all entities.
     * @return A list of matching entities, or all entities if no filter is provided.
     */
    List<T> findAllByPredicate(Specification<T> filter);

    /**
     * Finds all entities matching a given specification with pagination.
     * If the filter is null, it will return Page without any filtering.
     *
     * @param filter   The specification used for filtering. Can be null to retrieve all entities.
     * @param pageable The pagination information
     * @return A page of matching entities
     */
    Page<T> findAllPageableByPredicate(Specification<T> filter, Pageable pageable);
}
