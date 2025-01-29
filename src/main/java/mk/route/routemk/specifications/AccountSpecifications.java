package mk.route.routemk.specifications;

import mk.route.routemk.models.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecifications {
    /**
     * Creates a specification to find an account by email.
     *
     * @param email The email address to search for.
     * @return A {@link Specification<Account>} that checks if an account has the given email.
     */
    public static Specification<Account> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }
}
