package vn.kms.launch.contactmgr;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class BackendAdminUsernamePasswordAuthenticationProvider implements
        AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication arg0)
            throws AuthenticationException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        // TODO Auto-generated method stub
        return false;
    }

}
