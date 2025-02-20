package mk.route.routemk.services.company;

import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.company.interfaces.CompanyAuthorizationService;
import org.springframework.stereotype.Service;

@Service
public class CompanyAuthorizationServiceImpl implements CompanyAuthorizationService {
    private final AuthenticationService authenticationService;

    public CompanyAuthorizationServiceImpl(AuthenticationService authenticationService) {
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
