package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Account;
import org.springframework.stereotype.Repository;

/**
 * AccountRepository is a repository interface for managing Account entities.
 * It extends GenericRepository, inheriting common CRUD operations and support for Specifications.
 */
@Repository
public interface AccountRepository extends GenericRepository<Account, Integer> {
}