package vn.kms.launch.contactmgr.infrastructure;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.util.HashString;

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
        
        String userName = tokenService.extractUsername(token.get());
        User user = tokenService.getUser(userName);
        if (user != null){
        	String hash = HashString.MD5(user.getUsername()+user.getPassword());
        	System.out.println(hash+"..."+ tokenService.getHashUserNameAndPassWord(token.get()));
            if(hash.equals(tokenService.getHashUserNameAndPassWord(token.get()))){
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
