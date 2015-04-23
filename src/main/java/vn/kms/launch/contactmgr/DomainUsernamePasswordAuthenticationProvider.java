package vn.kms.launch.contactmgr;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.google.common.base.Optional;

public class DomainUsernamePasswordAuthenticationProvider implements
        AuthenticationProvider {

    private TokenService tokenService;
    private ExternalServiceAuthenticator externalServiceAuthenticator;
    public DomainUsernamePasswordAuthenticationProvider(
            TokenService tokenService,
            ExternalServiceAuthenticator externalServiceAuthenticator) {
       this.tokenService = tokenService;
       this.externalServiceAuthenticator = externalServiceAuthenticator;        
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Optional<String>userName =(Optional)authentication.getPrincipal();
        Optional<String>password=(Optional)authentication.getCredentials();
        
        
        if(!userName.isPresent()||!password.isPresent()){
            
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        AuthenticationWithToken resultOfAuthentication = externalServiceAuthenticator.authenticate(userName.get(), password.get());
        String newToken = tokenService.generateToken();
        resultOfAuthentication.setToken(newToken);
        tokenService.storeToken(newToken, resultOfAuthentication);
        
        return resultOfAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
