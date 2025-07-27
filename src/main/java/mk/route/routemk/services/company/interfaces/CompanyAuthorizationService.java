package mk.route.routemk.services.company.interfaces;

import mk.route.routemk.models.TransportOrganizer;

public interface CompanyAuthorizationService {
    Integer getAuthenticatedTransportOrganizerId();
    TransportOrganizer getAuthenticatedTransportOrganizer();
    boolean isAuthorizedTransportOrganizer(Integer transportOrganizerId);
}
