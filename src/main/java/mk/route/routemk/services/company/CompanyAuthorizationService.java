package mk.route.routemk.services.company;

import mk.route.routemk.services.auth.AuthenticationService;
import org.springframework.stereotype.Service;

@Service
public class CompanyAuthorizationService {
    private final AuthenticationService authenticationService;

    public CompanyAuthorizationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * If the user is a driver, it retrieves the ID from the Transport Organizer that driver "works" for
     * @return the authenticated Transport Organizer's ID.
     */
    public Integer getAuthenticatedTransportOrganizerId() {
        return authenticationService.getTransportOrganizerId() != null
                ? authenticationService.getTransportOrganizerId()
                : authenticationService.getTransportOrganizerIdFromDriverAccountId();
    }

    /**
     * @param transportOrganizerId the Transport Organizer ID
     * @return Checks if the authenticated user is the provided Transport Organizer ID.
     */
    public boolean isAuthorizedTransportOrganizer(Integer transportOrganizerId) {
        return transportOrganizerId.equals(getAuthenticatedTransportOrganizerId());
    }
}
