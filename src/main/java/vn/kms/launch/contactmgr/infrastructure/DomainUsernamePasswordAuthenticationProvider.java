package vn.kms.launch.contactmgr.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;

import com.google.common.base.Optional;

public class DomainUsernamePasswordAuthenticationProvider implements AuthenticationProvider {
    private TokenService tokenService;
    @Autowired
    UserService userService;

    public DomainUsernamePasswordAuthenticationProvider(
            TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        Optional<String> userName = (Optional) authentication.getPrincipal();
        Optional<String> password = (Optional) authentication.getCredentials();

        if (!userName.isPresent() || !password.isPresent()) {
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        System.out.println("lejrlkj"+userName.get());
        if (credentialsInvalid(userName, password)) {
            throw new BadCredentialsException("Invalid username password");
        }
        String userRole = getRoleUser(userName, password);
        AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(
                userService.findByUsernameOrEmail(userName.get()), password.get(),
                AuthorityUtils.createAuthorityList(userRole));
        User user = (User) resultOfAuthentication.getPrincipal();

        String newToken = tokenService.generateToken(user.getUsername(),
                resultOfAuthentication.getCredentials().toString());
        resultOfAuthentication.setToken(newToken);
        return resultOfAuthentication;
    }

    private String getRoleUser(Optional<String> username,
            Optional<String> password) {
        User user = userService.findByUsernameOrEmail(username.get());
        if (user == null || !user.getPassword().equals(password.get())) {
            return null;
        }
        return user.getRole();
    }

    private boolean credentialsInvalid(Optional<String> username,
            Optional<String> password) {
        User user = userService.findByUsernameOrEmail(username.get());
        if (user == null || !user.getPassword().equals(password.get())) {
            return true;
        }
        
        return false;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
