package vn.kms.launch.contactmgr.infrastructure;

public interface ExternalServiceAuthenticator {

    AuthenticationWithToken authenticate(String username, String password);
}
