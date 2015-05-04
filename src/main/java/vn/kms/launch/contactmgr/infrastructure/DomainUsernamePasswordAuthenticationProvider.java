package vn.kms.launch.contactmgr.infrastructure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.service.UserService;
import vn.kms.launch.contactmgr.util.HashString;

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
        if(userName.get().length()==0||password.get().length()==0){
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        
        if (!userName.isPresent() || !password.isPresent()) {
            throw new BadCredentialsException("Invalid Domain User Credentials");
        }
        
        if (credentialsValid(userName, password)==null) {
            throw new BadCredentialsException("Invalid username password");
        }
        
        User userRole = credentialsValid(userName, password);
        if(!UserInfoValid(userRole)){
            throw new BadCredentialsException("User Infomation Invalid");
        }
        
        String userGetRole = userRole.getRole();
        AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(
                userService.findByUsernameOrEmail(userName.get()), password.get(),
                AuthorityUtils.createAuthorityList(userGetRole));
        User user = (User) resultOfAuthentication.getPrincipal();

        String newToken = tokenService.generateToken(user.getUsername(),
                user.getPassword());
        resultOfAuthentication.setToken(newToken);
        return resultOfAuthentication;
    }
    private boolean UserInfoValid(User userRole) {
        Date date = new Date();
        long expired = userRole.getExpiredDate().getTime();
        if(date.getTime()>expired||!userRole.isActive()||userRole.getRole().length()==0)
            return false;
        return true;
    }

    private User credentialsValid(Optional<String> username,
            Optional<String> password) {
        User user = userService.findByUsernameOrEmail(username.get());
        if(user == null ||user.getPassword().equals(HashString.MD5(password.get())))
                return user;
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
