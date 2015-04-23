package vn.kms.launch.contactmgr;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.google.common.base.Optional;

public class AuthenticationFilter extends GenericFilterBean {

    public static final String TOKEN_SESSION_KEY = "token";
    public static final String USER_SESSION_KEY = "user";
    private AuthenticationManager authenticationManager;
    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager=authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = asHttp(request);
        HttpServletResponse httpResponse = asHttp(response);
        Optional<String> username = Optional.fromNullable(httpRequest.getHeader("X-Auth-Username"));
        Optional password = Optional.fromNullable(httpRequest.getHeader("X-Auth-Password"));
        Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Auth-Token"));
        String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);
        
        System.out.print("chay.."+resourcePath);
        
    }
    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

}
