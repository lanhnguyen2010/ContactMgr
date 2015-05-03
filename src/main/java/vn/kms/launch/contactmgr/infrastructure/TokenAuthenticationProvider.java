package vn.kms.launch.contactmgr.infrastructure;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import vn.kms.launch.contactmgr.domain.user.User;

import com.google.common.base.Optional;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private TokenService tokenService;
    public TokenAuthenticationProvider(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<String> token = (Optional) authentication.getPrincipal();
        if (!token.isPresent() || token.get().isEmpty()) {
            throw new BadCredentialsException("Invalid token");
        }
        if (!tokenService.isExpries(token.get())) {
            throw new BadCredentialsException("Invalid token or token expired");
        }
        
        String userName = tokenService.getUserName(token.get());
        if(tokenService.isValidUserName(userName)){
            User user = tokenService.getUserValid(userName);
            String token2 = tokenService.generateTokenWithUserNameAndPassword(user.getUsername(), user.getPassword());
            System.out.println("token2: "+token2);
            if(token2.equals(tokenService.getHashUserNameAndPassWord(token.get()))){
                System.out.println("token dcsfhgf ");
                AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(user, token,AuthorityUtils.createAuthorityList(user.getRole()));
                return resultOfAuthentication;
            }
            
        }
        return null;
    }
    @Override
    
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
