package mk.route.routemk.services.company.interfaces;

public interface CompanyAuthorizationService {
    Integer getAuthenticatedTransportOrganizerId();

    boolean isAuthorizedTransportOrganizer(Integer transportOrganizerId);
}
