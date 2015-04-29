package vn.kms.launch.contactmgr.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;

import com.google.common.base.Optional;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;
    private ExternalServiceAuthenticator externalServiceAuthenticator;
    @Autowired
    UserService userService;
    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<String> token = (Optional) authentication.getPrincipal();
        if (!token.isPresent() || token.get().isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }
        if (!tokenService.contains(token.get())) {
            throw new BadCredentialsException("Invalid token or token expired");
        }
        
        String userName = tokenService.getUserName(token.get());
        System.out.println("USER...." + userName);
        if(isValidUserName(userName)){
            User user = getUserValid(userName);
            String token2 = tokenService.generateToken(user.getUsername(), user.getPassword());
            System.out.println("token2: "+token2);
            if(token2.equals(token.get())){
                AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(user, token,AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole()));
                System.out.println("resultOfAuthentication: "+ (resultOfAuthentication.isAuthenticated()));
                return resultOfAuthentication;
            }
            
        }

        return null;
       // return tokenService.retrieve(token.get());
    }

    public User getUserValid(String userName){
        User user = userService.findByUsername(userName);
        return user;
    }
    private boolean isValidUserName(String userName) {
        User user = userService.findByUsername(userName);
        if(user!=null)
            return true;
        return false;
    }
    @Override
    
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
