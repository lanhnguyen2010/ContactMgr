package vn.kms.launch.contactmgr.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;

import com.google.common.base.Optional;

public class DomainUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;
    private ExternalServiceAuthenticator externalServiceAuthenticator;
    
    @Autowired
    UserService userService;
    
    public DomainUsernamePasswordAuthenticationProvider( TokenService tokenService, ExternalServiceAuthenticator externalServiceAuthenticator) {
       this.tokenService = tokenService;
       this.externalServiceAuthenticator = externalServiceAuthenticator;        
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        
        Optional<String> userName = (Optional) authentication.getPrincipal();
        Optional<String> password= (Optional) authentication.getCredentials();
        
        if(!userName.isPresent()||!password.isPresent()){   
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        
        if (credentialsInvalid(userName, password)){
            throw new BadCredentialsException("Invalid username password");
        }
        
        AuthenticationWithToken resultOfAuthentication = externalServiceAuthenticator.authenticate(userName.get(), password.get());
        User user = (User) resultOfAuthentication.getPrincipal();
        String newToken = tokenService.generateToken(user.getUsername(),resultOfAuthentication.getCredentials().toString());
        resultOfAuthentication.setToken(newToken);
        return resultOfAuthentication;
    }
    
    private boolean credentialsInvalid(Optional<String> username,
            Optional<String> password) {
        User user = userService.findByUsername(username.get());
        if(user == null || !user.getPassword().equals(password.get())){
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // TODO Auto-generated method stub
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
