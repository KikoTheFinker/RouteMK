package mk.route.routemk.services.auth.interfaces;

import mk.route.routemk.models.Account;

import java.util.Optional;
import java.util.Set;

public interface AuthenticationService {
    Optional<Account> getAuthenticatedAccount();

    Integer getAuthenticatedUserId();

    Integer getTransportOrganizerId();

    Integer getTransportOrganizerIdFromDriverAccountId();

    Set<String> getRoles();

    boolean hasRole(String role);
}

