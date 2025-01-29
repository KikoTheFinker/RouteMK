package mk.route.routemk.specifications;

import mk.route.routemk.models.Account;
import org.springframework.data.jpa.domain.Specification;

public class AccountSpecifications {
    public static Specification<Account> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }
}
