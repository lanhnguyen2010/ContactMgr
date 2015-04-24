package vn.kms.launch.contactmgr.infrastructure;

import org.springframework.security.core.authority.AuthorityUtils;

import vn.kms.launch.contactmgr.domain.user.User;

public class SomeExternalServiceAuthenticator implements ExternalServiceAuthenticator{

    @Override
    public AuthenticatedExternalWebService authenticate(String username, String password) {
        ExternalWebServiceStub externalWebService = new ExternalWebServiceStub();
        AuthenticatedExternalWebService authenticatedExternalWebService = new AuthenticatedExternalWebService(new User(username), null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_DOMAIN_USER"));
        authenticatedExternalWebService.setExternalWebService(externalWebService);
        return authenticatedExternalWebService;
    }
}
