package vn.kms.launch.contactmgr;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.TokenResolver;
import com.google.common.base.Optional;

public class AuthenticationFilter extends GenericFilterBean {

    public static final String AUTHENTICATE_URL = "/api/authenticate";
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
       if(postToAuthenticate(httpRequest,resourcePath)){
           
           processUsernamePasswordAuthentication(httpResponse, username, password);
           return;
       }
        System.out.println("httpRequest.."+httpRequest.toString());
        System.out.println(" Resource Path "+resourcePath);
        
    }
    private void processUsernamePasswordAuthentication(
            HttpServletResponse httpResponse, Optional<String> username,
            Optional password) throws IOException {
       
        Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username,password);
        SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
        httpResponse.setStatus(HttpServletResponse.SC_OK);
        TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
        String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
        httpResponse.addHeader("Content-Type", "application/json");
        httpResponse.getWriter().print(tokenJsonResponse);
       
       
    }

    private Authentication tryToAuthenticateWithUsernameAndPassword(
            Optional<String> username, Optional password) {
        UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username , password);
        
        return tryToAuthenticate(requestAuthentication);
    }

    private Authentication tryToAuthenticate(
            UsernamePasswordAuthenticationToken requestAuthentication) {
       
        Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
        if(responseAuthentication==null||!responseAuthentication.isAuthenticated()){
            throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
        }
        System.out.println("User successfully authenticated");
        return responseAuthentication;
    }

    private boolean postToAuthenticate(HttpServletRequest httpRequest,
            String resourcePath) {
        return AUTHENTICATE_URL.equalsIgnoreCase(resourcePath)&&httpRequest.getMethod().equals("POST");
    }

    private HttpServletRequest asHttp(ServletRequest request) {
        return (HttpServletRequest) request;
    }

    private HttpServletResponse asHttp(ServletResponse response) {
        return (HttpServletResponse) response;
    }

}
